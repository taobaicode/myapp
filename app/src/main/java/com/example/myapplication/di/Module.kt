package com.example.myapplication.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.myapplication.database.PeopleDao
import com.example.myapplication.database.PeopleDatabase
import com.example.myapplication.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging =
            HttpLoggingInterceptor { message -> if (ENABLE_HTTP_CLIENT_LOG) Log.d("VC:", message) }
                .apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        val builder =
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://swapi.tech/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
        return builder.build().create()
    }

    @Singleton
    @Provides
    fun providePeopleDB(@ApplicationContext context : Context) : PeopleDatabase {
        return Room.databaseBuilder(context, PeopleDatabase::class.java, "people").build()
    }

    @Singleton
    @Provides
    fun providerPeopleDao(peopleDatabase : PeopleDatabase) : PeopleDao {
        return peopleDatabase.peopleDao()
    }

    private val ENABLE_HTTP_CLIENT_LOG = true
}