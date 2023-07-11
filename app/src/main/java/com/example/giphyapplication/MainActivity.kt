package com.example.giphyapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.compose.composable
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.giphyapplication.ui.features.gifslist.GifsListRoute
import com.example.giphyapplication.ui.features.gifslist.GifsListViewModel
import com.example.giphyapplication.ui.theme.GiphyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val gifsListViewModel by viewModels<GifsListViewModel>()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            GiphyApplicationTheme {
                NavHost(
                    navController = navController,
                    startDestination = "gifs_list"
                ) {
                    composable("gifs_list") {
                        GifsListRoute(viewModel = gifsListViewModel, navController = navController)
                    }
                }
            }
        }
    }
}