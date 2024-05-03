package edu.put.rpgtaskplanner.character.equipment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.put.rpgtaskplanner.R

class ItemDetailsFragment : Fragment() {
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



        return view;
    }
}