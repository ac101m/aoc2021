package com.ac101m.aoc2021.day07

import java.io.File
import kotlin.math.abs

fun printUsage() {
    println("usage: day07 <part> <input>")
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

fun parseCrabList(input: String): List<Int> {
    return input.split(",").filter { it.isNotEmpty() }.map { token ->
        token.filter { !it.isWhitespace() }.toInt()
    }
}

fun totalFuelCost(targetPosition: Int, crabPositions: List<Int>, fuelFunction: (distance: Int) -> Int): Int {
    var totalFuel = 0
    crabPositions.forEach { crabPosition ->
        totalFuel += fuelFunction(abs(targetPosition - crabPosition))
    }
    return totalFuel
}

fun part1(rawInput: String): Any {
    val crabPositions = parseCrabList(rawInput)
    val optimalPosition = crabPositions.sorted()[crabPositions.size / 2]
    return totalFuelCost(optimalPosition, crabPositions) { distance -> distance }
}

fun part2(rawInput: String): Any {
    var crabPositions = parseCrabList(rawInput)
    val minCrabPosition = crabPositions.minOf { it }
    val maxCrabPosition = crabPositions.maxOf { it }
    return minCrabPosition.rangeTo(maxCrabPosition).map { position ->
        totalFuelCost(position, crabPositions) { distance ->
            when (distance) {
                0 -> 0
                else -> (distance * (distance + 1)) / 2
            }
        }
    }.minOf { it }
}
