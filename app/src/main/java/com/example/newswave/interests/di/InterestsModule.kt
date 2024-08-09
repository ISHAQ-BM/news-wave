package com.example.newswave.interests.di

import com.example.newswave.interests.data.repository.InterestsRepositoryImpl
import com.example.newswave.interests.data.source.remote.InterestsRemoteDataSource
import com.example.newswave.interests.domain.repository.InterestsRepository
import com.example.newswave.interests.domain.use_case.GetInterestsUseCase
import com.example.newswave.interests.domain.use_case.SaveInterestsUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterestsModule {


    @Provides
    @Singleton
    fun provideGetInterestsUseCase(interestsRepository: InterestsRepository): GetInterestsUseCase {
        return GetInterestsUseCase(interestsRepository)
    }

    @Provides
    @Singleton
    fun provideSaveInterestsUseCaseUseCase(interestsRepository: InterestsRepository): SaveInterestsUseCase {
        return SaveInterestsUseCase(interestsRepository)
    }

    @Provides
    @Singleton
    fun provideInterestsRemoteDataSource(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): InterestsRemoteDataSource {
        return InterestsRemoteDataSource(auth, firestore)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class InterestsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindInterestsRepository(interestsRepositoryImpl: InterestsRepositoryImpl): InterestsRepository


}