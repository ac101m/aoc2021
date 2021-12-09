package com.ac101m.aoc2021.day03

import java.io.File
import java.lang.IllegalArgumentException

fun printUsage() {
    println("usage: day03 <part> <input>")
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

fun parseBitmaps(input: String): List<List<Boolean>> {
    return input.split("\n").filter { it.isNotEmpty() }.map { line ->
        line.map { char ->
            when (char) {
                '1' -> true
                '0' -> false
                else -> throw IllegalArgumentException("Illegal character in input, expected only 1 or 0!")
            }
        }
    }
}

fun getBitFrequency(bitmaps: List<List<Boolean>>): IntArray {
    val counts = IntArray(bitmaps[0].size) { _ -> 0 }
    for (bitmap in bitmaps) {
        bitmap.forEachIndexed { i, bit ->
            if (bit) {
                counts[i]++
            }
        }
    }
    return counts
}

fun getMostFrequentBits(bitmaps: List<List<Boolean>>, default: Boolean? = null): List<Boolean> {
    val lineCount = bitmaps.size
    return getBitFrequency(bitmaps).map { count ->
        when {
            count * 2 > lineCount -> true
            count * 2 < lineCount -> false
            else -> default ?: throw IllegalArgumentException("Uh oh, equal number of bits are set & unset!")
        }
    }
}

fun part1(rawInput: String): Any {
    val bitmaps = parseBitmaps(rawInput)
    val bitCount = bitmaps[0].size
    var gamma = 0
    var epsilon = 0
    getMostFrequentBits(bitmaps).forEachIndexed { i, mostCommon ->
        when (mostCommon) {
            true -> gamma = gamma or (1 shl ((bitCount - 1) - i))
            false -> epsilon = epsilon or (1 shl ((bitCount - 1) - i))
        }
    }
    return gamma * epsilon
}

fun thereCanBeOnlyOne(bitmaps: List<List<Boolean>>, selectLeastCommon: Boolean): List<Boolean> {
    var filteredBitmaps = bitmaps
    var i = 0
    while (filteredBitmaps.size > 1) {
        val mostCommon = getMostFrequentBits(filteredBitmaps, default = true)
        filteredBitmaps = filteredBitmaps.filter { bitmap ->
            bitmap[i] == mostCommon[i] xor selectLeastCommon
        }
        i++
    }
    return filteredBitmaps[0]
}

fun bitmapToInt(bitmap: List<Boolean>): Int {
    var value = 0
    val bitCount = bitmap.size
    bitmap.forEachIndexed { i, bit ->
        val iReversed = (bitCount - 1) - i
        if (bit) {
            value = value or (1 shl iReversed)
        }
    }
    return value
}

fun part2(rawInput: String): Any {
    val bitmaps = parseBitmaps(rawInput)
    val oxygenRating = bitmapToInt(thereCanBeOnlyOne(bitmaps, false))
    val co2Rating = bitmapToInt(thereCanBeOnlyOne(bitmaps, true))
    return co2Rating * oxygenRating
}
