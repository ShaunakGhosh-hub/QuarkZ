package com.example.flash.ui

import androidx.compose.foundation.layout.width
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.flash.data.Category



data class BestSeller(
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val price: String? = null
)

@Composable
fun StartScreen(
    flashViewModel: FlashViewModel,
    onCategoryClicked: (Category) -> Unit,
    onBestSellerClicked: (BestSeller) -> Unit = {},
    onCartClicked: () -> Unit = {}, // Add cart navigation
    onAuthClicked: () -> Unit = {}  // Add auth navigation
) {
    val uiState by flashViewModel.uiState.collectAsState()

    // Use a LazyVerticalGrid with a single column to make the entire screen scrollable
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxSize()
    ) {
        // --- 1. Hero Section ---
        item {
            HeroSection(onClickShopNow = {
                // TODO: Define what happens when "SHOP NOW" is clicked.
            })
        }

        // --- 2. "About" Section ---
        item {
            AboutSection()
        }

        // --- 3. Marquee Section ---
        item {
            MarqueeText()
        }

        // --- 4. Categories Section Title ---
        item {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
            )
        }

        // --- 5. Categories Grid ---
        if (uiState.isLoading && uiState.categories.isEmpty()) {
            item {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(32.dp)) {
                    CircularProgressIndicator()
                }
            }
        } else {
            // Display categories in a 2-column grid format
            items(uiState.categories.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (category in rowItems) {
                        CategoryCard(
                            category = category,
                            onClick = { onCategoryClicked(category) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // If there's an odd number of items, add a spacer to fill the remaining space
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }


        item {
            ClearSection(onClickShopNow = {
                // TODO: Define what happens when "SHOP NOW" is clicked.
            })
        }

        item {
            CollaborationSection(onClickShopNow = {
                // TODO: Define what happens when "SHOP NOW" is clicked for collaboration
            })
        }

        item {
            BestSellersSection()
        }


    }
}

@Composable
fun HeroSection(onClickShopNow: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        // The background image - using the specific image URL from the first code
        AsyncImage(
            model = "https://scontent.fblr5-1.fna.fbcdn.net/v/t39.30808-6/529332216_791939623227441_6497289830863230272_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=833d8c&_nc_ohc=TcZtQGIa1jQQ7kNvwHpSnWF&_nc_oc=AdmvMG8bCbkk00ZU46LKU-H0v69Z6DtLNDGJEgt8XEFc-KwgJVlimCGSXs5oC-uIO50&_nc_zt=23&_nc_ht=scontent.fblr5-1.fna&_nc_gid=rT_LpWHc4rrc_7_B-KWspg&oh=00_AfdkDUwOFhEPXmvkFfbPtp9c-vkWkrwKxnn48XeFDN3suQ&oe=68F5DCCC",
            contentDescription = "Hero Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // A semi-transparent gradient overlay to make text more readable
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 400f
                    )
                )
        )

        // The Column for all the text and the button
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "2025-26 SEASON KITS",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Feel the new season. Gear up like it's matchday everyday.",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.clickable(onClick = onClickShopNow),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SHOP NOW",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Shop Now",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun AboutSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.SportsSoccer, // Placeholder icon
            contentDescription = "Logo",
            tint = Color(0xFFADFF2F) // Lime green color
        )
        Text(
            text = "FULL TIME STORE",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Football is more than just a sport, it's a passion, a community, and a way of life. In recent years, the love for football has been growing rapidly across India, reaching fans not only in major cities but also in tier 3 and tier 4 towns, where the spirit and enthusiasm for the game are just as powerful.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MarqueeText() {
    val text = "  UPTO 70% OFF ON CLEARANCE SALE  "
    val infiniteTransition = rememberInfiniteTransition(label = "MarqueeTransition")

    // Animate the text's offset in a continuous loop
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "MarqueeOffset"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
    ) {
        // Draw the text multiple times to create a seamless scroll effect
        Text(
            text = text.repeat(4),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .graphicsLayer {
                    translationX = offset * this.size.width / 2f
                },
            maxLines = 1
        )
    }
}

@Composable
fun CategoryCard(category: Category, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box {
            AsyncImage(
                model = category.imageUrl,
                contentDescription = category.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                            startY = 200f
                        )
                    )
            )
            Text(
                text = category.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
            )
        }
    }
}
@Composable
fun ClearSection(onClickShopNow: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        // The background image - using the specific image URL from the first code
        AsyncImage(
            model = "https://images.unsplash.com/photo-1612872087720-bb876e2e67d1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2000&q=80",
            contentDescription = "Hero Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // A semi-transparent gradient overlay to make text more readable
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 400f
                    )
                )
        )

        // The Column for all the text and the button
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "Clearance Sale",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Score top jerseys and gear at unbeatable prices. Limited stock, limited time grab yours before they’re gone!",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.clickable(onClick = onClickShopNow),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SHOP NOW",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Shop Now",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun CollaborationSection(onClickShopNow: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        // The background image for Barcelona X Travis Scott collaboration
        AsyncImage(
            model = "https://fulltimestore.in/cdn/shop/files/event15-min.jpg?v=1750274328&width=1080",
            contentDescription = "Barcelona X Travis Scott Collaboration",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // A semi-transparent gradient overlay to make text more readable
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 300f
                    )
                )
        )

        // The Column for all the text and the button
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "BARCELONA X TRAVIS SCOTT",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Where football legacy meets Cactus Jack's iconic streetwear edge",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.clickable(onClick = onClickShopNow),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SHOP NOW",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Shop Now",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun BestSellersSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "F:T",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "BEST SELLERS",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "VIEW ALL",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Horizontal Scrollable Best Sellers - 4 items
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            // Item 1
            item {
                BestSellerCard(
                    imageUrl = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSfDLXHJeqp9GKKbb0HJIeX6MyvBSj0eaKO4zh2NfPu6MInq2FK",
                    title = "BARCELONA AWAY 25-26",
                    description = "Official 2025-26 season away jersey",
                    price = "₹4,299"
                )
            }
            // Item 2
            item {
                BestSellerCard(
                    imageUrl = "https://about.puma.com/sites/default/files/styles/dd_hero_tablet/public/media/news/images/25aw-pr-ts-football-man-city-away-matchwear-product-only-kv-0232-16x9-1920x1080px.jpg?itok=1EKwK2HB",
                    title = "MAN CITY AWAY 25-26",
                    description = "Limited edition collaboration",
                    price = "₹6,999"
                )
            }
            // Item 3
            item {
                BestSellerCard(
                    imageUrl = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTI15_oHZtU60UyNvSjykrG6hnxel7d0Q34-JlR22Y5lIOjwzh9",
                    title = "REAL MADRID AWAY 25-26",
                    description = "Elegant away kit with premium fabric",
                    price = "₹4,199"
                )
            }
            // Item 4
            item {
                BestSellerCard(
                    imageUrl = "https://gridinsoft.com/img/scr/site/vamoskits-com.jpeg",
                    title = "LIVERPOOL AWAY 25-26",
                    description = "Striking third kit with modern design",
                    price = "₹4,499"
                )
            }
        }
    }
}

@Composable
fun BestSellerCard(
    imageUrl: String,
    title: String,
    description: String,
    price: String
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.9f)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Price tag
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = price,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            // Product Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}