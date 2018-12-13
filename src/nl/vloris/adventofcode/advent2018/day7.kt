package nl.vloris.adventofcode.advent2018

data class Step(val label: String) {
    val duration = label[0].toInt() - 4

    var before = mutableSetOf<Step>()
    var started = false
    var finished = false

    fun canExecute(): Boolean {
        return before.all { it.finished } && !started
    }
}

data class Worker(val label: String) {
    var activeStep: Step? = null
    var startedAt: Int? = null

    fun willFinishAt(): Int? {
        return if (activeStep != null) {
            startedAt?.plus(activeStep!!.duration)
        } else {
            null
        }
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
        allInstructions.forEach { it.started = false; it.finished = false }

        val instructions = mutableListOf<Step>()

        var currentStep: Step? = findNextStep()
        while (currentStep != null) {
            currentStep.started = true
            currentStep.finished = true
            instructions.add(currentStep)

            currentStep = findNextStep()
        }

        return instructions.joinToString("") { it.label }
    }

    private fun distributeTasks(currentTime: Int, workers: List<Worker>) {
        // 1.: check finished tasks, mark them finished
        workers.filter { it.willFinishAt() == currentTime }.forEach { worker ->
            worker.activeStep?.finished = true
            worker.activeStep = null
            worker.startedAt = null
        }

        // 2.: check idle workers, assign new tasks
        workers.filter { it.activeStep == null }.forEach { worker ->
            val nextStep = findNextStep()
            nextStep?.started = true
            worker.activeStep = nextStep
            worker.startedAt = currentTime
        }
    }

    fun answerPart2(): Int {
        allInstructions.forEach { it.started = false; it.finished = false }

        val workers = listOf(Worker("1"), Worker("2"), Worker("3"), Worker("4"), Worker("5"))

        var currentTime = -1
        while (!allInstructions.all { it.finished }) {
            currentTime++
            distributeTasks(currentTime, workers)
        }

        return currentTime
    }
}

fun main(args: Array<String>) {
    println("Day7 part1: " + Day7.answerPart1())
    println("Day7 part2: " + Day7.answerPart2()) // 956 is too high
}