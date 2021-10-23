package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException

//Pre-fetch data when the app is in the background. You should use the WorkManager library to accomplish this.

//Add the RefreshDataWorker class. WorkManager workers always extend a Worker class.
//We're going to use a CoroutineWorker, because we want to use coroutines to handle our asynchronous code and threading.
// Have RefreshDataWorker extend from the CoroutineWorker class. You also need to pass a Context and WorkerParameters to the class and its parent class.
class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {

    //Override the required doWork() method. This is what "work" your RefreshDataWorker does, in our case,
    // syncing with the network. Add variables for the database and the repository.
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)
        return try {
            repository.refreshVideos()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }

    }

    }