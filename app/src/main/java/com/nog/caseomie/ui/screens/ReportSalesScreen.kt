package com.nog.caseomie.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nog.caseomie.R
import com.nog.caseomie.model.SaleItemEntity
import com.nog.caseomie.ui.MainViewModel
import com.nog.caseomie.ui.theme.textColor
import com.nog.caseomie.utils.Money

@Composable
fun ReportSalesScreen(navController: NavController) {
    val viewModel = hiltViewModel<MainViewModel>()
    val data = viewModel.allItemsSales.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AppBarReportScreen(navController)

        Spacer(modifier = Modifier.height(8.dp))

        ReportListSection(data)
    }
}

@Composable
fun AppBarReportScreen(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            navController.navigateUp()
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.relatorio_de_vendas),
            fontWeight = FontWeight.Light,
            color = textColor,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun ReportListSection(data: State<List<SaleItemEntity>>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(data.value) { item ->
            ReportSaleItemCard(item)
        }
    }
}


@Composable
fun ReportSaleItemCard(item: SaleItemEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .graphicsLayer(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 8F
            ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.clienteName, item.clientName),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    color = textColor)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.productName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    color = textColor
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                ) {

                    Row {
                        Text(
                            text = stringResource(R.string.value),
                            style = MaterialTheme.typography.bodyLarge,
                            color = textColor
                        )
                        Text(
                            text = Money.formatToReal(item.uniqueValue),
                            style = MaterialTheme.typography.bodyLarge,
                            color = textColor,
                            maxLines = 1
                        )
                    }
                    Row {
                        Text(
                            text = stringResource(R.string.qtdValue),
                            style = MaterialTheme.typography.bodyLarge,
                            color = textColor
                        )
                        Text(
                            text = "${item.quantity}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = textColor
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Row {
                        Text(text = "")
                    }

                    Row {
                        Text(
                            text = stringResource(R.string.totalValue),
                            style = MaterialTheme.typography.bodyLarge,
                            color = textColor
                        )
                        Text(
                            text = Money.formatToReal(item.quantity * item.uniqueValue),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}