package nl.vloris.adventofcode.advent2018

enum class Action {
    BEGINS_SHIFT,
    WAKES_UP,
    FALLS_ASLEEP;

    companion object {
        fun fromInput(input: String): Action {
            when {
                input.endsWith("begins shift") -> return BEGINS_SHIFT
                input.endsWith("wakes up") -> return WAKES_UP
                input.endsWith("falls asleep") -> return FALLS_ASLEEP
            }
            throw IllegalArgumentException("Unmatched input: $input")
        }
    }
}

data class GuardEvent(val timestamp: String, val minutes: Int, val action: Action, val guard: Guard?)

data class Guard(val id: Int) {
    companion object {
        fun fromInput(line: String): Guard? {
            val id = Regex("#\\d+\\b").find(line)?.value?.substring(1)?.toInt()

            return if (id != null) {
                Guard(id)
            } else {
                null
            }
        }
    }
}

object Day4 {
    private val input = javaClass.getResource("day4-input.txt")
        .readText()
        .lines()
        .filter { it.isNotEmpty() }
        .map { line ->
            GuardEvent(
                line.substring(1..16),
                line.substring(15..16).toInt(),
                Action.fromInput(line),
                Guard.fromInput(line)
            )
        }
        .sortedBy { it.timestamp }

    private val asleepMap = mutableMapOf<Guard, IntArray>()

    init {
        var currentGuard = input.first().guard!!
        var fellAsleep = -1

        input.forEach {
            when (it.action) {
                Action.BEGINS_SHIFT -> currentGuard = it.guard!!
                Action.FALLS_ASLEEP -> fellAsleep = it.minutes
                Action.WAKES_UP -> {
                    val minutes = asleepMap[currentGuard] ?: IntArray(60)
                    markAsleep(minutes, fellAsleep, it.minutes)
                    asleepMap[currentGuard] = minutes
                }
            }
        }
    }

    private fun markAsleep(minuteMap: IntArray, from: Int, to: Int) {
        for (i in from until to) {
            minuteMap[i]++
        }
    }

    fun answerPart1(): Int {
        // 1. fromInput guard with most minutes asleep
        val (sleepyGuard, _) = asleepMap.map { (guardId, minuteMap) ->
            val totalMinutes = minuteMap.sum()
            Pair(guardId, totalMinutes)
        }.sortedByDescending { it.second }.first()

        // 2. fromInput minute he is asleep on most days
        val sleepyMinutes = asleepMap[sleepyGuard]!!
        val maxMinutes = sleepyMinutes.indices.maxBy { sleepyMinutes[it] }

        return sleepyGuard.id * maxMinutes!!
    }

    fun answerPart2(): Int {

        return 0
    }
}

fun main(args: Array<String>) {
    println("Day4 part1: " + Day4.answerPart1())
    println("Day4 part2: " + Day4.answerPart2())
}