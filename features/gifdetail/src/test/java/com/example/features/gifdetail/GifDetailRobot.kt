package com.example.features.gifdetail

import GifDetailsScreen
import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import com.example.domain.model.GifDetail
import com.example.domain.usecase.GetGifDetailUseCase
import com.example.libraries.common.result.GetResult
import com.example.libraries.navigation.DestinationArgs
import com.example.libraries.test.BaseRobot
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow

class GifDetailRobot(private val composeContentTestRule: ComposeContentTestRule) : BaseRobot() {
    private val getGifDetailUseCase = mockk<GetGifDetailUseCase>()
    private lateinit var viewModel: GifDetailsViewModel
    private var navigateUp: () -> Unit = mockk()

    fun mockSuccessfulGetGifDetailUseCase() {
        coEvery { getGifDetailUseCase(any()) } returns flow { emit(GetResult.Success(gifDetail)) }
    }

    fun mockFailureGetGifDetailUseCase() {
        coEvery { getGifDetailUseCase(any()) } returns flow {
            emit(GetResult.Error(Exception("exception")))
        }
    }

    fun mockLoadingState() {
        coEvery { getGifDetailUseCase(any()) } returns flow { emit(GetResult.Loading) }
    }

    fun createViewModel() {
        val savedStateHandle: SavedStateHandle = mockk {
            every { this@mockk.get<String>(DestinationArgs.GIF_ID) } returns "id"
        }
        viewModel = GifDetailsViewModel(savedStateHandle, getGifDetailUseCase)
    }

    fun mockNavigateUp() {
        every { navigateUp() } returns Unit
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    fun createGifDetailScreen() {
        composeContentTestRule.setContent {
            Scaffold {
                GifDetailsScreen(viewModel = viewModel, onNavigateUp = navigateUp)
            }
        }
    }

    fun checkLoadingStateIsDisplayed() {
        composeContentTestRule.onNodeWithText("loadingStateView").assertIsDisplayed()
    }

    fun checkErrorStateIsDisplayed() {
        composeContentTestRule.onNodeWithText("errorState").assertIsDisplayed()
        composeContentTestRule.onNodeWithText("Try again").assertIsDisplayed()
    }

    fun checkGifDetailIsDisplayed() {
        composeContentTestRule.onNodeWithContentDescription("gif").assertIsDisplayed()
        composeContentTestRule.onNodeWithText("title").assertIsDisplayed()
        composeContentTestRule.onNodeWithText("Rating: ").assertIsDisplayed()
        composeContentTestRule.onNodeWithText("A").assertIsDisplayed()
        composeContentTestRule.onNodeWithText("Username: ").assertIsDisplayed()
        composeContentTestRule.onNodeWithText("username").assertIsDisplayed()
    }

    fun checkOnBackButtonClicked() {
        composeContentTestRule.onNodeWithContentDescription("back icon").performClick()
    }

    fun checkBackButtonIsClicked() {
        verify { navigateUp() }
    }

    companion object {
        val gifDetail = GifDetail(
            id = "id",
            gif = "url",
            username = "username",
            title = "title",
            rating = "A",
        )
    }
}