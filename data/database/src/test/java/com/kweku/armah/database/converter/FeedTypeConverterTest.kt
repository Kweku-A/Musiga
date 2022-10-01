package com.kweku.armah.database.converter

import com.kweku.armah.database.entity.feed.fake.feedSessionEntity1
import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FeedTypeConverterTest {

    private lateinit var sut: FeedTypeConverter

    @Before
    fun init() {
        val moshi = Moshi.Builder().build()
        sut = FeedTypeConverter(moshi)
    }

    @Test
    fun should_return_json() {
        val expected = """["c","d","e"]"""
        val actual = sut.listToJson(feedSessionEntity1.genres)
        assertEquals(expected, actual)
    }

    @Test
    fun should_return_list() {
        val jsonGenres = """["c","d","e"]"""
        val expected = feedSessionEntity1.genres
        val actual = sut.jsonToList(jsonGenres)
        assertEquals(expected, actual)
    }
}