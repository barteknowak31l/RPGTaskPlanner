package edu.put.rpgtaskplanner.shop

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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.repository.CharacterRepository
import edu.put.rpgtaskplanner.repository.ItemRepository
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.ShopSupplier
import edu.put.rpgtaskplanner.utility.UserManager

class TransactionDetailsFragment : Fragment(), ShopSupplier.OnDeleteItemListener{


    private lateinit var buyButton: Button
    private var price: Double = 0.0
    private val db = Firebase.firestore
    private val characterRepository = CharacterRepository(db)
    private val itemRepository = ItemRepository(db)
    private var shopSupplier: ShopSupplier? = null

    private lateinit var itemNameTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var itemStatTextView: TextView
    private lateinit var currentStatTextView: TextView
    private lateinit var currentGoldTextView: TextView


    override fun onAttach(context: Context) {
        super.onAttach(context)
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

        val view = inflater.inflate(R.layout.fragment_transaction_details, container, false)

        var itemType = 0

        buyButton = view.findViewById(R.id.buyButton)
        buyButton.setOnClickListener{
            onBuyButtonClick()
        }

        itemNameTextView = view.findViewById<TextView>(R.id.itemNameTextView)
        priceTextView = view.findViewById<TextView>(R.id.priceTextView)
        typeTextView = view.findViewById<TextView>(R.id.typeTextView)
        itemStatTextView = view.findViewById<TextView>(R.id.itemStatTextView)
        currentStatTextView = view.findViewById<TextView>(R.id.currentStatisticTextView)
        currentGoldTextView = view.findViewById(R.id.currentGoldTextView)

        val character = CharacterManager.getCurrentCharacter()
        if(character != null)
        {
            currentGoldTextView.text = getString(R.string.current_gold_shop,character.current_gold.toString())
        }


        if(arguments?.getString("itemName") != null)
        {
            arguments?.getString("itemName").let {
                val itemName = it.toString()

                itemNameTextView.text = getString(R.string.item_name_activity_shop) + ": " + itemName
            }

            arguments?.getDouble("price").let {
                val priceString = it.toString()
                if (it != null) {
                    price = it
                }
                priceTextView.text = getString(R.string.price_activity_shop) + ": " + priceString
            }

            arguments?.getInt("itemType").let {

                var type = ""

                if (it != null) {
                    itemType = it
                }
                val character = CharacterManager.getCurrentCharacter()
                if(character != null)
                {
                    type = Item.resolveItemTypeStringFromType(itemType, character.character_class, requireContext())
                }

                typeTextView.text = getString(R.string.item_type_activity_shop, type)
            }

            arguments?.getDouble("itemStat").let {

                var itemStatString = ""
                val character = CharacterManager.getCurrentCharacter()

                if(character != null)
                {
                    itemStatString = Item.resolveItemStatStringFromType(it.toString(),itemType,character.character_class,requireContext())
                }

                itemStatTextView.text = itemStatString
            }

            arguments?.getDouble("currentStat").let {
                val currentStat = it.toString()
                currentStatTextView.text = getString(R.string.current_stat_activity_shop) + ": " + currentStat
            }
        }
        else
        {
            clearTransactionDetails()
        }

        return view
    }


    fun onBuyButtonClick(){


        val item = ShopActivity.selectedItem
        val character = CharacterManager.getCurrentCharacter()
        val user = UserManager.getCurrentUser()

        if(character != null && user != null && item != null)
        {
            // sprawdz czy ma wystarczajaco golda
            if(character.current_gold >= price)
            {
                // pobierz koszt
                character.current_gold -= price
                val updateMap = mapOf(
                    CharacterRepository.CharacterFields.current_gold to character.current_gold
                )
                characterRepository.updateCharacter(user.character_id,updateMap) { success ->
                    if(success)
                    {

                    }
                    else {

                    }
                }
                // usun item ze sklepu
                shopSupplier?.deleteItemFromShop(item, user.character_id)

                // dodaj item do ekwipunku
                itemRepository.saveItemToCharacterEquipment(user.character_id, item) { success ->
                    if(success)
                    {
                        Toast.makeText(context, getString(R.string.toast_item_bought), Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(context, getString(R.string.toast_item_bought_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {
                Toast.makeText(context,getString(R.string.toast_item_too_expensive),Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clearTransactionDetails()
    {
        itemNameTextView.text = getString(R.string.shop_empty_item_name)
        priceTextView.text = getString(R.string.shop_empty_item_name)
        typeTextView.text = getString(R.string.shop_empty_item_name)
        itemStatTextView.text = getString(R.string.shop_empty_item_name)
        currentStatTextView.text = getString(R.string.shop_empty_item_name)
    }

    override fun onDeleteItem(item: Item) {
        clearTransactionDetails()
    }

}