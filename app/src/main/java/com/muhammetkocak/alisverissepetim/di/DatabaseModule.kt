package com.muhammetkocak.alisverissepetim.di

import android.app.Application
import androidx.room.Room
import com.muhammetkocak.alisverissepetim.data.local.ShoppingDatabase
import com.muhammetkocak.alisverissepetim.data.local.dao.ShoppingDao
import com.muhammetkocak.alisverissepetim.data.repository.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideShoppingDatabase(app: Application): ShoppingDatabase {
        return Room.databaseBuilder(
            app,
            ShoppingDatabase::class.java,
            "shopping_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideShoppingDao(db: ShoppingDatabase): ShoppingDao {
        return db.shoppingDao
    }

    @Provides
    @Singleton
    fun provideShoppingRepository(dao: ShoppingDao): ShoppingRepository {
        return ShoppingRepository(dao)
    }
}
