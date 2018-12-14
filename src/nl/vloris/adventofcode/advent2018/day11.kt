package nl.vloris.adventofcode.advent2018

class Grid(private val size: Int) : Iterable<Int> {
    private val grid = IntArray(size * size)

    operator fun get(x: Int, y: Int): Int {
        return grid[index(x, y)]
    }

    operator fun set(x: Int, y: Int, value: Int) {
        grid[index(x, y)] = value
    }

    private fun index(x: Int, y: Int): Int {
        return (y - 1) * size + (x - 1)
    }

    override fun iterator(): Iterator<Int> = grid.iterator()
}

object Day11 {
    private fun calculatePower(x: Int, y: Int, gridId: Int): Int {
        val rackId = x + 10
        var power = rackId * y
        power += gridId
        power *= rackId
        power = power / 100 % 10
        power -= 5

        return power
    }

    private fun createGrid(gridId: Int): Grid {
        val fuelGrid = Grid(300)
        for (x in 1..300) {
            for (y in 1..300) {
                fuelGrid[x, y] = calculatePower(x, y, gridId)
            }
        }

        return fuelGrid
    }

    private fun powerOfSquare(grid: Grid, left: Int, top: Int, size: Int): Int {
        var runningTotal = 0
        for (x in left until left + size) {
            for (y in top until top + size) {
                runningTotal += grid[x, y]
            }
        }
        return runningTotal
    }

    data class Answer(val left: Int, val top: Int, val size: Int, val power: Int)

    fun answerPart1(): String {
        val fuelGrid = createGrid(7139)

        var answer = Answer(-1, -1, -1, -1)

        for (left in 1..298) {
            for (top in 1..298) {
                val power = powerOfSquare(fuelGrid, left, top, 3)
                if (power > answer.power) {
                    answer = Answer(left, top, 3, power)
                }
            }
        }

        return "${answer.left},${answer.top}"
    }


    fun answerPart2(): String {
        val fuelGrid = createGrid(7139)

        var answer = Answer(-1, -1, -1, -1)

        for (size in 1..300) {
            for (left in 1..(300 - size)) {
                for (top in 1..(300 - size)) {
                    val power = powerOfSquare(fuelGrid, left, top, size)
                    if (power > answer.power) {
                        answer = Answer(left, top, size, power)
                    }
                }
            }
        }

        return "${answer.left},${answer.top},${answer.size}"
    }
}

fun main(args: Array<String>) {
    println("Day11, part1: " + Day11.answerPart1())
    println("Day11, part2: " + Day11.answerPart2())
}