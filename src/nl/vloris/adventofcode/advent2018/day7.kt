package nl.vloris.adventofcode.advent2018

data class Step(val label: String) {
    var before = mutableSetOf<Step>()
    var executed = false

    fun canExecute(): Boolean {
        return before.all { it.executed } && !executed
    }
}

object Day7 {
    private val input = javaClass.getResource("day7-input.txt")
        .readText()
        .lines()
        .filter { it.isNotEmpty() }

    private val allInstructions: Set<Step>

    init {
        val instructionMap = mutableMapOf<String, Step>()

        input.forEach { instruction ->
            val (_, first, second) = instruction.split("Step ", " must be finished before step ", " can begin.")
            println("$first, $second")

            val firstStep = instructionMap.getOrDefault(first, Step(first))
            val secondStep = instructionMap.getOrDefault(second, Step(second))

            secondStep.before.add(firstStep)

            instructionMap[first] = firstStep
            instructionMap[second] = secondStep
        }

        allInstructions = instructionMap.values.toSet()
    }

    private fun findNextStep(): Step? {
        return allInstructions
            .filter { it.canExecute() }
            .sortedBy { it.label }
            .firstOrNull()
    }

    fun answerPart1(): String {
        allInstructions.forEach { it.executed = false }

        val instructions = mutableListOf<Step>()

        var currentStep: Step? = findNextStep()
        while (currentStep != null) {
            currentStep.executed = true
            instructions.add(currentStep)

            currentStep = findNextStep()
        }

        return instructions.joinToString("") { it.label }
    }
}

fun main(args: Array<String>) {
    println("Day7 part1: " + Day7.answerPart1())
}