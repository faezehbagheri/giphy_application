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

    private val gifsService = mockk<GifsService> {
        coEvery {
            getTrendingGifs(limit = any(), offset = any())
        } returns gifResponseEntity

        coEvery {
            getGifById(id = any())
        } throws GifNotFountException

        coEvery {
            getGifById(id = "test")
        } returns gifResponseEntity
    }

    @Test
    fun `check getGifs function is called correctly`() = runTest {
        // Given
        val datasource: GifDataSource = GifDataSourceImpl(
            gifService = gifsService,
            coroutineContext = coroutineContext,
        )

        // When
        val gifs = datasource.getGifs(offset = 0, limit = 10)

        // Then
        coVerify(exactly = 1) { gifsService.getTrendingGifs(offset = any(), limit = any()) }
        assertTrue(gifs.isNotEmpty())
    }

    @Test
    fun `given current gif id then check getGifDetail function is called correctly`() = runTest {
        // Given
        val datasource: GifDataSource = GifDataSourceImpl(
            gifService = gifsService,
            coroutineContext = coroutineContext,
        )

        // When
        var isExceptionThrown = false
        try {
            datasource.getGifDetail(id = "test")
        } catch (e: GifNotFountException) {
            isExceptionThrown = true
        }

        // Then
        assertTrue(isExceptionThrown.not())
        coVerify(exactly = 1) { gifsService.getGifById(id = any()) }
    }

    @Test
    fun `given incorrect gif id then check getGifDetail function throws an exception`() = runTest {
        // Given
        val datasource: GifDataSource = GifDataSourceImpl(
            gifService = gifsService,
            coroutineContext = coroutineContext,
        )

        // When
        var isExceptionThrown = false
        try {
            datasource.getGifDetail(id = "incorrect_id")
        } catch (e: GifNotFountException) {
            isExceptionThrown = true
        }

        // Then
        assertTrue(isExceptionThrown)
        coVerify(exactly = 1) { gifsService.getGifById(id = any()) }
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
