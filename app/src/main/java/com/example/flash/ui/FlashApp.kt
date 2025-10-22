package com.example.flash.ui

import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flash.data.ProductDetail
import com.example.flash.data.ProductVariant

// Add ProductDetail screen to your enum
enum class FlashAppScreen(val title: String) {
    Start("Welcome to QuarkZ"),
    Items("Choose Items"),
    ProductDetail("Product Details"),
    Cart("Shopping Cart"),
    Profile("My Profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashApp(
    flashViewModel: FlashViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FlashAppScreen.valueOf(
        backStackEntry?.destination?.route ?: FlashAppScreen.Start.name
    )

    val uiState by flashViewModel.uiState.collectAsState()
    val selectedProduct by flashViewModel.selectedProduct.collectAsState() // Collect the state here

    Scaffold(
        topBar = {
            FlashTopAppBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            FlashBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FlashAppScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = FlashAppScreen.Start.name) {
                StartScreen(
                    flashViewModel = flashViewModel,
                    onCategoryClicked = { category ->
                        flashViewModel.selectCategory(category)
                        navController.navigate(FlashAppScreen.Items.name)
                    },
                    onBestSellerClicked = { bestSeller ->
                        // Convert BestSeller to ProductDetail and navigate
                        val productDetail = ProductDetail(
                            id = bestSeller.id,
                            name = bestSeller.title,
                            brand = "Official",
                            categoryName = "Best Seller",
                            price = bestSeller.price?.replace("₹", "")?.replace(",", "")?.toDoubleOrNull() ?: 0.0,
                            description = bestSeller.description,
                            discountPercent = 0,
                            imageUrl = bestSeller.imageUrl,
                            variants = listOf(
                                ProductVariant("S", 5),
                                ProductVariant("M", 3),
                                ProductVariant("L", 1),
                                ProductVariant("XL", 0)
                            )
                        )
                        // Store the product in ViewModel or pass as argument
                        flashViewModel.setSelectedProduct(productDetail)
                        navController.navigate(FlashAppScreen.ProductDetail.name)
                    }
                )
            }
            composable(route = FlashAppScreen.Items.name) {
                ItemScreen(
                    flashViewModel = flashViewModel,
                    onItemClicked = { item ->
                        // Convert Item to ProductDetail and navigate
                        val productDetail = ProductDetail(
                            id = item.name, // Use proper ID from your data
                            name = item.name,
                            brand = item.brand,
                            categoryName = item.categoryName,
                            price = item.price,
                            description = item.description,
                            discountPercent = item.discountPercent,
                            imageUrl = item.imageUrl,
                            variants = listOf(
                                ProductVariant("S", 5),
                                ProductVariant("M", 3),
                                ProductVariant("L", 1),
                                ProductVariant("XL", 0)
                            )
                        )
                        flashViewModel.setSelectedProduct(productDetail)
                        navController.navigate(FlashAppScreen.ProductDetail.name)
                    }
                )
            }
            composable(route = FlashAppScreen.ProductDetail.name) {
                val context = LocalContext.current // Add this
                if (selectedProduct != null) {
                    ProductDetailScreen(
                        product = selectedProduct!!,
                        relatedProducts = emptyList(),
                        onBackClicked = { navController.navigateUp() },
                        onAddToCart = { product, size ->
                            flashViewModel.addToCartWithSize(product, size)
                            Toast.makeText(context, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
                        },
                        onRelatedProductClicked = { productId ->
                            // You can navigate to related product here later
                        }
                    )
                } else {
                    navController.navigateUp()
                }
            }

            composable(route = FlashAppScreen.Cart.name) {
                CartScreen(
                    uiState = uiState,
                    onIncreaseItem = { item -> flashViewModel.addToCart(item) },
                    onDecreaseItem = { item -> flashViewModel.removeFromCart(item) },
                    onDeleteItem = { item -> flashViewModel.deleteFromCart(item) }
                )
            }
            composable(route = FlashAppScreen.Profile.name) {
                AuthScreen(authViewModel = authViewModel)
            }
        }
    }
}

// Rest of your existing code remains the same...
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashTopAppBar(
    currentScreenTitle: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = currentScreenTitle,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun FlashBottomAppBar(navController: NavHostController) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        // Home Button
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.clickable {
                navController.navigate(FlashAppScreen.Start.name) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        ) {
            Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home")
            Text(text = "Home", fontSize = 10.sp)
        }

        // Cart Button
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.clickable {
                navController.navigate(FlashAppScreen.Cart.name) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        ) {
            Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = "Cart")
            Text(text = "Cart", fontSize = 10.sp)
        }

        // Profile Button
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.clickable {
                navController.navigate(FlashAppScreen.Profile.name) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        ) {
            Icon(imageVector = Icons.Outlined.Person, contentDescription = "Profile")
            Text(text = "Profile", fontSize = 10.sp)
        }
    }
}