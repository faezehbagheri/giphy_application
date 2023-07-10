package com.example.giphyapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.compose.composable
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.giphyapplication.ui.theme.GiphyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            GiphyApplicationTheme {
                NavigationComponent(navController)
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "gifs_list"
    ) {
        composable("gifs_list") {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GiphyApplicationTheme {
        NavigationComponent(rememberNavController())
    }
}