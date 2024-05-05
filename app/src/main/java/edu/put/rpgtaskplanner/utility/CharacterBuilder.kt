package edu.put.rpgtaskplanner.utility

import edu.put.rpgtaskplanner.model.Character
import edu.put.rpgtaskplanner.model.CharacterClass

class CharacterBuilder {
    private val character = Character()

    fun characterClass(characterClass: CharacterClass): CharacterBuilder {
        character.character_class = characterClass.id
        return this
    }

    fun cooldownReduction(cooldownReduction: Double): CharacterBuilder {
        character.cooldown_reduction = cooldownReduction
        return this
    }

    fun currentEnergy(currentEnergy: Double): CharacterBuilder {
        character.current_energy = currentEnergy
        return this
    }

    fun currentExperience(currentExperience: Double): CharacterBuilder {
        character.current_experience = currentExperience
        return this
    }

    fun currentHealth(currentHealth: Double): CharacterBuilder {
        character.current_health = currentHealth
        return this
    }

    fun energyRegen(energyRegen: Double): CharacterBuilder {
        character.energy_regen = energyRegen
        return this
    }

    fun expMultiplier(expMultiplier: Double): CharacterBuilder {
        character.exp_multiplier = expMultiplier
        return this
    }

    fun goldMultiplier(goldMultiplier: Double): CharacterBuilder {
        character.gold_multiplier = goldMultiplier
        return this
    }

    fun healthRegen(healthRegen: Double): CharacterBuilder {
        character.health_regen = healthRegen
        return this
    }

    fun level(level: Int): CharacterBuilder {
        character.level = level
        return this
    }

    fun maxEnergy(maxEnergy: Double): CharacterBuilder {
        character.max_energy = maxEnergy
        return this
    }

    fun maxHealth(maxHealth: Double): CharacterBuilder {
        character.max_health = maxHealth
        return this
    }

    fun characterName(characterName: String): CharacterBuilder {
        character.character_name = characterName
        return this
    }

    fun currentGold(currentGold: Double): CharacterBuilder
    {
        character.current_gold = currentGold
        return this
    }

    fun build(): Character {
        return character
    }


    fun buildBaseWarrior(): Character
    {
        return this
            .characterClass(CharacterClass.WARRIOR)
            .cooldownReduction(0.0)
            .currentEnergy(100.0)
            .currentExperience(0.0)
            .currentHealth(100.0)
            .currentGold(0.0)
            .energyRegen(10.0)
            .expMultiplier(1.0)
            .goldMultiplier(1.0)
            .healthRegen(10.0)
            .level(1)
            .maxEnergy(100.0)
            .maxHealth(100.0)
            .characterName("new character")
            .build()
    }

    fun buildBaseMage(): Character
    {
        return this
            .characterClass(CharacterClass.MAGE)
            .cooldownReduction(0.0)
            .currentEnergy(100.0)
            .currentExperience(0.0)
            .currentHealth(100.0)
            .currentGold(0.0)
            .energyRegen(10.0)
            .expMultiplier(1.0)
            .goldMultiplier(1.0)
            .healthRegen(10.0)
            .level(1)
            .maxEnergy(100.0)
            .maxHealth(100.0)

            .characterName("new character")
            .build()
    }

    fun buildBaseRogue(): Character
    {
        return this
            .characterClass(CharacterClass.ROGUE)
            .cooldownReduction(0.0)
            .currentEnergy(100.0)
            .currentExperience(0.0)
            .currentHealth(100.0)
            .currentGold(0.0)
            .energyRegen(10.0)
            .expMultiplier(1.0)
            .goldMultiplier(1.0)
            .healthRegen(10.0)
            .level(1)
            .maxEnergy(100.0)
            .maxHealth(100.0)
            .characterName("new character")
            .build()
    }


}
