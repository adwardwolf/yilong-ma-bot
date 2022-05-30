/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f

import com.mongodb.client.MongoDatabase
import com.wo1f.domain.models.CategoryDb
import com.wo1f.domain.models.CategoryRes
import com.wo1f.domain.models.ChatRes
import com.wo1f.domain.models.ConversationDb
import com.wo1f.domain.models.ConversationRes
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

/**
 * Contains Mongodb: connection, databases, and collections
 */
object Connection {
    private const val connectionString = "mongodb://localhost:27017/chatterbot-database"
    private val client = KMongo.createClient(connectionString)

    /**
     * chatterbot-database instance
     */
    private val chatterbot: MongoDatabase = client.getDatabase(Databases.CHATTERBOT)

    /**
     * Conversation collection instance of type [ConversationRes]
     */
    val conversationsColl = chatterbot.getCollection<ConversationRes>(Collections.CONVERSATIONS)

    /**
     * Conversation collection instance of type [ConversationDb]
     */
    val conversationsDbColl = chatterbot.getCollection<ConversationDb>(Collections.CONVERSATIONS)

    /**
     * Category collection instance of type [CategoryRes]
     */
    val categoriesColl = chatterbot.getCollection<CategoryRes>(Collections.CATEGORIES)

    /**
     * Category collection instance of type [CategoryDb]
     */
    val categoriesDbColl = chatterbot.getCollection<CategoryDb>(Collections.CATEGORIES)

    /**
     * Chat collection instance of type [ChatRes]
     */
    val chatsColl = chatterbot.getCollection<ChatRes>(Collections.CHATS)
}

object Databases {
    const val CHATTERBOT = "chatterbot-database"
}

object Collections {
    const val CONVERSATIONS = "conversations"
    const val CATEGORIES = "categories"
    const val CHATS = "chats"
}
