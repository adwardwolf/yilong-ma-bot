/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f.chatapp.utils

import com.google.gson.Gson
import com.wo1f.chatapp.data.model.chat.ChatRes
import com.wo1f.chatapp.data.model.chat.ChatRq
import com.wo1f.chatapp.data.model.chat.JoinChat
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.EngineIOException
import timber.log.Timber
import java.time.LocalDate

class SocketIO {

    private lateinit var mSocket: Socket
    private lateinit var mListener: OnSocketListener
    private val gson = Gson()

    init {
        try {
            mSocket = IO.socket(Constants.BASE_URL + Constants.SOCKET_PORT)
            mSocket.apply {
                connect()
                on(Socket.EVENT_CONNECT_ERROR, onConnectionError)
                on(Socket.EVENT_CONNECT, onConnect)
                on(EVENT_UPDATE_CHAT, onUpdateChat)
                on(Socket.EVENT_DISCONNECT, onDisconnect)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.d("fail", "Failed to connect: ${e.localizedMessage}")
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
            Timber.d("SocketHandler", "Connection Error : $it")
            it.forEach { item ->
                val exception = item as EngineIOException
                print(exception.message + " " + exception.code + " " + exception.cause)
            }
        }

    /**
     * Listen for new message
     */
    private val onUpdateChat: Emitter.Listener
        get() = Emitter.Listener {
            val chatRes = gson.fromJson(it[0].toString(), ChatRes::class.java)
            val chat = ChatRes(
                id = chatRes.id,
                userName = chatRes.userName,
                roomName = room,
                text = chatRes.text,
                date = LocalDate.now().toAppDate()
            )
            mListener.setSocketListener(chat)
        }

    private val onDisconnect: Emitter.Listener
        get() = Emitter.Listener {
        }

    fun sendMessage(text: String) {
        val sendData = ChatRq(
            userName = name,
            roomName = room,
            text = text
        )
        val jsonData = gson.toJson(sendData)
        mSocket.emit(EVENT_NEW_MESSAGE, jsonData)
    }

    fun disconnect() {
        val data = JoinChat(name, room)
        val jsonData = gson.toJson(data)
        mSocket.emit(EVENT_UNSUBSCRIBE, jsonData)
        mSocket.disconnect()
    }

    fun onReceiving(listener: OnSocketListener) {
        mListener = listener
    }

    interface OnSocketListener {

        fun setSocketListener(chat: ChatRes)
    }

    companion object {
        const val name = "adwardwo1f"
        const val room = "1"
        const val EVENT_UPDATE_CHAT = "update_chat"
        const val EVENT_NEW_MESSAGE = "new_message"
        const val EVENT_UNSUBSCRIBE = "unsubscribe"
        const val EVENT_SUBSCRIBE = "subscribe"
    }
}
