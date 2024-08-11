package com.example.newswave.bookmark.di

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.newswave.bookmark.data.repository.BookmarkRepositoryImpl
import com.example.newswave.bookmark.data.source.remote.BookmarkRemoteDataSource
import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import com.example.newswave.bookmark.domain.use_case.GetBookmarkedNewsUseCase
import com.example.newswave.bookmark.domain.use_case.UnBookmarkNewsUseCase
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
object BookmarkModule {

    @Provides
    @Singleton
    fun provideGetBookmarkedNewsUseCase(bookmarkRepository: BookmarkRepository): GetBookmarkedNewsUseCase {
        return GetBookmarkedNewsUseCase(bookmarkRepository)
    }

    @Provides
    @Singleton
    fun provideUnBookmarkNewsUseCase(bookmarkRepository: BookmarkRepository): UnBookmarkNewsUseCase {
        return UnBookmarkNewsUseCase(bookmarkRepository)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @Provides
    @Singleton
    fun provideBookmarkRemoteDataSource(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): BookmarkRemoteDataSource {
        return BookmarkRemoteDataSource(firestore, auth)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BookmarkRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(bookmarkRepositoryImpl: BookmarkRepositoryImpl): BookmarkRepository


}
