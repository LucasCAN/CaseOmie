package com.nog.caseomie.database

import com.nog.caseomie.model.SaleItemEntity
import javax.inject.Inject

class SaleLocalDataSource @Inject constructor(
    private val saleDao: SaleDao
) {
    fun getItems() = saleDao.getItems()
    suspend fun addItem(
        clientName: String,
        productName: String,
        quantity: Int,
        uniqueValue: Double,
        totalValue: Double
    ) = saleDao.addItem(SaleItemEntity(clientName, productName, quantity, uniqueValue, totalValue))
    suspend fun removeAllSaleItems() = saleDao.removeAll()
    suspend fun copySaleToReport() = saleDao.copySaleToReport()
    fun sumTotalValue() = saleDao.sumTotalValue()
    fun getAllItemsReport() = saleDao.getAllItemsReport()
    suspend fun removeItem(item: SaleItemEntity) = saleDao.removeItem(item)
}