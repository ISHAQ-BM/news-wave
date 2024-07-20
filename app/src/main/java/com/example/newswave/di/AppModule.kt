package com.example.newswave.di


import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.newswave.BuildConfig
import com.example.newswave.R
import com.example.newswave.auth.data.source.remote.AuthRemoteDataSource
import com.example.newswave.auth.domain.repository.AuthRepository
import com.example.newswave.auth.domain.use_case.OneTapSignInUseCase
import com.example.newswave.auth.domain.use_case.SignUserWithCredentialUseCase
import com.example.newswave.bookmark.data.source.local.BookmarkLocalDataSource
import com.example.newswave.bookmark.data.source.local.NewsDao
import com.example.newswave.bookmark.data.source.local.NewsDatabase
import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import com.example.newswave.bookmark.domain.use_case.GetBookmarkedNewsUseCase
import com.example.newswave.core.util.SIGN_IN_REQUEST
import com.example.newswave.core.util.SIGN_UP_REQUEST
import com.example.newswave.news.data.source.remote.NewsRemoteDataSource
import com.example.newswave.news.data.source.remote.api.NewsApiService
import com.example.newswave.news.domain.repository.NewsRepository
import com.example.newswave.news.domain.use_case.GetNewsHeadlinesUseCase
import com.example.newswave.search.data.source.remote.SearchNewsRemoteDataSource
import com.example.newswave.search.data.source.remote.api.SearchApiService
import com.example.newswave.search.domain.repository.SearchNewsRepository
import com.example.newswave.search.domain.use_case.SearchNewsUseCase
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth()= Firebase.auth


    @Singleton
    @Provides
    @Named(SIGN_IN_REQUEST)
    fun provideSignInRequest(app: Application)= BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()
    @Singleton
    @Provides
    @Named(SIGN_UP_REQUEST)
    fun provideSignUpRequest(app: Application)= BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    @Singleton
    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    )= Identity.getSignInClient(context)


    @Provides
    @Singleton
    fun provideNewsApiService(): NewsApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchApiService(): SearchApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SearchApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.newsDao()
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_database"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()
    }



    @Provides
    @Singleton
    fun provideGetNewsHeadlinesUseCase(newsRepository: NewsRepository):GetNewsHeadlinesUseCase{
        return GetNewsHeadlinesUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideGetBookmarkedNewsUseCase(bookmarkRepository: BookmarkRepository):GetBookmarkedNewsUseCase{
        return GetBookmarkedNewsUseCase(bookmarkRepository)
    }

    @Provides
    @Singleton
    fun provideSearchNewsUseCase(searchNewsRepository: SearchNewsRepository):SearchNewsUseCase{
        return SearchNewsUseCase(searchNewsRepository)
    }

    @Provides
    @Singleton
    fun provideSignUserWithCredentialUseCase(authRepository: AuthRepository):SignUserWithCredentialUseCase{
        return SignUserWithCredentialUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideOneTapSignInUseCase(authRepository: AuthRepository):OneTapSignInUseCase{
        return OneTapSignInUseCase(authRepository)
    }


    @Provides
    @Singleton
    fun provideNewsRemoteDataSource(newsApiService: NewsApiService):NewsRemoteDataSource{
        return NewsRemoteDataSource(newsApiService)
    }

    @Provides
    @Singleton
    fun provideBookmarkLocalDataSource(newsDao: NewsDao):BookmarkLocalDataSource{
        return BookmarkLocalDataSource(newsDao)
    }

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        auth:FirebaseAuth,
        oneTapClient: SignInClient,
        @Named(SIGN_IN_REQUEST)
        signUpRequest: BeginSignInRequest,
        @Named(SIGN_UP_REQUEST)
        signInRequest: BeginSignInRequest,
    ):AuthRemoteDataSource{
        return AuthRemoteDataSource(oneTapClient, signUpRequest, signInRequest, auth)
    }

    @Provides
    @Singleton
    fun provideSearchNewsRemoteDataSource(searchApiService: SearchApiService):SearchNewsRemoteDataSource{
        return SearchNewsRemoteDataSource(searchApiService)
    }


}
