package com.popmart.kotlinandroid

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
//                assertEquals(4, 2 + 2);

        val list = listOf(1, 2, 3, 4, 5, 6)
        val listRepeated = listOf(1, 2, 3, 4, 5, 6)
        list.plus(listRepeated)


    }
}