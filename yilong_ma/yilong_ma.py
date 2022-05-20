from chatterbot import ChatBot
from chatterbot.trainers import ChatterBotCorpusTrainer
import socketio
import json
import logging

name = "Yilong Ma"
room = "1"


class Chat:
    def __init__(self, roomName, userName, text):
        self.roomName = roomName
        self.userName = userName
        self.text = text

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4)


class JoinChat:
    def __init__(self, roomName, userName):
        self.roomName = roomName
        self.userName = userName

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4)


logger = logging.getLogger()
logger.setLevel(logging.CRITICAL)

sio = socketio.Client()


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

    bot_response = bot.get_response(user_input)
    message = Chat(room, name, bot_response.text).to_json()
    sio.emit('new_message', message)
    print('Bot:', bot_response)


@sio.event
def disconnect():
    print('disconnected from server')


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

trainer = ChatterBotCorpusTrainer(bot)

trainer.train('chatterbot.corpus.english')

sio.connect('http://localhost:8080')
sio.wait()
