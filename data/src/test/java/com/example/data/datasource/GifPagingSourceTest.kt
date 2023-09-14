package com.example.data.datasource

import androidx.paging.PagingSource
import com.example.data.entity.GifEntity
import com.example.libraries.test.BaseRobot
import com.example.libraries.test.dsl.AND
import com.example.libraries.test.dsl.GIVEN
import com.example.libraries.test.dsl.RUN_UNIT_TEST
import com.example.libraries.test.dsl.THEN
import com.example.libraries.test.dsl.WHEN
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import enqueueResponse
import junit.framework.TestCase.assertEquals
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

    @Inject
    lateinit var mockWebServer: MockWebServer
    private lateinit var robot: Robot

    @Before
    fun setUp() {
        hiltRule.inject()
        robot = Robot(mockWebServer)
    }

    @Test
    fun test_page1_getGifs() {
        var loadResult: PagingSource.LoadResult<Int, GifEntity>? = null

        RUN_UNIT_TEST(robot) {
            GIVEN { mockGetGifsPage1() }
            WHEN {
                runTest {
                    loadResult = gifDataSource.getGifs().load(
                        PagingSource.LoadParams.Refresh(
                            key = null,
                            loadSize = 30,
                            placeholdersEnabled = false
                        )
                    )
                }
            }
            THEN { checkLoadResultIsAPage(loadResult!!) }
            AND { checkPage1Result(loadResult as PagingSource.LoadResult.Page<Int, GifEntity>) }
        }
    }

    @Test
    fun test_page2_getGifs() {
        var loadResult: PagingSource.LoadResult<Int, GifEntity>? = null

        RUN_UNIT_TEST(robot) {
            GIVEN { mockGetGifsPage2() }
            WHEN {
                runTest {
                    loadResult = gifDataSource.getGifs().load(
                        PagingSource.LoadParams.Refresh(
                            key = 2,
                            loadSize = 30,
                            placeholdersEnabled = false,
                        )
                    )
                }
            }
            THEN { checkLoadResultIsAPage(loadResult!!) }
            THEN { checkPage2Result(loadResult as PagingSource.LoadResult.Page<Int, GifEntity>) }
        }
    }

    @Test
    fun test_page3_getGifs() {
        var loadResult: PagingSource.LoadResult<Int, GifEntity>? = null

        RUN_UNIT_TEST(robot) {
            GIVEN { mockGetGifsPage3() }
            WHEN {
                runTest {
                    loadResult = gifDataSource.getGifs().load(
                        PagingSource.LoadParams.Refresh(
                            key = 3,
                            loadSize = 30,
                            placeholdersEnabled = false,
                        )
                    )
                }
            }
            THEN { checkLoadResultIsAPage(loadResult!!) }
            AND { checkPage3Result(loadResult as PagingSource.LoadResult.Page<Int, GifEntity>) }
        }
    }

    class Robot(private val mockWebServer: MockWebServer) : BaseRobot() {

        fun mockGetGifsPage1() {
            mockWebServer.enqueueResponse("get-search-page1-200.json", 200)
        }

        fun mockGetGifsPage2() {
            mockWebServer.enqueueResponse("get-search-page2-200.json", 200)
        }

        fun mockGetGifsPage3() {
            mockWebServer.enqueueResponse("get-search-page3-200.json", 200)
        }

        fun checkLoadResultIsAPage(loadResult: PagingSource.LoadResult<Int, GifEntity>) {
            assertTrue(loadResult is PagingSource.LoadResult.Page)
        }

        fun checkPage1Result(page: PagingSource.LoadResult.Page<Int, GifEntity>) {
            assertEquals(30, page.data.size)
            assertEquals(null, page.prevKey)
            assertEquals(2, page.nextKey)
            assertEquals("qL24DCxwreoE8aINpF", page.data[0].id)
        }

        fun checkPage2Result(page: PagingSource.LoadResult.Page<Int, GifEntity>) {
            assertEquals(30, page.data.size)
            assertEquals(2, page.prevKey)
            assertEquals(3, page.nextKey)
            assertEquals("EUpd0vfZZI3Lu2hwwy", page.data[0].id)
        }

        fun checkPage3Result(pagingSource: PagingSource.LoadResult.Page<Int, GifEntity>) {
            assertEquals(30, pagingSource.data.size)
            assertEquals(3, pagingSource.prevKey)
            assertEquals(4, pagingSource.nextKey)
            assertEquals("vIWdboHBl9sjKnEByb", pagingSource.data[0].id)
        }
    }
}