package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class VideosRepository(private val database: VideosDatabase) {

    //Use Transformation.map to convert your LiveData list of DatabaseVideo objects to domain Video objects
    val videos: LiveData<List<Video>> = Transformations.map(database.videoDao.getVideos()) {
        it.asDomainModel()
    }



    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) { //Dispatcher IO is stating to run on disk, not ram
            //Get the data from the network and then put it in the database
            val playlist = Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel()) //Note the asterisk * is the spread operator. It allows you to pass in an array to a function that expects varargs.
        }
    }


}