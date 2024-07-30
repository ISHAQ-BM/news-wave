package com.example.newswave.interests.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.interests.data.source.remote.InterestsRemoteDataSource
import com.example.newswave.interests.domain.repository.InterestsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InterestsRepositoryImpl @Inject constructor(
    private val interestsRemoteDataSource: InterestsRemoteDataSource
): InterestsRepository {
    override val currentUser: FirebaseUser?
        get() = interestsRemoteDataSource.currentUser

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getInterests(): Flow<Result<List<String>, Error>> =
        interestsRemoteDataSource.getInterests()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun updateInterests(interests: List<String>): Flow<Result<Boolean, Error>> =
        interestsRemoteDataSource.updateInterests(interests)
}