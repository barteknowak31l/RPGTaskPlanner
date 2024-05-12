package edu.put.rpgtaskplanner.utility
import edu.put.rpgtaskplanner.model.Character
import edu.put.rpgtaskplanner.model.Item

object CharacterManager {
    private var currentCharacter: Character? = null

    fun setCurrentCharacter(character: Character) {
        currentCharacter = character
    }

    fun getCurrentCharacter(): Character? {
        return currentCharacter
    }

    fun equipItem(item: Item)
    {
        if(currentCharacter != null)
        {
            when(item.type)
            {
                0 -> currentCharacter!!.max_health = item.base_bonus
                1 -> currentCharacter!!.energy_regen = item.base_bonus
                2 -> currentCharacter!!.gold_multiplier = item.base_bonus
                3 -> currentCharacter!!.exp_multiplier = item.base_bonus
                4 -> currentCharacter!!.max_energy = item.base_bonus
                5 -> currentCharacter!!.gold_multiplier = item.base_bonus
                6 -> currentCharacter!!.health_regen = item.base_bonus
                7 -> currentCharacter!!.cooldown_reduction = item.base_bonus
            }
        }
    }

    fun unequipItem(item: Item)
    {
        if(currentCharacter != null)
        {
            when(item.type)
            {
                0 -> currentCharacter!!.max_health = CharacterBuilder.BASE_HEALTH
                1 -> currentCharacter!!.energy_regen = CharacterBuilder.BASE_ENERGY_REGEN
                2 -> currentCharacter!!.gold_multiplier = CharacterBuilder.BASE_GOLD_MULTIPLIER
                3 -> currentCharacter!!.exp_multiplier = CharacterBuilder.BASE_EXP_MULTIPLIER
                4 -> currentCharacter!!.max_energy = CharacterBuilder.BASE_ENERGY
                5 -> currentCharacter!!.gold_multiplier = CharacterBuilder.BASE_GOLD_MULTIPLIER
                6 -> currentCharacter!!.health_regen = CharacterBuilder.BASE_HEALTH_REGEN
                7 -> currentCharacter!!.cooldown_reduction = CharacterBuilder.BASE_COOLDOWN
            }

            if(currentCharacter!!.current_health > currentCharacter!!.max_health)
            {
                currentCharacter!!.current_health = currentCharacter!!.max_health
            }

            if(currentCharacter!!.current_energy > currentCharacter!!.max_energy)
            {
                currentCharacter!!.current_energy = currentCharacter!!.max_energy
            }

        }
    }



    fun setToNull() {
        currentCharacter = null
    }
}
