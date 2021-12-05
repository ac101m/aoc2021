package com.ac101m.aoc2021.day01

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

fun intList(input: String): List<Int> {
    return input.split('\n').filter{
        it.isNotEmpty()
    }.map {
        it.toInt()
    }
}

fun countIncreases(depths: List<Int>): Int {
    var previous = depths[0]
    var increases = 0
    for (depth in depths) {
        if (depth > previous) {
            increases += 1
        }
        previous = depth
    }
    return increases
}

fun movingAverage(depths: List<Int>): List<Int> {
    return depths.windowed(3, 1) {
        it.sum()
    }
}

fun part1(rawInput: String): Any {
    val depths = intList(rawInput)
    return countIncreases(depths)
}

fun part2(rawInput: String): Any {
    val depths = intList(rawInput)
    return countIncreases(movingAverage(depths))
}
