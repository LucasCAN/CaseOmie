package com.nog.caseomie.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nog.caseomie.model.ReportEntity
import com.nog.caseomie.model.SaleItemEntity

@Database(entities = [SaleItemEntity::class, ReportEntity::class], version = 1)
abstract class SaleDatabase: RoomDatabase() {
    abstract fun SaleDao(): SaleDao
}