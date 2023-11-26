package com.nog.caseomie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nog.caseomie.model.SaleItemEntity
import com.nog.caseomie.repository.SaleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SaleRepository
): ViewModel() {

    //No UDF o viewModel possui um state
    val allItems: Flow<List<SaleItemEntity>> = repository.getItems()

    fun addItem(itemData: SaleItemEntity) {
        viewModelScope.launch {
            repository.addItem(itemData.clientName, itemData.productName, itemData.quantity, itemData.uniqueValue, itemData.totalValue)
        }
    }

    fun removeAllSaleItems() {
        viewModelScope.launch {
            repository.removeAllSaleItems()
        }
    }

    fun copySaleToReport() {
        viewModelScope.launch {
            repository.copySaleToReport()
        }
    }

    fun removeItem(item: SaleItemEntity) {
        viewModelScope.launch {
            repository.removeItem(item)
        }
    }

    val sumTotalValue: Flow<Double> = repository.sumTotalValue()

    val allItemsSales: Flow<List<SaleItemEntity>> = repository.getAllItemsReport()
}