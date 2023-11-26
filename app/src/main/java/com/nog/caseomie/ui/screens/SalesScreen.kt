package com.nog.caseomie.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nog.caseomie.R
import com.nog.caseomie.model.SaleItemEntity
import com.nog.caseomie.ui.MainViewModel
import com.nog.caseomie.ui.components.PlaceHolderTextField
import com.nog.caseomie.ui.theme.primaryBlue
import com.nog.caseomie.ui.theme.textColor
import com.nog.caseomie.utils.Money

var listTotalValues: MutableList<Double> = mutableListOf()

@Composable
fun SalesScreen(navController: NavController) {
    val viewModel = hiltViewModel<MainViewModel>()
    val context = LocalContext.current
    val data = viewModel.allItems.collectAsState(initial = emptyList())
    val textMaxLength = 25
    val quantityMaxLength = 3
    val valueMaxLength = 8

    var clientName by remember {
        mutableStateOf("")
    }
    var product by remember {
        mutableStateOf("")
    }
    var quantity by remember {
        mutableStateOf("")
    }
    var unitValue by remember {
        mutableStateOf("")
    }
    var sumTotalValue by remember {
        mutableDoubleStateOf(0.0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        AppBarSalesScreen(navController)

        Spacer(modifier = Modifier.height(8.dp))

        InputSection(clientName, product, quantity, unitValue,
            onNewClientChange = {
                if (it.length <= textMaxLength) clientName = it
                else Toast.makeText(context,
                    R.string.min_25_caracteres, Toast.LENGTH_SHORT)
                    .show()
            },
            onNewProductChange = {
                if (it.length <= textMaxLength) product = it
                else Toast.makeText(context, R.string.min_25_caracteres, Toast.LENGTH_SHORT)
                    .show()
            },
            onNewQuantityChange = {
                if (it.length <= quantityMaxLength) quantity = it
                else Toast.makeText(context, R.string.min_3_caracteres, Toast.LENGTH_SHORT)
                    .show()
            },
            onNewValueUnitChange = {
                if (it.length <= valueMaxLength) unitValue = it
                else Toast.makeText(context, R.string.min_8_caracteres, Toast.LENGTH_SHORT)
                    .show()
            },
            onAddItemClick = {
                if (clientName.isNotBlank() && product.isNotBlank() &&
                    quantity.isNotBlank() && unitValue.isNotBlank() &&
                    quantity.toDouble() > 0.0 && unitValue.toDouble() > 0.0
                ) {
                    sumTotalValue = (quantity.toInt() * unitValue.toDouble())
                    viewModel.addItem(
                        SaleItemEntity(
                            clientName,
                            product,
                            quantity.toInt(),
                            unitValue.toDouble(),
                            sumTotalValue
                        )
                    )
                    listTotalValues.add(sumTotalValue)
                    product = ""
                    quantity = ""
                    unitValue = ""
                } else {
                    Toast.makeText(
                        context,
                        "Complete corretamente todos os campos!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(4.dp))

        ListSection(data,
            onItemClick = {
                viewModel.removeItem(it)
                listTotalValues.remove(it.totalValue)
            })

        Spacer(modifier = Modifier.height(4.dp))

        Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(4.dp))

        BottomSalesScreen(navController, data)
    }
}

@Composable
fun AppBarSalesScreen(navController: NavController) {
    val viewModel = hiltViewModel<MainViewModel>()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            viewModel.removeAllSaleItems()
            listTotalValues.clear()
            navController.navigateUp()
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.fazer_uma_venda),
            fontWeight = FontWeight.Light,
            color = textColor,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputSection(
    newValueClientName: String,
    newValueProductName: String,
    newValueQuantity: String,
    newValueUnit: String,
    onNewClientChange: (String) -> Unit,
    onNewProductChange: (String) -> Unit,
    onNewQuantityChange: (String) -> Unit,
    onNewValueUnitChange: (String) -> Unit,
    onAddItemClick: () -> Unit
) {
    var totalItemValue by remember {
        mutableStateOf(Money.formatToReal(0.0))
    }

    Text(
        text = stringResource(R.string.cliente),
        color = textColor,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 8.dp)
    )
    PlaceHolderTextField(
        modifier = Modifier
            .padding(8.dp),
        value = newValueClientName,
        onValueChange = { onNewClientChange(it) },
        textStyle = MaterialTheme.typography.bodyMedium,
        placeholder = stringResource(R.string.escrever_nome_do_cliente),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Sentences
        )
    )

    Spacer(modifier = Modifier.height(8.dp))

    Divider(
        color = Color.Gray,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = stringResource(R.string.produto),
        color = textColor,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 8.dp)
    )
    PlaceHolderTextField(
        modifier = Modifier
            .padding(8.dp),
        value = newValueProductName,
        onValueChange = { onNewProductChange(it) },
        textStyle = MaterialTheme.typography.bodyMedium,
        placeholder = stringResource(R.string.nome_do_produto),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Sentences
        )
    )
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        PlaceHolderTextField(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            value = newValueQuantity,
            onValueChange = { onNewQuantityChange(it) },
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = stringResource(R.string.quantidade),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        PlaceHolderTextField(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            value = newValueUnit,
            onValueChange = {
                onNewValueUnitChange(it)
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = stringResource(R.string.valor_ex_9_99),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.valor_total_do_item),
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
            if (newValueQuantity.isNotBlank() && newValueUnit.isNotBlank()) {
                totalItemValue =
                    Money.formatToReal(newValueQuantity.toInt() * newValueUnit.toDouble())
                Text(
                    text = totalItemValue,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            } else {
                Text(
                    text = Money.formatToReal(0.0),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            onClick = {
                onAddItemClick()
                keyboardController?.hide()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryBlue,
                contentColor = textColor
            )
        ) {
            Text(text = stringResource(R.string.incluir))
        }
    }
}

@Composable
fun ListSection(
    data: State<List<SaleItemEntity>>,
    onItemClick: (SaleItemEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier.height(350.dp)
    ) {
        items(data.value) {  item ->
            SaleItem(item, onItemClick)
        }
    }
}

@Composable
fun BottomSalesScreen(navController: NavController, data: State<List<SaleItemEntity>>) {
    val viewModel = hiltViewModel<MainViewModel>()
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(R.string.total_de_itens),
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
            Text(
                text = data.value.size.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(R.string.valor_total),
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
            Text(
                text = Money.formatToReal(listTotalValues.sum()),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            onClick = {
                viewModel.removeAllSaleItems()
                listTotalValues.clear()
                navController.navigateUp()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryBlue,
                contentColor = textColor
            )
        ) {
            Text(text = stringResource(R.string.cancelar))
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            onClick = {
                if (!listTotalValues.isEmpty()) {
                    viewModel.copySaleToReport()
                    viewModel.removeAllSaleItems()
                    listTotalValues.clear()
                    navController.navigateUp()
                } else {
                    Toast.makeText(context, R.string.pedido_vazio, Toast.LENGTH_SHORT).show()
                }
            }, colors = ButtonDefaults.buttonColors(
                containerColor = primaryBlue,
                contentColor = textColor
            )
        ) {
            Text(text = stringResource(R.string.salvar))
        }
    }
}

@Composable
fun SaleItem(
    item: SaleItemEntity,
    onItemClick: (SaleItemEntity) -> Unit
) {
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

            IconButton(onClick = {
                onItemClick(item)
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
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
                        text = stringResource(R.string.valor),
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
                        text = stringResource(R.string.qtd),
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
                        text = stringResource(R.string.total),
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