package com.nog.caseomie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report")
data class ReportEntity(
    val clientName: String,
    val productName: String,
    val quantity: Int,
    val uniqueValue: Double,
    val totalValue: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
