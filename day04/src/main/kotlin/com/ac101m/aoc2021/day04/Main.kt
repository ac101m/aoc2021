package com.ac101m.aoc2021.day04

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

class BingoBoard(val numbers: List<List<Int>>) {
    val matches: List<ArrayList<Boolean>> = numbers.map { row ->
        run {
            val matchRow = ArrayList<Boolean>()
            row.forEach { _ -> matchRow.add(false) }
            matchRow
        }
    }

    companion object {
        fun parse(source: String): BingoBoard {
            val numbers = source.split("\n").filter { it.isNotEmpty() }.map { row ->
                row.split(" ").filter { it.isNotEmpty() }.map { number ->
                    number.toInt()
                }
            }
            return BingoBoard(numbers)
        }
    }

    fun callNumber(calledNumber: Int) {
        numbers.forEachIndexed { i, row ->
            row.forEachIndexed { j, number ->
                if (number == calledNumber) {
                    matches[i][j] = true
                }
            }
        }
    }

    private fun hasCompleteRow(): Boolean {
        for (row in matches) {
            if (false !in row) {
                return true
            }
        }
        return false
    }

    private fun hasCompleteColumn(): Boolean {
        for (i in 0 until matches[0].size) {
            var columnMatches = true
            for (j in matches.indices) {
                if (!matches[j][i]) {
                    columnMatches = false
                    break
                }
            }
            if (columnMatches) {
                return true
            }
        }
        return false
    }

    fun hasWon(): Boolean {
        return hasCompleteRow() or hasCompleteColumn()
    }

    fun sumNotMatched(): Int {
        var sum = 0
        matches.forEachIndexed { i, row ->
            row.forEachIndexed { j, matched ->
                if (!matched) {
                    sum += numbers[i][j]
                }
            }
        }
        return sum
    }
}

fun getInputSections(rawInput: String): List<String> {
    return rawInput.split("\n\n").filter { it.isNotEmpty() }
}

fun parseNumberList(rawInput: String): List<Int> {
    val numberListSection = getInputSections(rawInput)[0]
    return numberListSection.split(",").map {
        it.toInt()
    }
}

fun parseBingoBoards(rawInput: String): List<BingoBoard> {
    return getInputSections(rawInput).filterIndexed { i, _ -> i != 0 }.map { section ->
        BingoBoard.parse(section)
    }
}

fun computeWinningBoardScore(numbers: List<Int>, boards: List<BingoBoard>): Int {
    for (number in numbers) {
        for (bingoBoard in boards) {
            bingoBoard.callNumber(number)
            if (bingoBoard.hasWon()) {
                return bingoBoard.sumNotMatched() * number
            }
        }
    }
    throw IllegalArgumentException("Board list contains no winning boards!")
}

fun part1(rawInput: String): Any {
    val numbers = parseNumberList(rawInput)
    val bingoBoards = parseBingoBoards(rawInput)
    return computeWinningBoardScore(numbers, bingoBoards)
}

fun part2(rawInput: String): Any {
    return "not implemented!"
}
