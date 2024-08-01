package com.example.newswave.bookmark.data.source.remote

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.BOOKMARKED
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.NEWS
import com.example.newswave.core.util.Result
import com.google.android.play.integrity.internal.al
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException

import javax.inject.Inject


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class BookmarkRemoteDataSource @Inject constructor(
    val firestore:FirebaseFirestore,
    val auth:FirebaseAuth
) {
    suspend fun getBookmarkedNews():Flow<Result<List<News>,Error>>{
        return flow {
            try {
                val bookmarkedNews= mutableListOf<News>()
                val result =firestore.collection(NEWS).document(auth.currentUser?.uid!!).collection(BOOKMARKED).get().await()
                for (document in result.documents) {
                    document.toObject<News>()?.let { bookmarkedNews.add(it) }
                }
                emit(Result.Success(bookmarkedNews))
            }catch (e: HttpException) {
                emit(Result.Error(Error.Network.UNKNOWN))
            } catch (e: IOException) {
                emit(Result.Error(Error.Network.NO_INTERNET))
            } catch (e: Exception) {
                emit(Result.Error(Error.Network.UNKNOWN))
            }
        }
    }


    suspend fun bookmarkNews(news:News): Flow<Result<Boolean,Error>> {
        return flow {
            try {
                firestore.collection(NEWS).document(auth.currentUser?.uid!!).collection(BOOKMARKED).document(news.title).set(news).await()
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
suspend fun unBookmarkNews(title:String): Flow<Result<Boolean,Error>> {
        return flow {
            try {
                firestore.collection(NEWS).document(auth.currentUser?.uid!!).collection(BOOKMARKED).document(title).delete().await()
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