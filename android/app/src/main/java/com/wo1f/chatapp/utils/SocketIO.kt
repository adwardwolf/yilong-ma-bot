package com.wo1f.chatapp.utils

import android.util.Log
import com.google.gson.Gson
import com.wo1f.chatapp.data.Chat
import com.wo1f.chatapp.data.ChatRes
import com.wo1f.chatapp.data.JoinChat
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.EngineIOException
import io.socket.engineio.client.Transport
import java.time.LocalTime


class SocketIO {

    private lateinit var mSocket: Socket
    private lateinit var mListener: OnSocketListener
    private val gson = Gson()
    private val name = "adwardwo1f"
    private val room = "1"

    init {
        try {
            mSocket = IO.socket("http://192.168.3.42:8080")
            mSocket.apply {
                connect()
                on(Socket.EVENT_CONNECT_ERROR, onConnectionError)
                on(Socket.EVENT_CONNECT, onConnect)
                on(EVENT_NEW_USER, onNewUser)
                on(EVENT_UPDATE_CHAT, onUpdateChat)
                on(EVENT_USER_LEFT, onUserLeft)
                on(Socket.EVENT_DISCONNECT, onDisconnect)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect: ${e.localizedMessage}")
        }
    }

    private val onConnect: Emitter.Listener
        get() = Emitter.Listener {
            val data = JoinChat(name, room)
            val jsonData = gson.toJson(data)
            mSocket.emit(EVENT_SUBSCRIBE, jsonData)
        }

    private val onConnectionError: Emitter.Listener
        get() = Emitter.Listener {
            Log.d("SocketHandler" ,"Connection Error : $it")
            it.forEach { item ->
                val exception = item as EngineIOException
                print(exception.message + " " + exception.code + " " + exception.cause)
            }
        }

    private val onNewUser: Emitter.Listener
        get() = Emitter.Listener {
            val name = it[0] as String
            val chat = Chat(
                name = name,
                text = "",
                date = LocalTime.now(),
                type = Chat.Type.JOINED
            )
            mListener.setSocketListener(chat)
        }

    private val onUpdateChat: Emitter.Listener
        get() = Emitter.Listener {
            val chatRes = gson.fromJson(it[0].toString(), ChatRes::class.java)
            Log.d("chatRes", it[0].toString())
            val chat = Chat(
                name = chatRes.userName,
                text = chatRes.text,
                date = LocalTime.now(),
                type = Chat.Type.RECEIVER
            )
            mListener.setSocketListener(chat)
        }

    private val onUserLeft: Emitter.Listener
        get() = Emitter.Listener {
            val leftUserName = it[0] as String
            val chat = Chat(
                name = leftUserName,
                text = "",
                date = LocalTime.now(),
                type = Chat.Type.LEFT
            )
            mListener.setSocketListener(chat)
        }

    private val onDisconnect: Emitter.Listener
        get() = Emitter.Listener {
            val data = JoinChat(name, room)
            val jsonData = gson.toJson(data)
            mSocket.emit(EVENT_UNSUBSCRIBE, jsonData)
        }

    fun sendMessage(text: String) {
        val sendData = ChatRes(
            userName = name,
            roomName = room,
            text = text
        )
        val jsonData = gson.toJson(sendData)
        mSocket.emit(EVENT_NEW_MESSAGE, jsonData)
    }

    fun onReceiving(listener: OnSocketListener) {
        mListener = listener
    }

    interface OnSocketListener {

        fun setSocketListener(chat: Chat)
    }

    companion object {
        const val EVENT_NEW_USER = "new_user"
        const val EVENT_USER_LEFT = "user_left"
        const val EVENT_UPDATE_CHAT = "update_chat"
        const val EVENT_NEW_MESSAGE = "new_message"
        const val EVENT_UNSUBSCRIBE = "unsubscribe"
        const val EVENT_SUBSCRIBE = "subscribe"
    }
}