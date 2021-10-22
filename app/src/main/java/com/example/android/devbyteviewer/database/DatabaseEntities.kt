package com.example.android.devbyteviewer.database

import android.content.Context
import androidx.room.*
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.NetworkVideoContainer


// Create the DatabaseEntities class, adding annotations for the class and the primary key.
@Entity
data class DatabaseVideo constructor(
    @PrimaryKey
    val url: String,
    val updated: String,
    val title: String,
    val description: String,
    val thumbnail: String)


// Add an extension function which converts from database objects to domain objects:
fun List<DatabaseVideo>.asDomainModel(): List<Video> {
    return map {
        Video (
            url = it.url,
            title = it.title,
            description = it.description,
            updated = it.updated,
            thumbnail = it.thumbnail)
    }
}
