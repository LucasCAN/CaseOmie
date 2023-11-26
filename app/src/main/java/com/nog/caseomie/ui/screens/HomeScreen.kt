package com.nog.caseomie.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nog.caseomie.R
import com.nog.caseomie.navigation.NavigationRoutes.REPORT_SALE_SCREEN
import com.nog.caseomie.navigation.NavigationRoutes.SALE_SCREEN
import com.nog.caseomie.ui.MainViewModel
import com.nog.caseomie.ui.theme.primaryBlue
import com.nog.caseomie.ui.theme.textColor
import com.nog.caseomie.utils.Money

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeScreenButtons(navController)

        Spacer(modifier = Modifier.height(16.dp))

        HomeScreenTotalSales()
    }
}

@Composable
fun HomeScreenButtons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = { navController.navigate(SALE_SCREEN) },
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryBlue,
                contentColor = textColor
            )
        ) {
            Text(
                text = stringResource(R.string.fazer_uma_venda),
                color = textColor
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            modifier = Modifier.weight(1f),
            onClick = { navController.navigate(REPORT_SALE_SCREEN) },
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryBlue,
                contentColor = textColor
            )
        ) {
            Text(
                text = stringResource(R.string.relatorio_de_vendas),
                color = textColor
            )
        }
    }
}

@Composable
fun HomeScreenTotalSales() {
    val viewModel = hiltViewModel<MainViewModel>()

    Text(
        text = stringResource(R.string.total_de_vendas),
        style = MaterialTheme.typography.bodyLarge,
        fontSize = 18.sp,
        color = textColor
    )
    viewModel.sumTotalValue.collectAsState(initial = 0).value?.let { totalValue ->
        Text(
            text = Money.formatToReal(totalValue.toDouble()),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}