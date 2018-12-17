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
}

fun main(args: Array<String>) {
    println("Day14, part1: " + Day14.answerPart1())
}