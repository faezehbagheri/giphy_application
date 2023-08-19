package com.example.data.mapper

import com.example.data.entity.GifEntity
import com.example.data.entity.ImagesEntity
import com.example.data.entity.OriginalEntity
import com.example.domain.model.Gif
import com.example.domain.model.GifDetail
import com.example.libraries.test.BaseRobot
import com.example.libraries.test.dsl.AND
import com.example.libraries.test.dsl.GIVEN
import com.example.libraries.test.dsl.RUN_UNIT_TEST
import com.example.libraries.test.dsl.THEN
import com.example.libraries.test.dsl.WHEN
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GifEntityMapperTest {

    private val robot = Robot()

    @Test
    fun test_toDomainGif() {
        RUN_UNIT_TEST(robot) {
            GIVEN {
                gifEntity = GifEntity(
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
                )
            }
            AND {
                gif = Gif(
                    id = "id",
                    thumbnail = "url"
                )
            }
            WHEN { toDomainGif() }
            THEN { checkGifEntityMappedToGif() }
        }
    }

    @Test
    fun test_toDomainGifDetail() {
        RUN_UNIT_TEST(robot) {
            GIVEN {
                gifEntity = GifEntity(
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
                )
            }
            AND {
                gifDetail = GifDetail(
                    id = "id",
                    gif = "url",
                    title = "title",
                    rating = "A",
                    username = "username",
                )
            }
            WHEN { toDomainGifDetail() }
            THEN { checkGifEntityMappedToGifDetail() }
        }
    }


    class Robot : BaseRobot() {
        lateinit var gifEntity: GifEntity
        lateinit var gif: Gif
        lateinit var gifDetail: GifDetail

        fun toDomainGif() {
            gifEntity.toDomainGif()
        }

        fun toDomainGifDetail() {
            gifEntity.toDomainGifDetail()
        }

        fun checkGifEntityMappedToGif() {
            assertEquals(gifEntity.id, gif.id)
            assertEquals(gifEntity.images.original.url, gif.thumbnail)
        }

        fun checkGifEntityMappedToGifDetail() {
            assertEquals(gifEntity.id, gifDetail.id)
            assertEquals(gifEntity.images.original.url, gifDetail.gif)
            assertEquals(gifEntity.title, gifDetail.title)
            assertEquals(gifEntity.rating, gifDetail.rating)
            assertEquals(gifEntity.username, gifDetail.username)
        }
    }
}