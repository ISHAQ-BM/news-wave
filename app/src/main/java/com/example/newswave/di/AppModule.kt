package com.example.newswave.di


import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.room.Room
import com.example.newswave.BuildConfig
import com.example.newswave.auth.data.source.remote.AuthRemoteDataSource
import com.example.newswave.auth.domain.repository.AuthRepository
import com.example.newswave.auth.domain.use_case.GetCurrentUserUseCase
import com.example.newswave.auth.domain.use_case.OneTapSignInUseCase
import com.example.newswave.auth.domain.use_case.SignUserWithCredentialUseCase
import com.example.newswave.home.data.source.local.NewsDao
import com.example.newswave.home.data.source.local.NewsDatabase
import com.example.newswave.bookmark.data.source.remote.BookmarkRemoteDataSource
import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import com.example.newswave.bookmark.domain.use_case.BookmarkNewsUseCase
import com.example.newswave.bookmark.domain.use_case.GetBookmarkedNewsUseCase
import com.example.newswave.bookmark.domain.use_case.UnBookmarkNewsUseCase
import com.example.newswave.core.util.SIGN_IN_REQUEST
import com.example.newswave.core.util.SIGN_UP_REQUEST
import com.example.newswave.home.data.source.local.HomeLocalDataSource
import com.example.newswave.home.data.source.remote.HomeRemoteDataSource
import com.example.newswave.home.data.source.remote.api.HomeApiService
import com.example.newswave.home.domain.repository.HomeRepository
import com.example.newswave.home.domain.use_case.GetNewsHeadlinesUseCase
import com.example.newswave.interests.data.source.remote.InterestsRemoteDataSource
import com.example.newswave.search.data.source.remote.SearchNewsRemoteDataSource
import com.example.newswave.search.data.source.remote.api.SearchApiService
import com.example.newswave.search.domain.repository.SearchNewsRepository
import com.example.newswave.search.domain.use_case.SearchNewsUseCase
import com.example.newswave.settings.domain.repository.SettingsRepository
import com.example.newswave.settings.domain.use_case.SignOutUseCase
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
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
    fun provideFirebaseFirestore() = Firebase.firestore


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
    fun provideNewsApiService(): HomeApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(HomeApiService::class.java)
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
    fun provideGetNewsHeadlinesUseCase(homeRepository: HomeRepository): GetNewsHeadlinesUseCase {
        return GetNewsHeadlinesUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun provideGetBookmarkedNewsUseCase(bookmarkRepository: BookmarkRepository):GetBookmarkedNewsUseCase{
        return GetBookmarkedNewsUseCase(bookmarkRepository)
    }

    @Provides
    @Singleton
    fun provideBookmarkNewsUseCase(bookmarkRepository: BookmarkRepository):BookmarkNewsUseCase{
        return BookmarkNewsUseCase(bookmarkRepository)
    }

    @Provides
    @Singleton
    fun provideUnBookmarkNewsUseCase(bookmarkRepository: BookmarkRepository):UnBookmarkNewsUseCase{
        return UnBookmarkNewsUseCase(bookmarkRepository)
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
    fun provideGetCurrentUserUseCase(authRepository: AuthRepository):GetCurrentUserUseCase{
        return GetCurrentUserUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideOneTapSignInUseCase(authRepository: AuthRepository):OneTapSignInUseCase{
        return OneTapSignInUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSignOutUseCase(settingsRepository: SettingsRepository):SignOutUseCase{
        return SignOutUseCase(settingsRepository)
    }


    @Provides
    @Singleton
    fun provideNewsRemoteDataSource(homeApiService: HomeApiService): HomeRemoteDataSource {
        return HomeRemoteDataSource(homeApiService)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @Provides
    @Singleton
    fun provideBookmarkRemoteDataSource(auth: FirebaseAuth,firestore: FirebaseFirestore): BookmarkRemoteDataSource {
        return BookmarkRemoteDataSource(firestore, auth)
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
        firestore:FirebaseFirestore
    ):AuthRemoteDataSource{
        return AuthRemoteDataSource(oneTapClient, signUpRequest, signInRequest, auth, firestore  )
    }

    @Provides
    @Singleton
    fun provideSearchNewsRemoteDataSource(searchApiService: SearchApiService):SearchNewsRemoteDataSource{
        return SearchNewsRemoteDataSource(searchApiService)
    }

    @Provides
    @Singleton
    fun provideInterestsRemoteDataSource(auth:FirebaseAuth,firestore:FirebaseFirestore):InterestsRemoteDataSource{
        return InterestsRemoteDataSource(auth, firestore)
    }

    @Provides
    @Singleton
    fun provideHomeLocalDataSource(newsDatabase: NewsDatabase, homeApiService: HomeApiService):HomeLocalDataSource{
        return HomeLocalDataSource(newsDatabase, homeApiService)
    }


}
