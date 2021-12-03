package com.ac101m.aoc2021.day02

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

enum class Direction {
    FORWARD,
    DOWN,
    UP
}

class Instruction(val operation: Direction, val magnitude: Int) {
    companion object {
        fun parse(source: String): Instruction {
            val tokens = source.split(" ")
                .filter { it.isNotEmpty() }
                .map { it.filterNot { char -> char.isWhitespace() } }
            val direction = Direction.valueOf(tokens[0].uppercase())
            val distance = tokens[1].toInt()
            return Instruction(direction, distance)
        }
    }
}

fun parseInstructions(input: String): List<Instruction> {
    return input.split("\n")
            .filter { it.isNotEmpty() }
            .map { Instruction.parse(it) }
}

open class Submarine {
    var displacement = 0
    var depth = 0

    open fun executeInstruction(instruction: Instruction) {
        when(instruction.operation) {
            Direction.UP -> depth -= instruction.magnitude
            Direction.DOWN -> depth += instruction.magnitude
            Direction.FORWARD -> displacement += instruction.magnitude
        }
    }
}

class FixedSubmarine : Submarine() {
    var aim = 0

    override fun executeInstruction(instruction: Instruction) {
        when(instruction.operation) {
            Direction.UP -> aim -= instruction.magnitude
            Direction.DOWN -> aim += instruction.magnitude
            Direction.FORWARD -> {
                displacement += instruction.magnitude
                depth += aim * instruction.magnitude
            }
        }
    }
}

fun part1(input: String): Any {
    val submarine = Submarine()
    for (instruction in parseInstructions(input)) {
        submarine.executeInstruction(instruction)
    }
    return submarine.depth * submarine.displacement
}

fun part2(input: String): Any {
    val submarine = FixedSubmarine()
    for (instruction in parseInstructions(input)) {
        submarine.executeInstruction(instruction)
    }
    return submarine.depth * submarine.displacement
}
