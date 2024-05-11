
package edu.put.rpgtaskplanner.shop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.equipment.EquipmentFragment
import edu.put.rpgtaskplanner.character.equipment.ItemDetailsActivity
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.repository.UserRepository
import edu.put.rpgtaskplanner.utility.EquipmentManager
import edu.put.rpgtaskplanner.utility.ShopSupplier
import edu.put.rpgtaskplanner.utility.UserManager
import java.util.Calendar
import java.util.Date

class ShopFragment : Fragment(), ShopSupplier.RefreshShopCallback,
    ShopSupplier.OnDeleteItemListener {

    interface  ShopItemClickListener{
        fun onShopItemClick(position: Int)
    }

    private lateinit var itemClickListener: ShopItemClickListener

    private val db = Firebase.firestore
    private val userRepository = UserRepository(db)


    private var shopSupplier: ShopSupplier? = null
    private var names: List<String> = listOf()
    private var images: List<Int> = listOf()
    private lateinit var adapter: EquipmentFragment.CustomRecyclerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemClickListener = context as ShopItemClickListener
        shopSupplier = ShopSupplier(requireContext(),requireActivity(),lifecycleScope)
        ShopSupplier.listeners += this
    }

    override fun onDetach() {
        super.onDetach()
        ShopSupplier.listeners -= this

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val recyclerView = inflater.inflate(R.layout.fragment_shop, container, false) as RecyclerView


        val user = UserManager.getCurrentUser()

        if(user != null)
        {

            if(user.next_shop_refresh < Date())
            {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                user.next_shop_refresh = calendar.time
                userRepository.saveUser(user) {
                    shopSupplier?.refreshShop(this)
                    Log.d("SHOP", "SHOP REFRESHED!")
                }

            }

        }

        if(user != null)
        {
            shopSupplier?.fetchShopFromLocalDb(this, user.character_id)
        }


        // przypisanie listy do adaptera powinio być w on refresh finished
        adapter = EquipmentFragment.CustomRecyclerAdapter(emptyArray(), IntArray(0), emptyList())
        recyclerView.adapter = adapter

        adapter.setListener(object : EquipmentFragment.CustomRecyclerAdapter.Listener {
            override fun onClick(position: Int) {
                itemClickListener.onShopItemClick(position)

            }
        })

        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager=layoutManager

        return recyclerView
    }

    override fun onRefreshFinished() {
        val itemList = shopSupplier?.itemList()

        var matchingIndices = itemList?.let { EquipmentManager.getMatchingIndicesAsBooleans(it) }


        if (itemList != null)
        {
            shopItemList = itemList
            names = itemList.map { it.item_name }
            //TODO create splasharts for items and set their ids in database
            // images = itemList.map { it.image_resource_id }
            images = itemList.map { R.drawable.rpg_logo_sm }
            adapter.setItemList(names.toTypedArray(), images.toIntArray(), matchingIndices.orEmpty())
        }
    }

    override fun onShopFetchedFromDatabase(itemList: List<Item>) {
            shopItemList = itemList

            var matchingIndices = itemList.let { EquipmentManager.getMatchingIndicesAsBooleans(it) }


            names = itemList.map { it.item_name }
            //TODO create splasharts for items and set their ids in database
            // images = itemList.map { it.image_resource_id }
            images = itemList.map { R.drawable.rpg_logo_sm }
            adapter.setItemList(names.toTypedArray(), images.toIntArray(),matchingIndices.orEmpty())
    }

    companion object
    {
        var shopItemList: List<Item> = listOf()
    }

    override fun onDeleteItem(item: Item) {

        val user = UserManager.getCurrentUser()
        if(user != null)
        {
            shopSupplier?.fetchShopFromLocalDb(this, user.character_id)
        }
    }

}