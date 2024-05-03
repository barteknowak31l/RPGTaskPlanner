package edu.put.rpgtaskplanner.character

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.equipment.EquipmentActivity




class CharacterInventoryFragment : Fragment() {

    enum class INTENT_DATA{
        EQUIPMENT_TYPE
    }
    enum class EQUIPMENT_TYPES
    {
        HELMET,
        ARMOUR,
        WEAPON,
        OFFHAND,
        RING,
        ARTIFACT,
        BELT,
        BOOTS
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_inventory, container, false)

        val helmet = view.findViewById<ImageView>(R.id.imageViewHelmet)
        helmet.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),EQUIPMENT_TYPES.HELMET.toString())
            startActivity(intent)
        }

        val armour = view.findViewById<ImageView>(R.id.imageViewArmour)
        armour.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),EQUIPMENT_TYPES.ARMOUR.toString())
            startActivity(intent)
        }

        val weapon = view.findViewById<ImageView>(R.id.imageViewLeftHand)
        weapon.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),EQUIPMENT_TYPES.WEAPON.toString())
            startActivity(intent)
        }

        val offhand = view.findViewById<ImageView>(R.id.imageViewRightHand)
        offhand.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),EQUIPMENT_TYPES.OFFHAND.toString())
            startActivity(intent)
        }

        val belt = view.findViewById<ImageView>(R.id.imageViewBelt)
        belt.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),EQUIPMENT_TYPES.BELT.toString())
            startActivity(intent)
        }

        val ring = view.findViewById<ImageView>(R.id.imageViewRing)
        ring.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),EQUIPMENT_TYPES.RING.toString())
            startActivity(intent)
        }

        val artifact = view.findViewById<ImageView>(R.id.imageViewArtifact)
        artifact.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),EQUIPMENT_TYPES.ARTIFACT.toString())
            startActivity(intent)
        }

        val boots = view.findViewById<ImageView>(R.id.imageViewBoots)
        boots.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),EQUIPMENT_TYPES.BOOTS.toString())
            startActivity(intent)
        }
        
        return view
    }


}