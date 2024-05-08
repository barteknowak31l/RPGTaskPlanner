package edu.put.rpgtaskplanner.utility

import edu.put.rpgtaskplanner.model.Character
import edu.put.rpgtaskplanner.model.CharacterClass

class CharacterBuilder {
    private val character = Character()

    companion object{
        val BASE_COOLDOWN = 0.0
        val BASE_ENERGY = 100.0
        val BASE_GOLD = 0.0
        val BASE_ENERGY_REGEN = 10.0
        val BASE_EXP_MULTIPLIER = 1.0
        val BASE_GOLD_MULTIPLIER = 1.0
        val BASE_HEALTH_REGEN = 10.0
        val BASE_HEALTH = 100.0
    }
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
            .cooldownReduction(BASE_COOLDOWN)
            .currentEnergy(BASE_ENERGY)
            .currentExperience(0.0)
            .currentHealth(BASE_HEALTH)
            .currentGold(BASE_GOLD)
            .energyRegen(BASE_ENERGY_REGEN)
            .expMultiplier(BASE_EXP_MULTIPLIER)
            .goldMultiplier(BASE_GOLD_MULTIPLIER)
            .healthRegen(BASE_HEALTH_REGEN)
            .level(1)
            .maxEnergy(BASE_ENERGY)
            .maxHealth(BASE_HEALTH)
            .characterName("new character")
            .build()
    }

    fun buildBaseMage(): Character
    {
        return this
            .characterClass(CharacterClass.MAGE)
            .cooldownReduction(BASE_COOLDOWN)
            .currentEnergy(BASE_ENERGY)
            .currentExperience(0.0)
            .currentHealth(BASE_HEALTH)
            .currentGold(BASE_GOLD)
            .energyRegen(BASE_ENERGY_REGEN)
            .expMultiplier(BASE_EXP_MULTIPLIER)
            .goldMultiplier(BASE_GOLD_MULTIPLIER)
            .healthRegen(BASE_HEALTH_REGEN)
            .level(1)
            .maxEnergy(BASE_ENERGY)
            .maxHealth(BASE_HEALTH)
            .characterName("new character")
            .build()
    }

    fun buildBaseRogue(): Character
    {
        return this
            .characterClass(CharacterClass.ROGUE)
            .cooldownReduction(BASE_COOLDOWN)
            .currentEnergy(BASE_ENERGY)
            .currentExperience(0.0)
            .currentHealth(BASE_HEALTH)
            .currentGold(BASE_GOLD)
            .energyRegen(BASE_ENERGY_REGEN)
            .expMultiplier(BASE_EXP_MULTIPLIER)
            .goldMultiplier(BASE_GOLD_MULTIPLIER)
            .healthRegen(BASE_HEALTH_REGEN)
            .level(1)
            .maxEnergy(BASE_ENERGY)
            .maxHealth(BASE_HEALTH)
            .characterName("new character")
            .build()
    }


}
