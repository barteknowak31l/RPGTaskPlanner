package edu.put.rpgtaskplanner.utility
import edu.put.rpgtaskplanner.model.Character

object CharacterManager {
    private var currentCharacter: Character? = null

    fun setCurrentCharacter(character: Character) {
        currentCharacter = character
    }

    fun getCurrentCharacter(): Character? {
        return currentCharacter
    }

    fun setToNull() {
        currentCharacter = null
    }
}
