import java.io.File
var puzzleText = mutableListOf<String>()
var sequence = mutableListOf<Int>()
var numberChars = mutableListOf<Char>()
var numberString: String = ""
var numberExtracted: Int = 0
var foundDigits: Boolean = false

fun getPuzzleLines () { // open PuzzleInput.txt file and passes one line at a time through solvePuzzleInput()
    File("src/main/kotlin/PuzzleInput3.txt").useLines { lines -> lines.forEach { puzzleText.add(it) }}
    for (line in puzzleText) {
        sequence.add(solvePuzzleInput(line)) // one line at a time
    }
}

fun solvePuzzleInput(text: String): Int {
    for (idx in text) {
        if (idx.isDigit()) {
            numberChars.add(idx)
            foundDigits = true
        }
        if (!idx.isDigit()) {
            foundDigits = false
            println("IN HERE")
            if (numberExtracted == 0) {
                numberString = numberChars.toString()
                println(numberString)
                println(numberExtracted)
                numberExtracted = 0
                numberChars.clear()
                numberString = ""
            }



        }
    }
    return 0
}

fun main(args: Array<String>) {
    getPuzzleLines()
}