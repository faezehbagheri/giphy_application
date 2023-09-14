package com.example.seatures.search

import androidx.compose.ui.test.junit4.createComposeRule
import com.example.libraries.test.dsl.AND
import com.example.libraries.test.dsl.GIVEN
import com.example.libraries.test.dsl.RUN_UI_TEST
import com.example.libraries.test.dsl.THEN
import com.example.libraries.test.dsl.WHEN
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GifsListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val robot = GifsListRobot(composeTestRule)

    @Test
    fun test_loadingState() {
        RUN_UI_TEST(robot) {
            GIVEN { mockGetTrendingGifsUseCase(responseDelay = 2000) }
            WHEN { createViewModel() }
            AND { createGifsListScreen() }
            THEN { waitUntilLoadingStateIsDisplayed() }
        }
    }

    @Test
    fun test_errorState() {
        RUN_UI_TEST(robot) {
            GIVEN { mockGetTrendingGifsUseCase(returnErrorOnFirstPage = true) }
            WHEN { createViewModel() }
            AND { createGifsListScreen() }
            THEN { waitUntilErrorStateIsDisplayed() }
        }
    }

    @Test
    fun test_gifsListState() {
        RUN_UI_TEST(robot) {
            GIVEN { mockGetTrendingGifsUseCase() }
            AND { mockNavigateToGifsDetails() }
            WHEN { createViewModel() }
            AND { createGifsListScreen() }
            THEN { waitUntilGifsListIsDisplayed() }
            AND { clickOnGif() }
            AND { checkNavigateToUserDetailCalled() }
        }
    }
}