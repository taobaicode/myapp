package com.example.myapplication

import com.example.myapplication.model.People
import com.example.myapplication.model.PeopleProperties
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val p = People(uid = 1, properties = PeopleProperties(mass ="100g", height = "6feet", name ="Joe", birthYear = "2000"))
        assertEquals(p, People.from(p.toJson()))
    }
}