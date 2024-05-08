    package edu.put.rpgtaskplanner.model

    import android.content.Context
    import edu.put.rpgtaskplanner.R
    import edu.put.rpgtaskplanner.roomDAO.ItemDAO
    import edu.put.rpgtaskplanner.roomEntity.ItemEntity
    import edu.put.rpgtaskplanner.utility.UserManager


    enum class ItemType(val id: Int) {
    ARMOUR(0), // max health
    ARTIFACT(1), // energy regen
    BELT(2), // gold multiplier
    BOOTS(3), // exp multiplier
    HELMET(4), // max energy
    OFFHAND(5), // gold mult
    RING(6), //  health regen
    WEAPON(7); // cooldown
        companion object {
            fun getItemTypeNameById(id: Int): String? {
                return entries.find { it.id == id }?.name
            }
        }

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
        fun resolveItemStatStringFromType(statValueString: String, type: Int, character_class: Int, context: Context) : String
        {
            var itemStatString = ""
            when(type)
            {
                0->{
                    itemStatString = context.getString(R.string.item_stats_armour, statValueString)
                }
                1->{
                        when(character_class) {
                            0->{
                                itemStatString = context.getString(R.string.item_stats_artifact_warrior, statValueString)
                            }
                            1->{
                                itemStatString = context.getString(R.string.item_stats_artifact_rogue, statValueString)

                            }
                            2->{
                                itemStatString = context.getString(R.string.item_stats_artifact_mage, statValueString)
                            }
                        }

                }
                2->{
                    itemStatString = context.getString(R.string.item_stats_gold_belt, statValueString)

                }
                3->{
                    itemStatString = context.getString(R.string.item_stats_exp_boots, statValueString)
                }
                4->{
                        when(character_class) {
                            0->{
                                itemStatString = context.getString(R.string.item_stats_helmet_warrior, statValueString)
                            }
                            1->{
                                itemStatString = context.getString(R.string.item_stats_helmet_rogue, statValueString)

                            }
                            2->{
                                itemStatString = context.getString(R.string.item_stats_helmet_mage, statValueString)
                            }
                        }

                }
                5->{
                    itemStatString = context.getString(R.string.item_stats_gold_offhand, statValueString)

                }
                6->{
                    itemStatString = context.getString(R.string.item_stats_ring, statValueString)

                }
                7->{
                    itemStatString = context.getString(R.string.item_stats_weapon, statValueString)
                }
            }
            return itemStatString
        }

        fun resolveItemTypeStringFromType(itemType: Int, character_class: Int, context: Context) : String
        {
            var typeString: String = ""
            when(itemType)
            {
                0->{
                    typeString = context.getString(R.string.item_type_armour)
                }
                1->{
                    typeString = context.getString(R.string.item_type_artifact)

                }
                2->{
                    typeString = context.getString(R.string.item_type_belt)

                }
                3->{
                    typeString = context.getString(R.string.item_type_boots)

                }
                4->{
                    typeString = context.getString(R.string.item_type_helmet)

                }
                5->{
                    typeString = context.getString(R.string.item_type_offhand)
                }
                6->{
                    typeString = context.getString(R.string.item_type_ring)
                }
                7->{
                    typeString = context.getString(R.string.item_type_weapon)
                }
            }
            return typeString
        }
    }

}