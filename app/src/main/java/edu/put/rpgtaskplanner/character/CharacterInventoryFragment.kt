package edu.put.rpgtaskplanner.character

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.equipment.EquipmentActivity
import edu.put.rpgtaskplanner.model.CharacterClass
import edu.put.rpgtaskplanner.model.ItemType
import edu.put.rpgtaskplanner.model.Character
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.EquipmentManager


class CharacterInventoryFragment : Fragment() {

    private lateinit var characterDisplay: ImageView
    private lateinit var characterNameTextView: TextView

    private lateinit var armour: ImageView
    private lateinit var artifact: ImageView
    private lateinit var belt: ImageView
    private lateinit var boots: ImageView
    private lateinit var helmet: ImageView
    private lateinit var ring: ImageView
    private lateinit var offhand: ImageView
    private lateinit var weapon: ImageView


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

        helmet = view.findViewById<ImageView>(R.id.imageViewHelmet)
        helmet.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.HELMET.id)
            startActivity(intent)
        }

        armour = view.findViewById<ImageView>(R.id.imageViewArmour)
        armour.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.ARMOUR.id)
            startActivity(intent)
        }

        weapon = view.findViewById<ImageView>(R.id.imageViewLeftHand)
        weapon.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.WEAPON.id)
            startActivity(intent)
        }

        offhand = view.findViewById<ImageView>(R.id.imageViewRightHand)
        offhand.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.OFFHAND.id)
            startActivity(intent)
        }

        belt = view.findViewById<ImageView>(R.id.imageViewBelt)
        belt.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.BELT.id)

            startActivity(intent)
        }

        ring = view.findViewById<ImageView>(R.id.imageViewRing)
        ring.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.RING.id)
            startActivity(intent)
        }

        artifact = view.findViewById<ImageView>(R.id.imageViewArtifact)
        artifact.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.ARTIFACT.id)
            startActivity(intent)
        }

        boots = view.findViewById<ImageView>(R.id.imageViewBoots)
        boots.setOnClickListener {
            val intent = Intent(context, EquipmentActivity::class.java)
            intent.putExtra(INTENT_DATA.EQUIPMENT_TYPE.toString(),ItemType.BOOTS.id)
            startActivity(intent)
        }

        refreshDisplay()

        return view
    }

    override fun onResume() {
        super.onResume()
        refreshDisplay()
    }

    private fun refreshDisplay(){
        val equippedItems = EquipmentManager.getEquippedItems()
        if(equippedItems != null)
        {
            val equipped = equippedItems.firstOrNull { item -> item.type == ItemType.BOOTS.id }
            if(equipped != null)
            {
                val drawable = ContextCompat.getDrawable(requireContext(), equipped.image_resource_id)
                boots.setImageDrawable(drawable)
            }
        }
        if(equippedItems != null)
        {
            val equipped = equippedItems.firstOrNull { item -> item.type == ItemType.ARTIFACT.id }
            if(equipped != null)
            {
                val drawable = ContextCompat.getDrawable(requireContext(), equipped.image_resource_id)
                artifact.setImageDrawable(drawable)
            }
        }
        if(equippedItems != null)
        {
            val equipped = equippedItems.firstOrNull { item -> item.type == ItemType.RING.id }
            if(equipped != null)
            {
                val drawable = ContextCompat.getDrawable(requireContext(), equipped.image_resource_id)
                ring.setImageDrawable(drawable)
            }
        }
        if(equippedItems != null)
        {
            val equipped = equippedItems.firstOrNull { item -> item.type == ItemType.BELT.id }
            if(equipped != null)
            {
                val drawable = ContextCompat.getDrawable(requireContext(), equipped.image_resource_id)
                belt.setImageDrawable(drawable)
            }
        }
        if(equippedItems != null)
        {
            val equipped = equippedItems.firstOrNull { item -> item.type == ItemType.OFFHAND.id }
            if(equipped != null)
            {
                val drawable = ContextCompat.getDrawable(requireContext(), equipped.image_resource_id)
                offhand.setImageDrawable(drawable)
            }
        }
        if(equippedItems != null)
        {
            val equipped = equippedItems.firstOrNull { item -> item.type == ItemType.WEAPON.id }
            if(equipped != null)
            {
                val drawable = ContextCompat.getDrawable(requireContext(), equipped.image_resource_id)
                weapon.setImageDrawable(drawable)
            }
        }
        if(equippedItems != null)
        {
            val equipped = equippedItems.firstOrNull { item -> item.type == ItemType.ARMOUR.id }
            if(equipped != null)
            {
                val drawable = ContextCompat.getDrawable(requireContext(), equipped.image_resource_id)
                armour.setImageDrawable(drawable)
            }
        }
        if(equippedItems != null)
        {
            val equipped = equippedItems.firstOrNull { item -> item.type == ItemType.HELMET.id }
            if(equipped != null)
            {
                val drawable = ContextCompat.getDrawable(requireContext(), equipped.image_resource_id)
                helmet.setImageDrawable(drawable)
            }
        }

    }


}