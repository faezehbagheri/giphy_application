package com.example.features.gifdetail

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
class GifDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val robot = GifDetailRobot(composeTestRule)

    @Test
    fun test_loadingState() {
        RUN_UI_TEST(robot) {
            GIVEN { mockLoadingState() }
            WHEN { createViewModel() }
            AND { createGifDetailScreen() }
            THEN { checkLoadingStateIsDisplayed() }
        }
    }

    @Test
    fun test_errorState() {
        RUN_UI_TEST(robot) {
            GIVEN { mockFailureGetGifDetailUseCase() }
            WHEN { createViewModel() }
            AND { createGifDetailScreen() }
            THEN { checkErrorStateIsDisplayed() }
        }
    }

    @Test
    fun test_successfulGetGifDetail() {
        RUN_UI_TEST(robot) {
            GIVEN { mockSuccessfulGetGifDetailUseCase() }
            WHEN { createViewModel() }
            AND { createGifDetailScreen() }
            THEN { checkGifDetailContentIsDisplayed() }
        }
    }

    @Test
    fun test_navigateUp() {
        RUN_UI_TEST(robot) {
            GIVEN { mockSuccessfulGetGifDetailUseCase() }
            AND { mockNavigateUp() }
            WHEN { createViewModel() }
            AND { createGifDetailScreen() }
            AND { clickOnBackButton() }
            THEN { checkBackButtonIsClicked() }
        }
    }
}