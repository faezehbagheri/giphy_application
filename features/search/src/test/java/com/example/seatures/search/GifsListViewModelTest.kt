package com.example.seatures.search

import androidx.paging.PagingData
import com.example.domain.model.Gif
import com.example.domain.usecase.GetTrendingGifsUseCase
import com.example.features.search.GifsListViewModel
import com.example.libraries.test.BaseRobot
import com.example.libraries.test.dsl.GIVEN
import com.example.libraries.test.dsl.RUN_UNIT_TEST
import com.example.libraries.test.dsl.THEN
import com.example.libraries.test.dsl.WHEN
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GifsListViewModelTest {

    private val robot = Robot()

    @Test
    fun test_getTrendingGifs() {
        RUN_UNIT_TEST(robot) {
            GIVEN { mockGetTrendingGifsUseCase() }
            WHEN { createViewModel() }
            THEN { checkGetGifsListIsCalled() }
        }
    }


    class Robot : BaseRobot() {
        val gifsLisGifsUseCase = mockk<GetTrendingGifsUseCase>()
        private lateinit var viewModel: GifsListViewModel

        fun mockGetTrendingGifsUseCase() {
            coEvery { gifsLisGifsUseCase() } answers {
                flow {
                    val gifs = buildList {
                        repeat(5) {
                            add(Gif(id = "id: $it", thumbnail = "thumbnail"))
                        }
                    }
                    emit(PagingData.from(gifs))
                }.flowOn(Dispatchers.IO)
            }
        }

        fun createViewModel() {
            viewModel = GifsListViewModel(gifsLisGifsUseCase)
        }

        fun checkGetGifsListIsCalled() {
            verify(exactly = 1) { gifsLisGifsUseCase() }
        }
    }
}