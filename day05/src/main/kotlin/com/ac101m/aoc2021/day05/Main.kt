package com.ac101m.aoc2021.day05

import java.io.File
import java.lang.IllegalArgumentException
import kotlin.math.abs

fun printUsage() {
    println("usage: day05 <part> <input>")
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

class Point(var x: Int, var y: Int) {
    companion object {
        fun parse(str: String): Point {
            val tokens = str.split(",")
            val x = tokens[0].filter { !it.isWhitespace() }.toInt()
            val y = tokens[1].filter { !it.isWhitespace() }.toInt()
            return Point(x, y)
        }
    }

    operator fun plusAssign(lhs: Point) {
        x += lhs.x
        y += lhs.y
    }
}

class VentLine(val start: Point, val end: Point) {
    init {
        if (!(isOrthogonal() or isDiagonal())) {
            throw IllegalArgumentException("Oh no, vent line is wierd :S")
        }
    }

    companion object {
        fun parse(str: String): VentLine {
            val tokens = str.split("->")
            return VentLine(Point.parse(tokens[0]), Point.parse(tokens[1]))
        }
    }

    fun getDirection(): Point {
        val x = when {
            start.x < end.x -> 1
            start.x > end.x -> -1
            else -> 0
        }
        val y = when {
            start.y < end.y -> 1
            start.y > end.y -> -1
            else -> 0
        }
        return Point(x, y)
    }

    fun isOrthogonal(): Boolean {
        return ((start.x == end.x) or (start.y == end.y))
    }

    fun isDiagonal(): Boolean {
        return abs(start.x - end.x) == abs(start.y - end.y)
    }
}

class VentMap(width: Int, height: Int) {
    private val map = Array(height) { IntArray(width) { 0 } }

    companion object {
        fun parse(rawInput: String, filterDiagonal: Boolean): VentMap {
            val ventMap = VentMap(1000, 1000)
            val ventLines = rawInput.split("\n").filter { it.isNotEmpty() }.map { line ->
                VentLine.parse(line)
            }
            for (ventLine in ventLines) {
                if (ventLine.isDiagonal() and filterDiagonal) {
                    continue
                }
                ventMap.markVents(ventLine)
            }
            return ventMap
        }
    }

    fun markVents(ventLine: VentLine) {
        val position = Point(ventLine.start.x, ventLine.start.y)
        val direction = ventLine.getDirection()
        while ((position.x != ventLine.end.x) or (position.y != ventLine.end.y)) {
            map[position.y][position.x]++
            position += direction
        }
        map[position.y][position.x]++
    }

    fun countOverlaps(threshold: Int = 2): Int {
        var count = 0
        map.forEach { row ->
            row.forEach { value ->
                if (value >= threshold) {
                    count++
                }
            }
        }
        return count
    }
}

fun part1(rawInput: String): Any {
    val ventMap = VentMap.parse(rawInput, filterDiagonal = true)
    return ventMap.countOverlaps()
}

fun part2(rawInput: String): Any {
    val ventMap = VentMap.parse(rawInput, filterDiagonal = false)
    return ventMap.countOverlaps()
}
