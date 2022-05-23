import json


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
