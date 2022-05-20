const express = require('express');
const bodyParser = require('body-parser');


const socketio = require('socket.io')(server);
var app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var server = app.listen(8080,"0.0.0.0", ()=>{
    console.log('Server is running on port number 8080')
})

var io = socketio.listen(server)

io.on('connection',function(socket) {

    console.log(`Connection : SocketId = ${socket.id}`)
    
    socket.on('subscribe', function(data) {
        console.log('subscribe trigged')
        const room_data = JSON.parse(data)
        const userName = room_data.userName;
        const roomName = room_data.roomName;
    
        socket.join(`${roomName}`)
        console.log(`Username : ${userName} joined Room Name : ${roomName}`)
        
        io.to(`${roomName}`).emit('new_user', userName);

    })

    socket.on('unsubscribe',function(data) {
        console.log('unsubscribe trigged')
        const room_data = JSON.parse(data)
        const userName = room_data.userName;
        const roomName = room_data.roomName;
    
        console.log(`Username : ${userName} left Room Name : ${roomName}`)
        socket.broadcast.to(`${roomName}`).emit('user_left',userName)
        socket.leave(`${roomName}`)
    })

    socket.on('new_message',function(data) {

        const messageData = JSON.parse(data)
        const text = messageData.text
        const roomName = messageData.roomName
        const userName = messageData.userName;

        console.log(`[Room ${roomName}] ${userName} : ${text}`)

        const chatData = {
            userName : userName,
            text : text,
            roomName : roomName
        }
        socket.broadcast.to(`${roomName}`).emit('update_chat',JSON.stringify(chatData))
    })

    socket.on('disconnect', function () {
        console.log("One of sockets disconnected from our server.")
    });
})

module.exports = server;
