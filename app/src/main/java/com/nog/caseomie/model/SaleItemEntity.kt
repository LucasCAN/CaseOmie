package com.nog.caseomie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sale_items")
data class SaleItemEntity(
    val clientName: String,
    val productName: String,
    val quantity: Int,
    val uniqueValue: Double,
    val totalValue: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
)
