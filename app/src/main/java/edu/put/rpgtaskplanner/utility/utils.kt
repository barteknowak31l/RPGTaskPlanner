package edu.put.rpgtaskplanner.utility


operator fun Number.plus(number: Number): Any {
    return this.toFloat() + number.toFloat()
}
