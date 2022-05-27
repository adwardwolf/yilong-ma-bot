# @Author adwardwo1f
# @Created May 27, 2022

from chatterbot import ChatBot
from chatterbot.trainers import ListTrainer
import socketio
import logging
from database import get_conversation
from database import mark_as_trained
from classes.chat import Chat, JoinChat
import json

name = "Yilong Ma"
room = "1"

logger = logging.getLogger()
logger.setLevel(logging.CRITICAL)

sio = socketio.Client()

# Create a new ChatBot instance
bot = ChatBot(
    'Terminal',
    storage_adapter='chatterbot.storage.MongoDatabaseAdapter',
    logic_adapters=[
        {
            'import_path': 'chatterbot.logic.BestMatch',
            'default_response': 'Sorry, I don\'t understand',
        }
    ],
    database_uri='mongodb://localhost:27017/chatterbot-database'
)

trainer = ListTrainer(bot)


def emit_new_message(text):
    message = Chat(room, name, text).to_json()
    sio.emit('new_message', message)


def restart():
    emit_new_message("Restarting...")
    new_list = get_conversation()
    print("new_list", new_list)
    if len(new_list) != 0:
        trainer.train(new_list)
        mark_as_trained()

    emit_new_message("Restarted!")


def check_command(command):
    if command == "/restart":
        restart()


@sio.event
def connect():
    print('connection established')
    message = JoinChat(room, name).to_json()
    sio.emit('subscribe', message)


@sio.event
def update_chat(data):
    username = json.loads(data)['userName']
    user_input = json.loads(data)['text']
    print(f'{username}:', user_input)
    if str(user_input).startswith("/"):
        check_command(user_input)
        return

    bot_response = bot.get_response(user_input)
    message = Chat(room, name, bot_response.text).to_json()
    sio.emit('new_message', message)
    print('Bot:', bot_response)


@sio.event
def disconnect():
    print('disconnected from server')


sio.connect('http://localhost:8080')
sio.wait()
