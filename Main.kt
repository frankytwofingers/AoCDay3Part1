// Code by Nathan Robinson Dec 7th 2023

import java.io.File

var charsPerLine: Int = 0
var currentCharIndexNumber: Int = 0 // running tally of current char index
var foundDigits: Boolean = false // if digit found, TRUE, otherwise FALSE
var lineNumber: Int = 0 // stores current line number
var listOfSpecialCharIndex = mutableListOf<String>() // accumulates special chars in line, later stored in specialMap value
var numberIndexStart: Int = 0 // stores index where number starts, foundDigits changed to TRUE
var numberMap = mutableMapOf<Int, String>() // map indexing where number started and the number
var numberString: String = "" // stores number as string to call length of digits (numberString.length) | save number
var puzzleText = mutableListOf<String>() // stores puzzle input by line
var sequence = mutableListOf<Int>() // stores numbers that qualify under current rules to be summed
var specialMap = mutableMapOf<Int, List<String>>() // map of line number, list of special char index numbers in line

fun getPuzzleLines () { // open PuzzleInput.txt file and passes one line at a time through solvePuzzleInput()
    File("src/main/kotlin/PuzzleInput3.txt").useLines { lines -> lines.forEach { puzzleText.add(it) }}
    for (line in puzzleText) {
        if (charsPerLine == 0) { charsPerLine = line.length } // determine number of chars in lines, called once, checked too many times
        solvePuzzleInput(line) // input puzzle text one line at a time
        lineNumber++ // keep track of line number (probably redundant)
    }
}

fun solvePuzzleInput(line: String) {
    for (char in line) { // go through each char in line
        if (char.isDigit()) { // if char is a digit
            if (!foundDigits) { // and this is the first digit in the number found
                foundDigits = true // indicate beginning of digit sequence has been found
                numberIndexStart = currentCharIndexNumber // save index number of where number was found
            }
            numberString += char // add digit to string
        } else if (!char.isDigit()) { // if char is not a digit
            foundDigits = false // indicate no longer looking at digits
            if (numberString.isNotEmpty()) { // if numberString had digits appended to it
                numberMap[numberIndexStart] = numberString // add the number to the numberMap with starting index
                numberString = "" // clear the string for next number
            }
            if (char != '.') { // if char is not a digit and not a '.', then it is considered a special char
                listOfSpecialCharIndex.add(currentCharIndexNumber.toString()) // save index number of special char to list
            }
        }
        currentCharIndexNumber++ // go to next char index number
    } // end of line loop...
    specialMap[lineNumber] = listOfSpecialCharIndex.toList() // add list of special char to specialMap (line, list of numbers)
    listOfSpecialCharIndex.clear() // clear special char index for next line of text
}

fun seekAndSum(): Int {
    for (numbers in numberMap) { // iterate over all index/number pairs
        for (numberStartingIndex in numbers.key - 1..numbers.key + numbers.value.length) { // expand index to left and right of number
            if (specialMap[numbers.key / lineNumber - 1].toString().contains((numberStartingIndex - charsPerLine).toString()) ||
                specialMap[numbers.key / lineNumber].toString().contains(numberStartingIndex.toString()) ||
                specialMap[numbers.key / lineNumber + 1].toString().contains((numberStartingIndex + charsPerLine).toString())) {
                sequence.add(numbers.value.toInt()) // ^ check lines above, inline, and below ^ if one statement is true, valid number found
                break // ^ add number to sequence ^ bounce out of loop
            }
        }
    }
    return sequence.sum() // return the total added value of all valid numbers
}

fun main() {
    getPuzzleLines() // start
    println("Total Sum is ${seekAndSum()}") // call seekAndSum, display, and have a nice day

    sequence.clear() // destroy
    specialMap.clear() // all
    numberMap.clear() // the
    puzzleText.clear() // evidence
}
