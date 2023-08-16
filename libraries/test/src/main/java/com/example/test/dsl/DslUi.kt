package com.example.test.dsl

import com.example.test.BaseRobot
import java.util.concurrent.TimeUnit

@Suppress("FunctionName")
fun <T : BaseRobot> RUN_UI_TEST(
    robot: T,
    block: TestRun<T>.() -> Unit
): TestRun<T> {
    val startTime = System.nanoTime()

    println("*** UI TEST start ***")

    val testRun = TestRun(robot, true)
    block(testRun)

    val difference = System.nanoTime() - startTime

    println("*** time -> ${TimeUnit.NANOSECONDS.toMillis(difference)} ms ***")
    println("-------------------------------------------------------------------------------------")

    return testRun
}
