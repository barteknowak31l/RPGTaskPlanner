package edu.put.rpgtaskplanner.utility

import edu.put.rpgtaskplanner.model.Character
import edu.put.rpgtaskplanner.model.CharacterClass

class CharacterBuilder {
    private val character = Character()

    fun characterClass(characterClass: CharacterClass): CharacterBuilder {
        character.character_class = characterClass.id
        return this
    }

    fun cooldownReduction(cooldownReduction: Number): CharacterBuilder {
        character.cooldown_reduction = cooldownReduction
        return this
    }

    fun currentEnergy(currentEnergy: Number): CharacterBuilder {
        character.current_energy = currentEnergy
        return this
    }

    fun currentExperience(currentExperience: Number): CharacterBuilder {
        character.current_experience = currentExperience
        return this
    }

    fun currentHealth(currentHealth: Number): CharacterBuilder {
        character.current_health = currentHealth
        return this
    }

    fun energyRegen(energyRegen: Number): CharacterBuilder {
        character.energy_regen = energyRegen
        return this
    }

    fun expMultiplier(expMultiplier: Number): CharacterBuilder {
        character.exp_multiplier = expMultiplier
        return this
    }

    fun goldMultiplier(goldMultiplier: Number): CharacterBuilder {
        character.gold_multiplier = goldMultiplier
        return this
    }

    fun healthRegen(healthRegen: Number): CharacterBuilder {
        character.health_regen = healthRegen
        return this
    }

    fun level(level: Number): CharacterBuilder {
        character.level = level
        return this
    }

    fun maxEnergy(maxEnergy: Number): CharacterBuilder {
        character.max_energy = maxEnergy
        return this
    }

    fun maxHealth(maxHealth: Number): CharacterBuilder {
        character.max_health = maxHealth
        return this
    }

    fun characterName(characterName: String): CharacterBuilder {
        character.character_name = characterName
        return this
    }

    fun build(): Character {
        return character
    }


    fun buildBaseWarrior(): Character
    {
        return this
            .characterClass(CharacterClass.WARRIOR)
            .cooldownReduction(0)
            .currentEnergy(100)
            .currentExperience(0)
            .currentHealth(100)
            .energyRegen(10)
            .expMultiplier(1)
            .goldMultiplier(1)
            .healthRegen(10)
            .level(1)
            .maxEnergy(100)
            .maxHealth(100)
            .characterName("new character")
            .build()
    }

    fun buildBaseMage(): Character
    {
        return this
            .characterClass(CharacterClass.MAGE)
            .cooldownReduction(0)
            .currentEnergy(100)
            .currentExperience(0)
            .currentHealth(100)
            .energyRegen(10)
            .expMultiplier(1)
            .goldMultiplier(1)
            .healthRegen(10)
            .level(1)
            .maxEnergy(100)
            .maxHealth(100)
            .characterName("new character")
            .build()
    }

    fun buildBaseRogue(): Character
    {
        return this
            .characterClass(CharacterClass.ROGUE)
            .cooldownReduction(0)
            .currentEnergy(100)
            .currentExperience(0)
            .currentHealth(100)
            .energyRegen(10)
            .expMultiplier(1)
            .goldMultiplier(1)
            .healthRegen(10)
            .level(1)
            .maxEnergy(100)
            .maxHealth(100)
            .characterName("new character")
            .build()
    }


}
