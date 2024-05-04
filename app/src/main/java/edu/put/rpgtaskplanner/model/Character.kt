package edu.put.rpgtaskplanner.model

enum class CharacterClass(val id: Int) {
    WARRIOR(0),
    ROGUE(1),
    MAGE(2)
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
    var character_class: Number = 0
    var cooldown_reduction: Number = 0.0f
    var current_energy: Number = 0.0f
    var current_experience: Number = 0.0f
    var current_health: Number = 0.0f
    var energy_regen: Number = 0.0f
    var exp_multiplier: Number = 0.0f
    var gold_multiplier: Number = 0.0f
    var health_regen: Number = 0.0f
    var level: Number = 1
    var max_energy: Number = 100.0f
    var max_health: Number = 100.0f
    var character_name: String = ""
}