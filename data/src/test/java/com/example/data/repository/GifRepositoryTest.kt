package com.example.data.repository

import com.example.data.datasource.GifDataSource
import com.example.data.entity.GifEntity
import com.example.data.entity.ImagesEntity
import com.example.data.entity.OriginalEntity
import com.example.domain.model.GifDetail
import com.example.libraries.common.exception.GifNotFountException
import com.example.libraries.common.result.GetResult
import com.example.libraries.test.BaseRobot
import com.example.libraries.test.dsl.GIVEN
import com.example.libraries.test.dsl.RUN_UNIT_TEST
import com.example.libraries.test.dsl.THEN
import com.example.libraries.test.dsl.WHEN
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GifRepositoryTest {

    private val robot = Robot()

    @Test
    fun test_successful_getGifDetail() = runTest {
        RUN_UNIT_TEST(robot) {
            GIVEN { mockSuccessfulGetGifDetail() }
            WHEN { callingGetGifDetail() }
            THEN { checkGifDetailSuccessfulResult() }
        }
    }

    @Test
    fun test_failure_getGifDetail() = runTest {
        RUN_UNIT_TEST(robot) {
            GIVEN { mockFailureGetGifDetail() }
            WHEN { callingGetGifDetail() }
            THEN { checkGifDetailFailureResult() }
        }
    }

    companion object {
        val gifEntity = GifEntity(
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

    class Robot : BaseRobot() {
        private val gifDataSource: GifDataSource = mockk()
        private lateinit var gifsRepository: GifsRepositoryImpl
        private lateinit var gifDetail: GifDetail
        private var exception: Throwable? = null
        private val testCoroutineContext = StandardTestDispatcher()

        fun mockSuccessfulGetGifDetail() {
            coEvery { gifDataSource.getGifDetail(any()) } answers { gifEntity }
        }

        fun mockFailureGetGifDetail() {
            coEvery { gifDataSource.getGifDetail(any()) } throws GifNotFountException
        }

        fun callingGetGifDetail() = runTest(testCoroutineContext) {
            gifsRepository = GifsRepositoryImpl(gifDataSource, testCoroutineContext)
            gifsRepository.getGifDetail("id").collect {
                when (it) {
                    is GetResult.Success -> gifDetail = it.data
                    is GetResult.Error -> exception = it.throwable
                    else -> {}
                }
            }
        }

        fun checkGifDetailSuccessfulResult() {
            assertEquals("id", gifDetail.id)
            assertEquals("title", gifDetail.title)
            assertEquals("username", gifDetail.username)
            assertEquals("A", gifDetail.rating)
        }

        fun checkGifDetailFailureResult() {
            assertTrue(exception is GifNotFountException)
        }
    }
}
