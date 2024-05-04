package edu.put.rpgtaskplanner.model


enum class ItemType(val id: Int) {
    ARMOUR(0),
    ARTIFACT(1),
    BELT(2),
    BOOTS(3),
    HELMET(4),
    OFFHAND(5),
    RING(6),
    WEAPON(7)
}
class Item {

    var bonus: Number = 0.0f
    var description: String = ""
    var image_resource_id: Number = 0
    var level: Number = 0
    var price: Number = 0
    var type: Number = 0
    var task_name: String = ""

}