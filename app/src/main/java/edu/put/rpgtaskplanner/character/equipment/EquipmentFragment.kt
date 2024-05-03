package edu.put.rpgtaskplanner.character.equipment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.put.rpgtaskplanner.R

class EquipmentFragment : Fragment() {

    private var equipmentItemList: List<EquipmentItem> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val recyclerView = inflater.inflate(R.layout.fragment_equipment, container, false) as RecyclerView

        equipmentItemList = getItems()
        val names = equipmentItemList.map { it.name }
        val images = equipmentItemList.map { it.imageResourceId }

        val adapter = CustomRecyclerAdapter(names.toTypedArray(), images.toIntArray())
        recyclerView.setAdapter(adapter)

        adapter.setListener(object : CustomRecyclerAdapter.Listener {
            override fun onClick(position: Int) {
                Toast.makeText(context, "Clicked on "+ names[position], Toast.LENGTH_SHORT).show()
                val intent = Intent(context, ItemDetailsActivity::class.java);
                intent.putExtra("name",names[position])
                startActivity(intent)
            }
        })

        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager=layoutManager

        return recyclerView
    }

    enum class ItemType{
        HELMET,
        ARMOUR,
        WEAPON,
        OFFHAND,
        BELT,
        RING,
        ARTIFACT,
        BOOTS
    }

    class EquipmentItem(var id: Int, var name: String, var imageResourceId: Int, var price: Int, var type: ItemType, var statisticBoost: Int)
    companion object{
        private val equipment = mutableListOf<EquipmentItem>()

        init{
            equipment.add(EquipmentItem(0,"test0", R.drawable.rpg_logo_sm, 1, ItemType.HELMET, 1))
            equipment.add(EquipmentItem(1,"test1", R.drawable.rpg_logo_sm, 100, ItemType.ARMOUR, 10))
            equipment.add(EquipmentItem(2,"test2", R.drawable.rpg_logo_sm, 200, ItemType.WEAPON, 20))
            equipment.add(EquipmentItem(3,"test3", R.drawable.rpg_logo_sm, 300, ItemType.OFFHAND, 30))
            equipment.add(EquipmentItem(4,"test4", R.drawable.rpg_logo_sm, 400, ItemType.BELT, 40))
            equipment.add(EquipmentItem(5,"test5", R.drawable.rpg_logo_sm, 500, ItemType.RING, 50))
            equipment.add(EquipmentItem(6,"test6", R.drawable.rpg_logo_sm, 600, ItemType.ARTIFACT, 60))
            equipment.add(EquipmentItem(7,"test7", R.drawable.rpg_logo_sm, 700, ItemType.BOOTS, 70))
        }

        fun getItems(): List<EquipmentItem>
        {
            return equipment;
        }

    }


    class CustomRecyclerAdapter(private val captions: Array<String>, private val imageIds: IntArray) :
        RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>() {


        private var listener: Listener? = null

        interface Listener {
            fun onClick(position: Int)
        }


        fun setListener(listener: Listener?) {
            this.listener = listener
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
            val textView = cardView.findViewById<View>(R.id.info_text) as TextView
            textView.text = captions[position]

            cardView.setBackgroundColor(ContextCompat.getColor(cardView.context,R.color.button_color))

            cardView.setOnClickListener(View.OnClickListener(){
                listener?.onClick(position)
            })
        }

        override fun getItemCount(): Int {
            return captions.size
        }
    }

}