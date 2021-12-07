package com.ac101m.aoc2021.day06

import java.io.File

fun printUsage() {
    println("usage: day01 <part> <input>")
}

fun getInputText(path: String): String {
    return File(path).readText()
}

fun main(args: Array<String>) {
    if (args.size != 2) {
        printUsage()
        return
    }

    val part = args[0].toInt()
    val input = getInputText(args[1])

    val result = when (part) {
        1 -> part1(input)
        2 -> part2(input)
        else -> {
            println("part must be '1' or '2'")
            printUsage()
            return
        }
    }

    println("Running part $part...")
    println("Input: ${args[1]}")
    println("Result: $result")
}

fun parseAges(rawInput: String): MutableMap<Int, Long> {
    val ageList = rawInput.split(",").filter { it.isNotEmpty() }.map { token ->
        token.filter { char -> !char.isWhitespace() }.toInt()
    }
    val ageMap = HashMap<Int, Long>().toMutableMap()
    for (i in 0..8) {
        ageMap[i] = 0
    }
    ageList.forEach { age ->
        ageMap[age] = ageMap[age]!! + 1
    }
    return ageMap
}

fun performIteration(ageMap: MutableMap<Int, Long>) {
    val reproduceCount = ageMap[0]!!
    for (i in 0..7) {
        ageMap[i] = ageMap[i + 1]!!
    }
    ageMap[6] = ageMap[6]!! + reproduceCount
    ageMap[8] = reproduceCount
}

fun part1(rawInput: String): Any {
    val ageMap = parseAges(rawInput)
    for (i in 0 until 80) {
        performIteration(ageMap)
    }
    return ageMap.values.sum()
}

fun part2(rawInput: String): Any {
    val ageMap = parseAges(rawInput)
    for (i in 0 until 256) {
        performIteration(ageMap)
    }
    return ageMap.values.sum()
}
