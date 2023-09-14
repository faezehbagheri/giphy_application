package com.example.features.gifdetail

import androidx.lifecycle.SavedStateHandle
import com.example.domain.model.GifDetail
import com.example.domain.usecase.GetGifDetailUseCase
import com.example.libraries.common.result.GetResult
import com.example.libraries.navigation.DestinationArgs
import com.example.libraries.test.BaseRobot
import com.example.libraries.test.dsl.AND
import com.example.libraries.test.dsl.GIVEN
import com.example.libraries.test.dsl.RUN_UNIT_TEST
import com.example.libraries.test.dsl.THEN
import com.example.libraries.test.dsl.WHEN
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GifDetailViewModelTest {

    private val robot = Robot()

    @Test
    fun test_getGifDetail() {
        RUN_UNIT_TEST(robot) {
            GIVEN { mockGetGifDetailUseCase() }
            WHEN { createViewModel() }
            THEN { checkGetGifDetailIsCalled() }
        }
    }

    @Test
    fun test_retry() {
        RUN_UNIT_TEST(robot) {
            GIVEN { mockGetGifDetailUseCase() }
            WHEN { createViewModel() }
            AND { retry() }
            THEN { checkGetGifDetailIsCalled(times = 2) }
        }
    }

    class Robot : BaseRobot() {
        private val getGifDetailUseCase = mockk<GetGifDetailUseCase>()
        private lateinit var viewModel: GifDetailsViewModel

        fun mockGetGifDetailUseCase() {
            coEvery { getGifDetailUseCase(any()) } returns flow {
                emit(GetResult.Success(gifDetail))
            }.flowOn(Dispatchers.IO)
        }

        fun createViewModel() {
            val savedStateHandle: SavedStateHandle = mockk {
                every { this@mockk.get<String>(DestinationArgs.GIF_ID) } returns "id"
            }
            viewModel = GifDetailsViewModel(savedStateHandle, getGifDetailUseCase)
        }

        fun retry() {
            viewModel.retry()
        }

        fun checkGetGifDetailIsCalled(times: Int = 1) {
            coVerify(exactly = times) { getGifDetailUseCase(any()) }
        }
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