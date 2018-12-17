package nl.vloris.adventofcode.advent2018

object Day13 {
    enum class Turn {
        LEFT,
        STRAIGHT,
        RIGHT;

        val next: Turn
            get() = when (this) {
                LEFT -> STRAIGHT
                STRAIGHT -> RIGHT
                RIGHT -> LEFT
            }
    }

    enum class Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT;

        fun turn(turn: Turn): Direction {
            return when (Pair(this, turn)) {
                Pair(Direction.UP, Turn.LEFT) -> LEFT
                Pair(Direction.UP, Turn.RIGHT) -> RIGHT
                Pair(Direction.RIGHT, Turn.LEFT) -> UP
                Pair(Direction.RIGHT, Turn.RIGHT) -> DOWN
                Pair(Direction.DOWN, Turn.LEFT) -> RIGHT
                Pair(Direction.DOWN, Turn.RIGHT) -> LEFT
                Pair(Direction.LEFT, Turn.LEFT) -> DOWN
                Pair(Direction.LEFT, Turn.RIGHT) -> UP
                else -> this
            }
        }

        fun turn(track: Char): Direction {
            return when (Pair(this, track)) {
                Pair(UP, '/') -> RIGHT
                Pair(UP, '\\') -> LEFT
                Pair(RIGHT, '/') -> UP
                Pair(RIGHT, '\\') -> DOWN
                Pair(DOWN, '/') -> LEFT
                Pair(DOWN, '\\') -> RIGHT
                Pair(LEFT, '/') -> DOWN
                Pair(LEFT, '\\') -> UP
                else -> throw IllegalStateException()
            }
        }
    }


    data class Location(
        val x: Int,
        val y: Int
    ) {
        fun translate(direction: Direction): Location {
            return when (direction) {
                Direction.UP -> Location(x, y - 1)
                Direction.RIGHT -> Location(x + 1, y)
                Direction.DOWN -> Location(x, y + 1)
                Direction.LEFT -> Location(x - 1, y)
            }
        }
    }

    data class Cart(
        var location: Location,
        var direction: Direction
    ) {
        private var nextTurn = Turn.LEFT

        fun move(map: Map) {
            updateLocation()
            updateDirection(map[location])
        }

        private fun updateLocation() {
            location = location.translate(direction)
        }

        private fun updateDirection(track: Char) {
            if (track == '/' || track == '\\') {
                direction = direction.turn(track)
            } else if (track == '+') {
                direction = direction.turn(nextTurn)
                nextTurn = nextTurn.next
            }
        }
    }

    class Map(
        private val grid: Array<CharArray>
    ) {
        override fun toString(): String {
            val sb = StringBuilder()
            for (line in grid) {
                sb.append(line.joinToString(""))
                sb.append("\n")
            }
            return sb.toString()
        }

        operator fun get(location: Location): Char {
            return grid[location.y][location.x]
        }
    }

    class Game(
        private val map: Map,
        private val carts: List<Cart>
    ) {
        fun moveCarts(): Location? {
            val sortedCarts = carts
                .sortedBy { it.location.y * 150 + it.location.x }

            for (cart in sortedCarts) {
                cart.move(map)
                val collision = detectCollision()

                if (collision != null) {
                    return collision
                }
            }

            return null
        }

        private fun detectCollision(): Location? {
            return carts
                .groupingBy { it.location }
                .eachCount()
                .filterValues { it > 1 }
                .keys
                .firstOrNull()
        }
    }

    private val input = javaClass.getResource("day13-input.txt")
        .readText()
        .lines()
        .filter { it.isNotEmpty() }
        .map { it.toCharArray() }
        .toTypedArray()

    private fun extractCarts(map: Array<CharArray>): List<Cart> {
        val carts = mutableListOf<Cart>()
        for (y in 0 until map.size) {
            for (x in 0 until map[y].size) {
                val char = map[y][x]
                when (char) {
                    '^' -> {
                        carts.add(Cart(Location(x, y), Direction.UP))
                        map[y][x] = '|'
                    }
                    '>' -> {
                        carts.add(Cart(Location(x, y), Direction.RIGHT))
                        map[y][x] = '-'
                    }
                    'v' -> {
                        carts.add(Cart(Location(x, y), Direction.DOWN))
                        map[y][x] = '|'
                    }
                    '<' -> {
                        carts.add(Cart(Location(x, y), Direction.LEFT))
                        map[y][x] = '-'
                    }
                }
            }
        }
        return carts
    }

    fun answerPart1(): Location {
        val carts = extractCarts(input)
        val map = Map(input)
        val game = Game(map, carts)

        var collision: Location?
        do {
            collision = game.moveCarts()
        } while (collision == null)

        return collision
    }
}

fun main(args: Array<String>) {
    println("Day13, part1: " + Day13.answerPart1())
}