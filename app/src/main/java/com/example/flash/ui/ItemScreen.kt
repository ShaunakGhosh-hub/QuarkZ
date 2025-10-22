package com.example.flash.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.flash.data.Item

@Composable
fun ItemScreen(
    flashViewModel: FlashViewModel,
    onItemClicked: (Item) -> Unit // Add navigation callback
) {
    val uiState by flashViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.products) { item ->
                    ItemCard(
                        item = item,
                        onItemClicked = onItemClicked, // Pass the navigation callback
                        onAddToCartClicked = { flashViewModel.addToCart(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemCard(
    item: Item,
    onItemClicked: (Item) -> Unit,
    onAddToCartClicked: () -> Unit
) {
    val context = LocalContext.current
    // Calculate discounted price using data from Firestore
    val discountedPrice = item.price * (1 - (item.discountPercent / 100.0))

    Column(
        modifier = Modifier.padding(5.dp).clickable { onItemClicked(item) }
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFCDD2), Color(0xFFE1BEE7))
                    )
                )
            ) {
                // Use Coil's AsyncImage to load the image from the URL
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )

                // Show discount badge if there is a discount
                if (item.discountPercent > 0) {
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
                                text = "${item.discountPercent}% OFF",
                                color = Color.White,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
        // Display the product name directly from the String property
        Text(
            text = item.name,
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
                // Show original price with a strikethrough if there's a discount
                if (item.discountPercent > 0) {
                    Text(
                        text = "Rs ${item.price}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
                Text(
                    text = "Rs ${"%.2f".format(discountedPrice)}",
                    fontSize = 12.sp,
                    color = Color(0xFF388E3C)
                )
                            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .clickable {
                    onAddToCartClicked()
                    // Update Toast message to use the item name
                    Toast
                        .makeText(
                            context,
                            "${item.name} added to cart",
                            Toast.LENGTH_SHORT
                        )
                        .show()
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
