package com.example.newswave.settings.domain.repository

import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun signOut(): Flow<Result<Boolean, Error>>
}