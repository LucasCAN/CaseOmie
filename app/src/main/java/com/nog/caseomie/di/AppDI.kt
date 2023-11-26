package com.nog.caseomie.di

import android.content.Context
import androidx.room.Room
import com.nog.caseomie.database.SaleDao
import com.nog.caseomie.database.SaleDatabase
import com.nog.caseomie.database.SaleLocalDataSource
import com.nog.caseomie.repository.SaleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDI {
    @Provides
    @Singleton
    fun providesSaleDao(saleDatabase: SaleDatabase): SaleDao {
        return saleDatabase.SaleDao()
    }

    @Provides
    @Singleton
    fun providesSaleRepository(saleLocalDataSource: SaleLocalDataSource) = SaleRepository(saleLocalDataSource)

    @Provides
    @Singleton
    fun providesSaleDatabase(
        @ApplicationContext applicationContext: Context
    ): SaleDatabase {
        return Room.databaseBuilder(
            applicationContext,
            SaleDatabase::class.java,
            "sale_database"
        ).build()
    }
}