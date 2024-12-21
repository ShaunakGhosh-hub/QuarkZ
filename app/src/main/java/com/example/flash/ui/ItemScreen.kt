package com.example.flash.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flash.data.DataSource

@Composable
fun ItemScreen(flashViewModel: FlashViewModel) {
    val flashUiState by flashViewModel.uiState.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(45.dp), // Add space at the top
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(DataSource.loadItems(
            flashUiState.selectedCategory
        )) {
            ItemCard(
                stringResourceId = it.stringResourceId,
                imageResourceId = it.imageResourceId,
                itemQuantity = it.itemQuantityId,
                itemPrice = it.itemPrice
            )
        }
    }
}

@Composable
fun ItemCard(
    stringResourceId: Int,
    imageResourceId: Int,
    itemQuantity: String,
    itemPrice: Int
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .width(160.dp)
            .padding(5.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent // Transparent for gradient effect
            ),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFFFCDD2), Color(0xFFE1BEE7))
                        )
                    )
            ) {
                Image(
                    painter = painterResource(id = imageResourceId),
                    contentDescription = stringResource(id = stringResourceId),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFF5722)),
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(
                            text = "25% OFF",
                            color = Color.White,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
        Text(
            text = stringResource(id = stringResourceId),
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            maxLines = 1,
            textAlign = TextAlign.Left
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Rs $itemPrice",
                    fontSize = 12.sp,
                    color = Color.Red,
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = "Rs ${itemPrice * 75 / 100}",
                    fontSize = 12.sp,
                    color = Color.Green
                )
            }
            Text(
                text = itemQuantity,
                fontSize = 12.sp,
                color = Color.Blue
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .clickable {
                    Toast.makeText(context, "The card was clicked", Toast.LENGTH_SHORT).show()
                },
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF64B5F6), Color(0xFF42A5F5))
                        )
                    )
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Add to Cart",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}
