package com.example.newswave.interests.data.source.remote

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.newswave.core.data.source.remote.model.User
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.INTERESTS
import com.example.newswave.core.util.Result
import com.example.newswave.core.util.USERS
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class InterestsRemoteDataSource @Inject constructor(
    val auth:FirebaseAuth,
    private val firestore:FirebaseFirestore
) {
    val currentUser = auth.currentUser

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun getInterests(): Flow<Result<List<String>, Error>>{
        return flow {
            try {

                val currentUser=firestore.collection(USERS).document(currentUser?.uid!!).get().await()
                val interests=currentUser.get(INTERESTS) as? List<String>
                Log.d("interests","${interests}")
                emit(Result.Success(interests ?: emptyList()))
            }catch (e: HttpException) {
                emit(Result.Error(Error.Network.UNKNOWN))
            } catch (e: IOException) {
                emit(Result.Error(Error.Network.NO_INTERNET))
            } catch (e: Exception) {
                emit(Result.Error(Error.Network.UNKNOWN))
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun updateInterests(interests:List<String>): Flow<Result<Boolean, Error>>{
        return flow {
            try {
                firestore.collection(USERS).document(currentUser?.uid!!).update(
                    INTERESTS,interests).await()
                emit(Result.Success(true))

            }catch (e: HttpException) {
                emit(Result.Error(Error.Network.UNKNOWN))
            } catch (e: IOException) {
                emit(Result.Error(Error.Network.NO_INTERNET))
            } catch (e: Exception) {
                emit(Result.Error(Error.Network.UNKNOWN))
            }
        }
    }
}