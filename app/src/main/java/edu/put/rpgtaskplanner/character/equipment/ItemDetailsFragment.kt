package edu.put.rpgtaskplanner.character.equipment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.utility.EquipmentHandler
import edu.put.rpgtaskplanner.utility.EquipmentManager
import edu.put.rpgtaskplanner.utility.UserManager

class ItemDetailsFragment : Fragment(), EquipmentHandler.EquipmentHandlerCallback {

    private var equipmentHandler: EquipmentHandler? = null

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
    }

}