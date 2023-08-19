package com.example.data

import com.example.data.datasource.GifDataSourceImpl
import com.example.data.entity.GifEntity
import com.example.data.entity.GifResponseEntity
import com.example.data.entity.ImagesEntity
import com.example.data.entity.MetaEntity
import com.example.data.entity.OriginalEntity
import com.example.data.entity.PaginationEntity
import com.example.data.remote.GifService
import com.example.libraries.common.exception.GifNotFountException
import com.example.libraries.test.BaseRobot
import com.example.libraries.test.dsl.GIVEN
import com.example.libraries.test.dsl.RUN_UNIT_TEST
import com.example.libraries.test.dsl.THEN
import com.example.libraries.test.dsl.WHEN
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GifDataSourceTest {

    private val robot = Robot()

    @Test
    fun test_successful_getGifDetail() {
        RUN_UNIT_TEST(robot) {
            GIVEN { mockSuccessfulGetGifDetail() }
            WHEN { callingGetGifDetail() }
            THEN { checkGifDetailSuccessfulResult() }
        }
    }

    @Test
    fun test_failure_getGifDetail() {
        RUN_UNIT_TEST(robot) {
            GIVEN { mockFailureGetTrendingGifDetail() }
            WHEN { callingGetGifDetail() }
            THEN { checkGifDetailFailureResult() }
        }
    }

    companion object {
        val gifResponseEntity = GifResponseEntity(
            gifs = listOf(
                GifEntity(
                    id = "id",
                    images = ImagesEntity(
                        original = OriginalEntity(
                            mp4 = "mp4",
                            url = "url",
                            webp = "webp",
                        )
                    ),
                    rating = "A",
                    title = "title",
                    type = "type",
                    url = "url",
                    username = "username",
                ),
            ),
            meta = MetaEntity(
                msg = "",
                responseId = "",
                status = 0
            ),
            pagination = PaginationEntity(
                count = 1,
                offset = 0,
                totalCount = 1000,
            )
        )
    }

    class Robot : BaseRobot() {
        private val gifService = mockk<GifService>()
        private val gifDataSource = GifDataSourceImpl(gifService)
        private lateinit var gifDetail: GifEntity
        private lateinit var isExceptionThrown: Exception

        fun mockSuccessfulGetGifDetail() {
            coEvery { gifService.getGifDetail("id") } answers { gifResponseEntity }
        }

        fun mockFailureGetTrendingGifDetail() {
            coEvery { gifService.getGifDetail(any()) } throws GifNotFountException
        }

        fun callingGetGifDetail() = runTest {
            try {
                gifDetail = gifDataSource.getGifDetail(id = "id")
            } catch (e: GifNotFountException) {
                isExceptionThrown = e
            }
        }

        fun checkGifDetailSuccessfulResult() {
            TestCase.assertEquals("id", gifDetail.id)
            TestCase.assertEquals("title", gifDetail.title)
            TestCase.assertEquals("username", gifDetail.username)
            TestCase.assertEquals("url", gifDetail.url)
            TestCase.assertEquals("type", gifDetail.type)
            TestCase.assertEquals("A", gifDetail.rating)
            TestCase.assertEquals("mp4", gifDetail.images.original.mp4)
            TestCase.assertEquals("url", gifDetail.images.original.url)
            TestCase.assertEquals("webp", gifDetail.images.original.webp)
        }

        fun checkGifDetailFailureResult() {
            assertTrue(isExceptionThrown is GifNotFountException)
        }
    }
}
