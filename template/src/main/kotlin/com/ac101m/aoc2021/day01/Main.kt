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

fun part1(input: String): Any {
    return "not implemented!"
}

fun part2(input: String): Any {
    return "not implemented!"
}
