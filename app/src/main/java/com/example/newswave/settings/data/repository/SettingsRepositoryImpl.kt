package com.example.newswave.settings.data.repository

import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.settings.data.source.remote.SettingsRemoteDataSource
import com.example.newswave.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsRemoteDataSource: SettingsRemoteDataSource
) :SettingsRepository {
    override suspend fun signOut(): Flow<Result<Boolean, Error>> = settingsRemoteDataSource.signOut()
}