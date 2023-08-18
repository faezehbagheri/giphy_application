package com.example.data

import androidx.paging.PagingSource
import com.example.data.datasource.GifDataSource
import com.example.data.datasource.GifPagingSource
import com.example.data.entity.GifEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import enqueueResponse
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class GifPagingSourceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var gifDataSource: GifDataSource
    private lateinit var gifPagingSource: GifPagingSource

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        hiltRule.inject()
        gifPagingSource = GifPagingSource(gifDataSource)
    }

    @Test
    fun `given mock getGifs, when calling`() = runTest {
        ///Given
        var loadResult: PagingSource.LoadResult<Int, GifEntity>?
        mockWebServer.enqueueResponse("get-search-page1-200.json", 200)

        ///When
        loadResult = gifPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        ///Then
        assertTrue(loadResult is PagingSource.LoadResult.Page)
    }
}