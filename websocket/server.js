/*
@author adwardwo1f
@created May 28, 2022
*/

const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const socketio = require('socket.io')(server);
const { Chat } = require('./model');

// Mongoose
const mongodbUrl = "mongodb://localhost:27017/chatterbot-database"
mongoose.connect(mongodbUrl)
const database = mongoose.connection;

database.on('error', (error) => {
    console.log(error);
})

database.on('connected', () => {
    console.log('Database connected');
})

// Express
var app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var server = app.listen(8080,"0.0.0.0", ()=>{
    console.log('Server is running on port number 8080')
})

// Socket
var io = socketio.listen(server)

io.on('connection',function(socket) {

    console.log(`Connection : SocketId = ${socket.id}`)
    
    socket.on('subscribe', function(data) {
        const room_data = JSON.parse(data)
        const userName = room_data.userName;
        const roomName = room_data.roomName;
    
        socket.join(`${roomName}`)
        console.log(`Username : ${userName} joined Room Name : ${roomName}`)
        socket.broadcast.to(`${roomName}`).emit('new_user', userName);
    })

    socket.on('unsubscribe',function(data) {
        const room_data = JSON.parse(data)
        const userName = room_data.userName;
        const roomName = room_data.roomName;
    
        console.log(`Username : ${userName} left Room Name : ${roomName}`)
        socket.broadcast.to(`${roomName}`).emit('user_left', userName)
        socket.leave(`${roomName}`)
    })

    socket.on('new_message',function(data) {

        const messageData = JSON.parse(data)
        const idRes = messageData.id
        const text = messageData.text
        const roomName = messageData.roomName
        const userName = messageData.userName;

        console.log(`[Room ${roomName}] ${userName} : ${text}`)

        var id;
        if (idRes === null) {
            id = mongoose.Types.ObjectId()
        } else {
            id = mongoose.Types.ObjectId(idRes)
        }

        const chatData = new Chat({
            _id : id,
            userName : userName,
            text : text,
            type : "message",
            roomName : roomName
        })
        try {
            chatData.save();
            socket.broadcast.to(`${roomName}`).emit('update_chat',JSON.stringify(chatData))
        } catch (error) {
            console.log('Insert chat failed');
        }
    })

    socket.on('disconnect', function () {
        console.log("One of sockets disconnected from our server.")
    });
})

module.exports = server;
