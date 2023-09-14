package com.example.libraries.test.dsl

import com.example.libraries.test.BaseRobot

@Suppress("FunctionName")
fun <T : BaseRobot> TestRun<T>.GIVEN(
    block: T.() -> Unit
): T = robot.apply(block)

@Suppress("FunctionName")
fun <T : BaseRobot> TestRun<T>.WHEN(
    block: T.() -> Unit
): T = robot.apply(block)

@Suppress("FunctionName")
fun <T : BaseRobot> TestRun<T>.AND(
    block: T.() -> Unit
): T = robot.apply(block)

@Suppress("FunctionName")
fun <T : BaseRobot> TestRun<T>.THEN(
    block: T.() -> Unit
): T = robot.apply(block)

data class TestRun<T : BaseRobot>(
    val robot: T,
    val isUnitTest: Boolean
)
