package com.example.data

import com.example.data.datasource.GifDataSource
import com.example.data.datasource.GifDataSourceImpl
import com.example.data.entity.GifEntity
import com.example.data.entity.GifResponseEntity
import com.example.data.entity.ImagesEntity
import com.example.data.entity.MetaEntity
import com.example.data.entity.OriginalEntity
import com.example.data.entity.PaginationEntity
import com.example.data.remote.GifsService
import com.example.libraries.common.exception.GifNotFountException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GifDataSourceTest {

    private val gifsService = mockk<GifsService>()
    private val gifDataSource = GifDataSourceImpl(gifsService)

    @Test
    fun `given mock getTrendingGifs, when calling getGifs from gifDataSource, then check the correctness of the result`() =
        runTest {
            ///Given
            coEvery {
                gifsService.getTrendingGifs(
                    limit = any(),
                    offset = any()
                )
            } answers { gifResponseEntity }

            ///When
            val gifs = gifDataSource.getGifs(offset = 0, limit = 10)

            ///Then
            coVerify(exactly = 1) { gifsService.getTrendingGifs(any(), any()) }
            assertTrue(gifs.isNotEmpty())
        }

    @Test
    fun `given mock getGifById, when calling getGifDetail from gifDataSource, then check the correctness of the result`() =
        runTest {
            ///Given
            coEvery { gifsService.getGifById("test") } answers { gifResponseEntity }

            ///When
            var isExceptionThrown = false
            try {
                gifDataSource.getGifDetail(id = "test")
            } catch (e: GifNotFountException) {
                isExceptionThrown = true
            }

            ///Then
            coVerify(exactly = 1) { gifsService.getGifById(any()) }
            assertTrue(isExceptionThrown.not())
        }

    @Test
    fun `given mock getGifById, when calling getGifDetail from gifDataSource, then throws an exception`() =
        runTest {
            ///Given
            coEvery { gifsService.getGifById(any()) } throws GifNotFountException

            ///When
            var isExceptionThrown = false
            try {
                gifDataSource.getGifDetail(id = "incorrect_id")
            } catch (e: GifNotFountException) {
                isExceptionThrown = true
            }

            ///Then
            coVerify(exactly = 1) { gifsService.getGifById(any()) }
            assertTrue(isExceptionThrown)
        }

    companion object {
        val gifResponseEntity = GifResponseEntity(
            gifs = listOf(
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
}
