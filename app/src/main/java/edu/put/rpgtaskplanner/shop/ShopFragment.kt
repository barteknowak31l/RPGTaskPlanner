
package edu.put.rpgtaskplanner.shop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.equipment.EquipmentFragment
import edu.put.rpgtaskplanner.character.equipment.ItemDetailsActivity

class ShopFragment : Fragment() {

    interface  ShopItemClickListener{
        fun onShopItemClick(position: Int)
    }

    private lateinit var itemClickListener: ShopItemClickListener

    private var equipmentItemList: List<EquipmentFragment.EquipmentItem> = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemClickListener = context as ShopItemClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val recyclerView = inflater.inflate(R.layout.fragment_shop, container, false) as RecyclerView

        equipmentItemList = EquipmentFragment.getItems()
        val names = equipmentItemList.map { it.name }
        val images = equipmentItemList.map { it.imageResourceId }

        val adapter =
            EquipmentFragment.CustomRecyclerAdapter(names.toTypedArray(), images.toIntArray())
        recyclerView.setAdapter(adapter)

        adapter.setListener(object : EquipmentFragment.CustomRecyclerAdapter.Listener {
            override fun onClick(position: Int) {
                Toast.makeText(context, "Clicked on "+ names[position], Toast.LENGTH_SHORT).show()
                itemClickListener.onShopItemClick(position)

            }
        })

        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager=layoutManager

        return recyclerView
    }
}