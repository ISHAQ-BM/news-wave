package com.example.newswave.interests.domain.repository

import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface InterestsRepository {
    val currentUser: FirebaseUser?
    suspend fun getInterests(): Flow<Result<List<String>, Error>>
    suspend fun saveInterests(interests: List<String>): Flow<Result<Boolean, Error>>
}