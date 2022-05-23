def get_database():
    from pymongo import MongoClient

    CONNECTION_STRING = "mongodb://localhost:27017/chatterbot-database"

    client = MongoClient(CONNECTION_STRING)
    return client["chatterbot-database"]


def get_conversation():
    collections = get_database()['conversations']
    q_and_a = []
    conversations = collections.find({"isTrained": False})

    for item in conversations:
        q_and_a.append(item['question'])
        q_and_a.append(item['answer'])
    return q_and_a


def mark_as_trained():
    collections = get_database()['conversations']
    collections.update_many({}, {'$set': {'isTrained': True}})
