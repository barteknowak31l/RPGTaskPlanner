package edu.put.rpgtaskplanner.model

import android.widget.ImageView
import android.widget.TextView
import edu.put.rpgtaskplanner.R
import java.util.Date

enum class CharacterClass(val id: Int) {
    WARRIOR(0),
    ROGUE(1),
    MAGE(2);

    companion object {
        fun fromId(id: Int): CharacterClass? {
            return entries.find { it.id == id }
        }
    }
}

enum class StatisticTypes(val id: Int)
{
    COOLDOWN_REDUCTION(0),
    CURRENT_ENERGY(1),
    CURRENT_EXPERIENCE(2),
    CURRENT_HEALTH(3),
    ENERGY_REGEN(4),
    EXP_MULTIPLIER(5),
    GOLD_MULTIPLIER(6),
    HEALTH_REGEN(7),
    LEVEL(8),
    MAX_ENERGY(9),
    MAX_HEALTH(10),
}

class Character {
    var character_class: Int = 0
    var cooldown_reduction: Double = 0.0
    var current_energy: Double = 0.0
    var current_experience: Double = 0.0
    var current_health: Double = 0.0
    var energy_regen: Double = 0.0
    var exp_multiplier: Double = 0.0
    var gold_multiplier: Double = 0.0
    var health_regen: Double = 0.0
    var level: Int = 1
    var max_energy: Double = 100.0
    var max_health: Double = 100.0
    var character_name: String = ""
    var current_gold: Double = 0.0

    lateinit var last_resource_refresh_date: Date
    var resource_refresh_cooldown_minutes:Int = 60

    companion object
    {
        fun resolveStatStringOnItemType(itemType: Int, character: Character): String
        {
            return when(itemType)
            {
                0 ->  character.max_health.toString()
                1 ->  character.energy_regen.toString()
                2 ->  character.gold_multiplier.toString()
                3 ->  character.exp_multiplier.toString()
                4 ->  character.max_energy.toString()
                5 ->  character.gold_multiplier.toString()
                6 ->  character.health_regen.toString()
                7 ->  character.cooldown_reduction.toString()
                else -> ""
            }

        }

        fun resolveStatOnItemType(itemType: Int, character: Character): Double
        {
            return when(itemType)
            {
                0 ->  character.max_health
                1 ->  character.energy_regen
                2 ->  character.gold_multiplier
                3 ->  character.exp_multiplier
                4 ->  character.max_energy
                5 ->  character.gold_multiplier
                6 ->  character.health_regen
                7 ->  character.cooldown_reduction
                else -> -1.0
            }

        }
        fun setCharacterDisplay(currentClass: CharacterClass, characterName: String, characterDisplay: ImageView, nameDisplay: TextView?)
        {
            if(nameDisplay != null)
            {
                nameDisplay.text = characterName
            }

            when (currentClass)
            {
                CharacterClass.WARRIOR -> {
                    characterDisplay.setImageResource(R.drawable.karlach)
                }

                CharacterClass.ROGUE -> {
                    characterDisplay.setImageResource(R.drawable.astarion)
                }

                CharacterClass.MAGE -> {
                    characterDisplay.setImageResource(R.drawable.gale)
                }
            }
        }
    }
}