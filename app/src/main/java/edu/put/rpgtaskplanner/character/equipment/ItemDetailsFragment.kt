package edu.put.rpgtaskplanner.character.equipment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.EquipmentHandler
import edu.put.rpgtaskplanner.utility.EquipmentManager
import edu.put.rpgtaskplanner.utility.UserManager
import edu.put.rpgtaskplanner.model.Character

class ItemDetailsFragment : Fragment(), EquipmentHandler.EquipmentHandlerCallback {

    private var equipmentHandler: EquipmentHandler? = null
    private lateinit var itemTypeTextView: TextView
    private lateinit var enhancedStatTextView: TextView
    private lateinit var enhancementValueTextView: TextView
    private lateinit var itemDescriptionTextView: TextView
    private lateinit var itemDisplayImageView: ImageView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        equipmentHandler = EquipmentHandler(requireContext(),requireActivity(), lifecycleScope)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_item_details, container, false)
        arguments?.getString("name").let {
            val itemName = it.toString()
            val itemNameTextView = view.findViewById<TextView>(R.id.itemNameTextView)
            itemNameTextView.text = itemName
        }

        val equipButton: Button = view.findViewById(R.id.equipButton)
        equipButton.setOnClickListener{equipButtonOnclick()}

        itemTypeTextView = view.findViewById(R.id.itemTypeTextView)
        enhancedStatTextView = view.findViewById(R.id.enhancedStatTextView)
        enhancementValueTextView = view.findViewById(R.id.enhancementValueTextView)
        itemDescriptionTextView = view.findViewById(R.id.itemDescriptionTextView)
        itemDisplayImageView = view.findViewById(R.id.itemImageView)

        val currentItem = EquipmentManager.getCurrentItem()

        val character = CharacterManager.getCurrentCharacter()
        if(currentItem != null && character != null)
        {
            var typeString = Item.resolveItemTypeStringFromType(currentItem.type, character.character_class, requireContext())
            itemTypeTextView.text = getString(R.string.item_type_activity_shop, typeString)
            enhancedStatTextView.text = Item.resolveItemStatStringFromType(currentItem.base_bonus.toString(), currentItem.type, character.character_class, requireContext())
            enhancementValueTextView.text = getString(R.string.enhancement_value_activity_item_details, Character.resolveStatStringOnItemType(currentItem.type, character))
            itemDescriptionTextView.text = getString(R.string.item_description_header_activity_item_details, currentItem.description)
            val drawable = ContextCompat.getDrawable(requireContext(), currentItem.image_resource_id)
            itemDisplayImageView.setImageDrawable(drawable)
        }
        return view;
    }

    private fun equipButtonOnclick()
    {
        val item = EquipmentManager.getCurrentItem()
        val user = UserManager.getCurrentUser()
        if(user != null && item != null)
        {
            equipmentHandler?.equipItem(user.character_id,item, this)
        }
    }

    override fun onItemEquipped(item: Item?) {
        Toast.makeText(context,getString(R.string.item_equipped_item_details_fragment, item?.item_name),Toast.LENGTH_SHORT).show()
        val character = CharacterManager.getCurrentCharacter()
        if(item != null && character != null)
        {
            enhancementValueTextView.text = getString(R.string.enhancement_value_activity_item_details, Character.resolveStatStringOnItemType(item.type, character))
        }

    }

    override fun onItemsFetchedFromFirestore(items: List<Item>?) {

    }

}