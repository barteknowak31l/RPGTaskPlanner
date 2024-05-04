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
}