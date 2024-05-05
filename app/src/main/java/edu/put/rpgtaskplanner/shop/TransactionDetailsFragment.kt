package edu.put.rpgtaskplanner.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.equipment.EquipmentFragment

class TransactionDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        EquipmentFragment.ItemType.valueOf("HELMET")

        val view = inflater.inflate(R.layout.fragment_transaction_details, container, false)

        arguments?.getString("itemName").let {
            val itemName = it.toString()
            val itemNameTextView = view.findViewById<TextView>(R.id.itemNameTextView)

            itemNameTextView.text = getString(R.string.item_name_activity_shop) + ": " + itemName
        }

        arguments?.getDouble("price").let {
            val price = it.toString()
            val priceTextView = view.findViewById<TextView>(R.id.priceTextView)

            priceTextView.text = getString(R.string.price_activity_shop) + ": " + price
        }

        arguments?.getInt("itemType").let {

            var type = ""
            when(it)
            {
                0->{
                    type = getString(R.string.item_type_armour)
                }
                1->{
                    type = getString(R.string.item_type_artifact)

                }
                2->{
                    type = getString(R.string.item_type_belt)

                }
                3->{
                    type = getString(R.string.item_type_boots)

                }
                4->{
                    type = getString(R.string.item_type_helmet)

                }
                5->{
                    type = getString(R.string.item_type_offhand)
                }
                6->{
                    type = getString(R.string.item_type_ring)
                }
                7->{
                    type = getString(R.string.item_type_weapon)
                }
            }

            val typeTextView = view.findViewById<TextView>(R.id.typeTextView)
            typeTextView.text = getString(R.string.item_type_activity_shop) + ": " +type
        }

        arguments?.getDouble("itemStat").let {
            val itemStat = it.toString()
            val itemStatTextView = view.findViewById<TextView>(R.id.itemStatTextView)
            itemStatTextView.text = getString(R.string.item_stat_activity_shop) + ": " + itemStat
        }

        arguments?.getDouble("currentStat").let {
            val currentStat = it.toString()
            val currentStatTextView = view.findViewById<TextView>(R.id.currentStatisticTextView)
            currentStatTextView.text = getString(R.string.current_stat_activity_shop) + ": " + currentStat
        }

        return view
    }
}