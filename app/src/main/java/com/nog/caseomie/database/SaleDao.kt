package com.nog.caseomie.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nog.caseomie.model.SaleItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SaleDao {
    @Query("SELECT * FROM sale_items")
    fun getItems(): Flow<List<SaleItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: SaleItemEntity)

    @Query("DELETE FROM sale_items")
    suspend fun removeAll()


    @Query("INSERT INTO report (clientName, productName, quantity, uniqueValue, totalValue)\n" +
            "SELECT clientName, productName, quantity, uniqueValue, totalValue\n" +
            "FROM sale_items")
    suspend fun copySaleToReport()

    @Query("SELECT SUM(totalValue) AS totalSum\n" +
            "FROM report")
    fun sumTotalValue(): Flow<Double>

    @Query("SELECT * FROM report")
    fun getAllItemsReport(): Flow<List<SaleItemEntity>>

    @Delete
    suspend fun removeItem(item: SaleItemEntity)
}