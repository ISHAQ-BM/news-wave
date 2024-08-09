package com.example.newswave.settings.di


import com.example.newswave.settings.data.repository.SettingsRepositoryImpl
import com.example.newswave.settings.data.source.remote.SettingsRemoteDataSource
import com.example.newswave.settings.domain.repository.SettingsRepository
import com.example.newswave.settings.domain.use_case.SignOutUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object SettingsModule {
    @Provides
    @Singleton
    fun provideSignOutUseCase(settingsRepository: SettingsRepository): SignOutUseCase {
        return SignOutUseCase(settingsRepository)
    }

    @Provides
    @Singleton
    fun provideSettingsRemoteDataSource(auth: FirebaseAuth): SettingsRemoteDataSource {
        return SettingsRemoteDataSource(auth)
    }


}

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository


}

