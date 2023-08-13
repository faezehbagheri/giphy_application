@file:OptIn(ExperimentalCoroutinesApi::class)

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
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GifDataSourceTest {

    @Test
    fun `given mocked gif service, when calling getGifs function in GifDataSource, then check gifsService#getTrendingGifs is called`() = runTest {
        val gifsService = mockk<GifsService> {
            coEvery {
                getTrendingGifs(limit = any(), offset = any())
            } returns gifResponseEntity
        }
        val datasource: GifDataSource = GifDataSourceImpl(
            gifService = gifsService,
            coroutineContext = coroutineContext,
        )

        datasource.getGifs(offset = 0, limit = 10)

        coVerify { gifsService.getTrendingGifs(offset = any(), limit = any()) }
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
