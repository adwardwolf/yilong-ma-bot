package com.wo1f

import com.mongodb.client.MongoDatabase
import com.wo1f.domain.models.ConversationDb
import com.wo1f.domain.models.ConversationRes
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

object Connection {
    private const val connectionString = "mongodb://localhost:27017/chatterbot-database"
    private val client = KMongo.createClient(connectionString)
    private val chatterbot: MongoDatabase = client.getDatabase(Databases.CHATTERBOT)
    val conversations = chatterbot.getCollection<ConversationRes>(Collections.CONVERSATIONS)
    val conversationsDb = chatterbot.getCollection<ConversationDb>(Collections.CONVERSATIONS)
}

object Databases {
    const val CHATTERBOT = "chatterbot-database"
}

object Collections {
    const val CONVERSATIONS = "conversations"
}
