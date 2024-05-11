package edu.put.rpgtaskplanner.character.equipment

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.repository.ItemRepository
import edu.put.rpgtaskplanner.utility.EquipmentManager
import edu.put.rpgtaskplanner.utility.UserManager
import java.util.stream.Collectors


class EquipmentFragment : Fragment() {


    private val db = Firebase.firestore
    private val itemRepository = ItemRepository(db)
    private lateinit var adapter: CustomRecyclerAdapter
    private var itemType:Int? = 0

    companion object{
        var equipmentItemList: List<Item> = listOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val recyclerView = inflater.inflate(R.layout.fragment_equipment, container, false) as RecyclerView

        arguments?.getInt("type").let { itemType = it }

        val user = UserManager.getCurrentUser()
        if( user != null)
        {
            itemRepository.getItemsFromCharacterEquipment(user.character_id) {onOwnedItemFetchedCallback(it)}
        }

        adapter = CustomRecyclerAdapter(emptyArray(), IntArray(0), emptyList())
        recyclerView.setAdapter(adapter)

        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager=layoutManager

        return recyclerView
    }

    class CustomRecyclerAdapter(private var captions: Array<String>, private var imageIds: IntArray, private var isEquipped: List<Boolean>) :
        RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>() {

        private var listener: Listener? = null

        interface Listener {
            fun onClick(position: Int)
            fun onLongClick(position: Int)
        }

        fun setListener(listener: Listener?) {
            this.listener = listener
        }

        fun setItemList(newCaptions: Array<String>, newImageIds: IntArray, newIsEquipped: List<Boolean>) {
            captions = newCaptions
            imageIds = newImageIds
            isEquipped = newIsEquipped
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cardView: CardView = itemView as CardView
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val cv = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_captioned_image, parent, false) as CardView
            return ViewHolder(cv)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val cardView = holder.cardView
            val imageView = cardView.findViewById<View>(R.id.info_image) as ImageView
            val drawable = ContextCompat.getDrawable(cardView.context, imageIds[position])
            imageView.setImageDrawable(drawable)
            imageView.contentDescription = captions[position]
            var textView = cardView.findViewById<View>(R.id.info_text) as TextView
            textView.text = captions[position]
            if(isEquipped.isNotEmpty() && isEquipped[position])
            {
                textView.setTextColor(ContextCompat.getColor(cardView.context, R.color.equipped_item_name_color))
            }
            else if(isEquipped.isNotEmpty() && !isEquipped[position])
            {
                textView.setTextColor(ContextCompat.getColor(cardView.context, R.color.text_color))

            }

            cardView.setBackgroundColor(ContextCompat.getColor(cardView.context, R.color.button_color))

            cardView.setOnClickListener {
                listener?.onClick(position)
            }

            cardView.setOnLongClickListener {
                listener?.onLongClick(position)
                true
            }

        }

        override fun getItemCount(): Int {
            return captions.size
        }
    }

    fun onOwnedItemFetchedCallback(ownedItems: List<Item>)
    {

        equipmentItemList = ownedItems.stream().filter {it.type == itemType}.collect(Collectors.toList())

        var matchingIndices = EquipmentManager.getMatchingIndicesAsBooleans(equipmentItemList)

        val names = equipmentItemList.map { it.item_name }
        //TODO create splasharts for items and set their ids in database
        // images = itemList.map { it.image_resource_id }
        val images = equipmentItemList.map { R.drawable.rpg_logo_sm }
        adapter.setItemList(names.toTypedArray(), images.toIntArray(),matchingIndices.orEmpty() )

        adapter.setListener(object : CustomRecyclerAdapter.Listener {
            override fun onClick(position: Int) {
                EquipmentManager.setCurrentItem(equipmentItemList[position])
                val intent = Intent(context, ItemDetailsActivity::class.java);
                intent.putExtra("name",names[position])
                startActivity(intent)
            }

            override fun onLongClick(position: Int) {
                EquipmentManager.setCurrentItem(equipmentItemList[position])
                val intent = Intent(context, ItemDetailsActivity::class.java);
                intent.putExtra("name",names[position])
                startActivity(intent)
                true
            }
        })
    }

    override fun onResume() {
        super.onResume()

        val user = UserManager.getCurrentUser()
        if( user != null)
        {
            itemRepository.getItemsFromCharacterEquipment(user.character_id) {onOwnedItemFetchedCallback(it)}
        }
    }


}