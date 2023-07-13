package com.example.giphyapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.compose.composable
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.giphyapplication.ui.features.gifdetails.GifDetailsRoute
import com.example.giphyapplication.ui.features.gifdetails.GifDetailsViewModel
import com.example.giphyapplication.ui.features.gifslist.GifsListRoute
import com.example.giphyapplication.ui.features.gifslist.GifsListViewModel
import com.example.giphyapplication.ui.theme.GiphyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val gifsListViewModel by viewModels<GifsListViewModel>()
    private val gifsDetailsViewModel by viewModels<GifDetailsViewModel>()

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
                    composable(route = "gif_details/{id}", arguments = listOf(navArgument("id") {
                        type = NavType.StringType
                    })) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id") ?: "0"
                        val gif =
                            gifsListViewModel.uiState.collectAsState().value.gifsList.first { it.id == id }
                        GifDetailsRoute(
                            gif = gif,
                            viewModel = gifsDetailsViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}