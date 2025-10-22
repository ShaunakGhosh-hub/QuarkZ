package com.example.flash.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flash.data.Item
import java.text.NumberFormat

@Composable
fun CartScreen(
    uiState: FlashUiState,
    onIncreaseItem: (Item) -> Unit,
    onDecreaseItem: (Item) -> Unit,
    onDeleteItem: (Item) -> Unit
) {
    if (uiState.cartItems.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Your cart is empty!", style = MaterialTheme.typography.headlineSmall)
        }
    } else {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(uiState.cartItems.entries.toList()) { (item, quantity) ->
                    CartItemRow(
                        item = item,
                        quantity = quantity,
                        onIncrease = { onIncreaseItem(item) },
                        onDecrease = { onDecreaseItem(item) },
                        onDelete = { onDeleteItem(item) }
                    )
                    Divider()
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Total: ${formatCurrency(uiState.cartTotal)}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun CartItemRow(
    item: Item,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Item Name
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        // Quantity Controls
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Corrected line
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                .padding(horizontal = 4.dp)
        ) {
            IconButton(onClick = onDecrease, modifier = Modifier.size(24.dp)) {
                Text("-", fontSize = 18.sp)
            }
            Text("$quantity", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            IconButton(onClick = onIncrease, modifier = Modifier.size(24.dp)) {
                Text("+", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Delete Button
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Remove Item", tint = MaterialTheme.colorScheme.error)
        }
    }
}

private fun formatCurrency(price: Double): String {
    // A simple currency formatter for demonstration
    return NumberFormat.getCurrencyInstance().format(price)
}