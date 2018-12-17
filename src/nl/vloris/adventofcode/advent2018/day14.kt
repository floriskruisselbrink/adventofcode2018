package nl.vloris.adventofcode.advent2018

object Day14 {
    data class Recipe(val score: Int)
    data class Elf(var currentIndex: Int)

    class ScoreBoard(recipes: List<Recipe>) {
        private val recipes: MutableList<Recipe> = recipes.toMutableList()

        private val elves: List<Elf>

        init {
            this.elves = recipes.indices.map { index -> Elf(index) }
        }

        fun findScoreAfter(index: Int): String {
            val neededRecipes = index + 10
            while (recipes.size < neededRecipes) {
                nextRound()
            }

            return recipes
                .subList(index, index + 10)
                .map { recipe -> recipe.score }
                .joinToString("")
        }

        fun countRecipesBefore(pattern: String): Int {
            val matchList = pattern.toCharArray().map { Recipe(it.toInt() - '0'.toInt()) }

            var index: Int?
            do {
                nextRound()
                index = scoreBoardEndsWith(matchList)
            } while (index == null)
            return index
        }

        private fun nextRound() {
            val currentRecipes = elves.map { elf -> recipes[elf.currentIndex] }
            val newRecipes = createNewRecipes(currentRecipes)
            recipes.addAll(newRecipes)

            elves.forEach { elf ->
                val currentScore = recipes[elf.currentIndex].score
                val newIndex = (elf.currentIndex + 1 + currentScore) % recipes.size
                elf.currentIndex = newIndex
            }
            //printScoreboard()
        }

        private fun createNewRecipes(currentRecipes: List<Recipe>): List<Recipe> {
            val sum = currentRecipes.sumBy { it.score }
            return if (sum >= 10) {
                listOf(Recipe(sum / 10), Recipe(sum % 10))
            } else {
                listOf(Recipe(sum))
            }
        }

        private fun scoreBoardEndsWith(matchList: List<Recipe>): Int? {
            if (recipes.size <= matchList.size) return null

            // check also for match one earlier in case two recipes where added
            val list1 = recipes.subList(recipes.size - matchList.size, recipes.size)
            val list2 = recipes.subList(recipes.size - matchList.size - 1, recipes.size - 1)

            return when (matchList) {
                list1 -> recipes.size - matchList.size
                list2 -> recipes.size - matchList.size - 1
                else -> null
            }
        }

        private fun printScoreboard() {
            val sb = StringBuilder()
            val currentIndices = elves.map { elf -> elf.currentIndex }

            recipes.forEachIndexed { index, recipe ->
                when (index) {
                    currentIndices[0] -> sb.append("(${recipe.score})")
                    currentIndices[1] -> sb.append("[${recipe.score}]")
                    else -> sb.append(" ${recipe.score} ")
                }
            }

            println(sb.toString())
        }
    }

    fun answerPart1(): String {
        val scoreBoard = ScoreBoard(listOf(Recipe(3), Recipe(7)))
        return scoreBoard.findScoreAfter(190221)
    }

    fun answerPart2(): Int {
        val scoreBoard = ScoreBoard(listOf(Recipe(3), Recipe(7)))
        return scoreBoard.countRecipesBefore("190221")
    }
}

fun main(args: Array<String>) {
    println("Day14, part1: " + Day14.answerPart1())
    println("Day14, part2: " + Day14.answerPart2())
}