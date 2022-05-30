const mongoose = require("mongoose");

const chatSchema = new mongoose.Schema({
    _id: {
        required: true,
        type: mongoose.Schema.Types.ObjectId
    },
    roomName: {
        required: true,
        type: String
    },
    userName: {
        required: true,
        type: String
    },
    text: {
        type: String
    },
    type: {
        required: true,
        type: String
    },
    date: {
        type: Date,
        default: Date.now
    }
}, { versionKey: false })

const Chat = mongoose.model('Chat', chatSchema);
module.exports = { Chat };
