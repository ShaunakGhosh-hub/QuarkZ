package com.example.flash.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class FlashAppScreen (val title:String){
    Start("Welcome to Quarkz"),
    Items("Choose Items"),
    Cart("Shopping Cart")
}

var canNavigateBack=false

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashApp(flashViewModel: FlashViewModel= viewModel(),
             navController: NavHostController=rememberNavController()
    ) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen=FlashAppScreen.valueOf(
        backStackEntry?.destination?.route?:FlashAppScreen.Start.name
    )
    canNavigateBack=navController.previousBackStackEntry!=null
    Scaffold(
        topBar={
                    TopAppBar(title = {
                        Text(text = currentScreen.title,
                            fontSize = 26.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    },
                        navigationIcon = {
                            if(canNavigateBack){
                                IconButton(onClick = {navController.navigateUp()}) {
                                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                                }
                            }

                        })
                },
        bottomBar={
            FlashAppBar(navController=navController)
        }

    ) {
        NavHost(navController=navController,startDestination=FlashAppScreen.Start.name,
            Modifier.padding(it)
        ){
            composable(route=FlashAppScreen.Start.name){
                StartScreen(flashViewModel=flashViewModel,
                    onCategoryClicked={
                        flashViewModel.updateSelectedCategory(it)
                        navController.navigate(FlashAppScreen.Items.name)
                    })
            }
            composable(route=FlashAppScreen.Items.name){
                ItemScreen(flashViewModel=flashViewModel)
            }

        }
    }

}

@Composable
fun FlashAppBar(navController: NavHostController){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
            .padding(
                horizontal = 70.dp,
                vertical = 10.dp
            )
    ) {
        Column (


            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.clickable {
                navController.navigate(FlashAppScreen.Start.name){
                    popUpTo(0)
                }
            }

        ){

            Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home")
            Text(text = "Home", fontSize = 10.sp)
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.clickable {
                navController.navigate(FlashAppScreen.Cart.name){
                    popUpTo(0)
                }


            }
        ){
            Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = "Cart")
            Text(text = "Home", fontSize = 10.sp)
        }

    }
}