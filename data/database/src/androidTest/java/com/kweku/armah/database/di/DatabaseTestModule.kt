package com.kweku.armah.database.di

import android.content.Context
import androidx.room.Room
import com.kweku.armah.database.converter.FeedTypeConverter
import com.kweku.armah.database.db.MusigaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object DatabaseTestModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context, feedTypeConverter: FeedTypeConverter) =
        Room.inMemoryDatabaseBuilder(context, MusigaDatabase::class.java)
            .addTypeConverter(feedTypeConverter)
            .build()

    @Provides
    @Singleton
    fun providesFeedDao(musigaDatabase: MusigaDatabase) = musigaDatabase.feedDao()

    @Provides
    @Singleton
    fun providesRemoteKeysDao(musigaDatabase: MusigaDatabase) = musigaDatabase.remoteKeysDao()
}