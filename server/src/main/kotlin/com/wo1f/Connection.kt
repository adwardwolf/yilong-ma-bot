/**
 * @author Adwardwo1f
 * @created May 27, 2022
 */

package com.wo1f

import com.mongodb.client.MongoDatabase
import com.wo1f.domain.models.CategoryDb
import com.wo1f.domain.models.CategoryRes
import com.wo1f.domain.models.ConversationDb
import com.wo1f.domain.models.ConversationRes
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

object Connection {
    private const val connectionString = "mongodb://localhost:27017/chatterbot-database"
    private val client = KMongo.createClient(connectionString)
    private val chatterbot: MongoDatabase = client.getDatabase(Databases.CHATTERBOT)

    val conversationsColl = chatterbot.getCollection<ConversationRes>(Collections.CONVERSATIONS)
    val conversationsDbColl = chatterbot.getCollection<ConversationDb>(Collections.CONVERSATIONS)
    val categoriesColl = chatterbot.getCollection<CategoryRes>(Collections.CATEGORIES)
    val categoriesDbColl = chatterbot.getCollection<CategoryDb>(Collections.CATEGORIES)
}

object Databases {
    const val CHATTERBOT = "chatterbot-database"
}

object Collections {
    const val CONVERSATIONS = "conversations"
    const val CATEGORIES = "categories"
}
