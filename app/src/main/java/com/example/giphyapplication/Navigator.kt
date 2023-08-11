package com.example.giphyapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.features.gifdetail.GifDetailsScreen
import com.example.features.gifdetail.GifDetailsViewModel
import com.example.libraries.navigation.DestinationArgs
import com.example.libraries.navigation.Destinations
import com.example.features.search.GifsListScreen
import com.example.features.search.GifsListViewModel

@Composable
fun Navigator(
    navController: NavHostController
) {
    val actions = remember { Actions(navController) }
    NavHost(
        navController = navController,
        startDestination = Destinations.GIFS_LIST
    ) {
        composable(Destinations.GIFS_LIST) {
            val viewModel: GifsListViewModel = hiltViewModel()
            GifsListScreen(
                viewModel = viewModel,
                onNavigateToGifsDetails = actions.openGifDetailsScreen,
            )
        }
        composable(
            route = "${Destinations.GIF_DETAILS}/{${DestinationArgs.GIF_ID}}",
            arguments = listOf(navArgument(DestinationArgs.GIF_ID) { type = NavType.StringType })
        ) {
            val viewModel: GifDetailsViewModel = hiltViewModel()
            GifDetailsScreen(
                viewModel = viewModel,
                onNavigateUp = actions.navigateBack
            )
        }
    }

}

class Actions(private val navHostController: NavHostController) {

    val openGifDetailsScreen: (id: String) -> Unit = {
        navHostController.navigate("${Destinations.GIF_DETAILS}/$it")
    }

    val navigateBack: () -> Unit = { navHostController.popBackStack() }
}