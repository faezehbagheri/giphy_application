package com.example.data

import com.example.data.datasource.GifDataSource
import com.example.data.entity.GifEntity
import com.example.data.entity.ImagesEntity
import com.example.data.entity.OriginalEntity
import com.example.data.repository.GifsRepositoryImpl
import com.example.libraries.common.exception.GifNotFountException
import com.example.libraries.common.result.GetResult
import com.example.test.BaseRobot
import com.example.test.dsl.GIVEN
import com.example.test.dsl.RUN_UNIT_TEST
import com.example.test.dsl.THEN
import com.example.test.dsl.WHEN
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GifRepositoryTest {

    private val robot = Robot()

    @Test
    fun test_successful_getUserDetail() {
        RUN_UNIT_TEST(robot) {
            GIVEN { mockSuccessfulGetGifDetail() }
            WHEN { callGetGifDetail() }
            THEN { checkUserDetailSuccessfulResult() }
        }
    }

    @Test
    fun test_failure_getUserDetail() {
        RUN_UNIT_TEST(robot) {
            GIVEN { mockFailureGetGifDetail() }
            WHEN { callGetGifDetail() }
            THEN { checkUserDetailFailureResult() }
        }
    }

    private class Robot : BaseRobot() {

        private val gifDataSource: GifDataSource = mockk()
        private val gifsRepository = GifsRepositoryImpl(gifDataSource)
        private lateinit var getGifsList: List<GifEntity>
        private var gifNotFountException: GifNotFountException? = null

        fun mockSuccessfulGetGifDetail() {
            coEvery { gifDataSource.getGifDetail(any()) } answers {
                GifEntity(
                    id = "id",
                    images = ImagesEntity(
                        original = OriginalEntity(
                            mp4 = "",
                            url = "",
                            webp = null,
                        )
                    ),
                    rating = "A",
                    title = "title",
                    type = "type",
                    url = "url",
                    username = "username",
                )
            }
        }

        fun mockFailureGetGifDetail() {
            coEvery { gifDataSource.getGifDetail(any()) } throws GifNotFountException
        }

        fun callGetGifDetail() = runBlocking {
            try {
                gifsRepository.getGifById("id").collect{
//                    if(it == GetResult.Success){
//                        getGifsList.data
//                    }
                }
            } catch (e: GifNotFountException) {
                gifNotFountException = e
            }
        }

        fun checkUserDetailSuccessfulResult() {
            assertTrue(getGifsList.isNotEmpty())
        }

        fun checkUserDetailFailureResult() = runBlocking {
            assertTrue(gifNotFountException != null)
        }
    }
}