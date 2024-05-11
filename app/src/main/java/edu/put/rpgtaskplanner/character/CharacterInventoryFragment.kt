package edu.put.rpgtaskplanner.character

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.equipment.EquipmentActivity
import edu.put.rpgtaskplanner.model.CharacterClass
import edu.put.rpgtaskplanner.model.ItemType
import edu.put.rpgtaskplanner.model.Character
import edu.put.rpgtaskplanner.utility.CharacterManager


class CharacterInventoryFragment : Fragment() {

    private lateinit var characterDisplay: ImageView
    private lateinit var characterNameTextView: TextView


    enum class INTENT_DATA{
        EQUIPMENT_TYPE
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_inventory, container, false)

        characterDisplay = view.findViewById(R.id.characterDisplay)
        characterNameTextView = view.findViewById(R.id.textViewCharacterName)

        val character = CharacterManager.getCurrentCharacter()
        if(character != null)
        {
            val char_class = CharacterClass.fromId(character.character_class)
            if(char_class != null)
            {
                Character.setCharacterDisplay(char_class,character.character_name, characterDisplay,characterNameTextView)
            }
        }

        val helmet = view.findViewById<ImageView>(R.id.imageViewHelmet)
        helmet.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.HELMET.id)
            startActivity(intent)
        }

        val armour = view.findViewById<ImageView>(R.id.imageViewArmour)
        armour.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.ARMOUR.id)
            startActivity(intent)
        }

        val weapon = view.findViewById<ImageView>(R.id.imageViewLeftHand)
        weapon.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.WEAPON.id)
            startActivity(intent)
        }

        val offhand = view.findViewById<ImageView>(R.id.imageViewRightHand)
        offhand.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.OFFHAND.id)
            startActivity(intent)
        }

        val belt = view.findViewById<ImageView>(R.id.imageViewBelt)
        belt.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.BELT.id)

            startActivity(intent)
        }

        val ring = view.findViewById<ImageView>(R.id.imageViewRing)
        ring.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.RING.id)
            startActivity(intent)
        }

        val artifact = view.findViewById<ImageView>(R.id.imageViewArtifact)
        artifact.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.ARTIFACT.id)
            startActivity(intent)
        }

        val boots = view.findViewById<ImageView>(R.id.imageViewBoots)
        boots.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.BOOTS.id)
            startActivity(intent)
        }
        
        return view
    }


}