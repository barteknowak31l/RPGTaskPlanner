    package edu.put.rpgtaskplanner.model

    import edu.put.rpgtaskplanner.roomDAO.ItemDAO
    import edu.put.rpgtaskplanner.roomEntity.ItemEntity
    import edu.put.rpgtaskplanner.utility.UserManager


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

    var base_bonus: Double = 0.0
    var description: String = ""
    var image_resource_id: Int = 0
    var level: Int = 0
    var price: Double = 0.0
    var type: Int = 0
    var item_name: String = ""

    companion object
    {
        fun toEntity(item: Item): ItemEntity
        {
            val user = UserManager.getCurrentUser()!!
            return ItemEntity(
                item.item_name,
                user.character_id,
                item.base_bonus,
                item.description,
                item.image_resource_id,
                item.level,
                item.price,
                item.type
            )
        }

        fun fromEntity(entity: ItemEntity): Item
        {
            var item = Item()
            item.item_name = entity.item_name!!
            item.base_bonus = entity.base_bonus!!
            item.description = entity.description!!
            item.image_resource_id = entity.image_resource_id!!
            item.price = entity.price!!
            item.type = entity.type!!
            item.level = entity.level!!
            return item
        }

    }

}