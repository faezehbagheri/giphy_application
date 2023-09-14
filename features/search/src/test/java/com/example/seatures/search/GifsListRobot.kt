package com.example.seatures.search

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.model.Gif
import com.example.domain.usecase.GetTrendingGifsUseCase
import com.example.features.search.GifsListScreen
import com.example.features.search.GifsListViewModel
import com.example.libraries.test.BaseRobot
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn

@OptIn(ExperimentalTestApi::class)
class GifsListRobot(private val composeContentTestRule: ComposeContentTestRule) : BaseRobot() {
    private val getTendingGifsUseCase: GetTrendingGifsUseCase = mockk()
    private lateinit var viewModel: GifsListViewModel
    private lateinit var pagingSource: TestPagingSource
    private var onNavigateToGifsDetails: (String) -> Unit = mockk()

    fun mockGetTrendingGifsUseCase(
        responseDelay: Long = 0,
        emitEmptyResult: Boolean = false,
        returnErrorOnFirstPage: Boolean = false,
        returnErrorOnSecondPage: Boolean = false,
    ) {
        pagingSource = TestPagingSource(
            responseDelay,
            emitEmptyResult,
            returnErrorOnFirstPage,
            returnErrorOnSecondPage
        )

        coEvery { getTendingGifsUseCase() } answers {
            Pager(
                config = PagingConfig(30),
                pagingSourceFactory = { pagingSource },
            ).flow.flowOn(Dispatchers.IO)
        }
    }

    fun createViewModel() {
        viewModel = GifsListViewModel(getTendingGifsUseCase)
    }

    fun mockNavigateToGifsDetails() {
        every { onNavigateToGifsDetails(any()) } returns Unit
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    fun createGifsListScreen() {
        composeContentTestRule.setContent {
            Scaffold {
                GifsListScreen(
                    viewModel = viewModel,
                    onNavigateToGifsDetails = onNavigateToGifsDetails
                )
            }
        }
    }

    fun waitUntilLoadingStateIsDisplayed() {
        composeContentTestRule.waitUntilExactlyOneExists(
            matcher = hasTestTag("loadingStateView"),
            timeoutMillis = 5000,
        )
    }

    fun waitUntilGifsListIsDisplayed() {
        composeContentTestRule.waitUntilExactlyOneExists(
            matcher = hasTestTag("gifsList"),
            timeoutMillis = 5000,
        )
        composeContentTestRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag("id: 1"),
            timeoutMillis = 5000,
        )
    }

    fun waitUntilErrorStateIsDisplayed() {
        composeContentTestRule.waitUntilExactlyOneExists(
            matcher = hasTestTag("errorState"),
            timeoutMillis = 5000,
        )
        composeContentTestRule.onNodeWithText("Try again").assertIsDisplayed()
    }

    fun clickOnGif() {
        composeContentTestRule.onNode(matcher = hasTestTag("id: 1")).performClick()
    }

    fun checkNavigateToUserDetailCalled() {
        verify { onNavigateToGifsDetails(any()) }
    }

    class TestPagingSource(
        private val responseDelay: Long,
        private val emitEmptyResult: Boolean,
        private val returnErrorOnFirstPage: Boolean,
        private val returnErrorOnSecondPage: Boolean,
    ) : PagingSource<Int, Gif>() {
        override fun getRefreshKey(state: PagingState<Int, Gif>): Int? = null

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gif> {
            val key = params.key ?: 0
            val gifs = if (emitEmptyResult) {
                listOf()
            } else {
                buildList {
                    repeat(5) {
                        add(Gif(id = "id: $it", thumbnail = "thumbnail"))
                    }
                }
            }
            val nextKey = if (key == 2) null else key + 1
            delay(responseDelay)
            if (returnErrorOnFirstPage || nextKey == 2 && returnErrorOnSecondPage) {
                return LoadResult.Error(Exception())
            }
            return LoadResult.Page(gifs, null, nextKey)
        }
    }
}