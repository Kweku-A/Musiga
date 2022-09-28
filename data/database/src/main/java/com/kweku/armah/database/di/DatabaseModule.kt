package com.kweku.armah.database.di

import android.content.Context
import androidx.room.Room
import com.kweku.armah.database.converter.FeedTypeConverter
import com.kweku.armah.database.db.MusigaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "musiga_db"

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context, feedTypeConverter: FeedTypeConverter) =
        Room.databaseBuilder(context, MusigaDatabase::class.java, DB_NAME)
            .addTypeConverter(feedTypeConverter)
            .build()

    @Provides
    @Singleton
    fun providesFeedDao(musigaDatabase: MusigaDatabase) = musigaDatabase.feedDao()

    @Provides
    @Singleton
    fun providesSearchFeedDao(musigaDatabase: MusigaDatabase) = musigaDatabase.searchFeedDao()

    @Provides
    @Singleton
    fun providesRemoteKeysDao(musigaDatabase: MusigaDatabase) = musigaDatabase.remoteKeysDao()
}
