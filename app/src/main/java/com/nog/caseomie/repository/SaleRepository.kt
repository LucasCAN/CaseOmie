package com.nog.caseomie.repository

import com.nog.caseomie.database.SaleLocalDataSource
import com.nog.caseomie.model.SaleItemEntity
import javax.inject.Inject

class SaleRepository @Inject constructor(
    private val saleLocalDataSource: SaleLocalDataSource
) {
    fun getItems() = saleLocalDataSource.getItems()
    suspend fun addItem(
        clientName: String,
        productName: String,
        quantity: Int,
        uniqueValue: Double,
        totalValue: Double
    ) = saleLocalDataSource.addItem(clientName, productName, quantity, uniqueValue, totalValue)
    suspend fun removeAllSaleItems() = saleLocalDataSource.removeAllSaleItems()
    suspend fun copySaleToReport() = saleLocalDataSource.copySaleToReport()
    fun sumTotalValue() = saleLocalDataSource.sumTotalValue()
    fun getAllItemsReport() = saleLocalDataSource.getAllItemsReport()
    suspend fun removeItem(item: SaleItemEntity) = saleLocalDataSource.removeItem(item)
}