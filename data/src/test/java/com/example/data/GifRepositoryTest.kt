package com.example.data

import com.example.data.datasource.GifDataSource
import com.example.data.entity.GifEntity
import com.example.data.entity.ImagesEntity
import com.example.data.entity.OriginalEntity
import com.example.data.repository.GifsRepositoryImpl
import com.example.domain.model.GifDetail
import com.example.libraries.common.exception.GifNotFountException
import com.example.libraries.common.result.GetResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GifRepositoryTest {

    private val gifDataSource: GifDataSource = mockk()
    private val gifsRepository = GifsRepositoryImpl(gifDataSource)

    @Test
    fun `given mock successfulGetGifDetail, when calling getGifDetail in gifsRepository, then check the correctness of the result`() =
        runTest {
            ///Given
            coEvery { gifDataSource.getGifDetail(any()) } answers { gifEntity }

            ///When
            var gifDetail: GifDetail? = null
            gifsRepository.getGifById("id").collect {
                when (it) {
                    is GetResult.Success -> gifDetail = it.data
                    else -> {}
                }
            }

            ///Then
            assertTrue(gifDetail != null)
            coVerify(exactly = 1) { gifDataSource.getGifDetail(any()) }

        }

    @Test
    fun `given mock failureGetGifDetail, when calling getGifDetail in gifsRepository, then throws a GifsNotFoundException`() =
        runTest {
            ///Given
            coEvery { gifDataSource.getGifDetail(any()) } throws GifNotFountException

            ///When
            var exception: Throwable? = null
            gifsRepository.getGifById("").collect {
                when (it) {
                    is GetResult.Error -> exception = it.throwable
                    else -> {}
                }
            }

            ///Then
            assertTrue(exception is GifNotFountException)
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
}