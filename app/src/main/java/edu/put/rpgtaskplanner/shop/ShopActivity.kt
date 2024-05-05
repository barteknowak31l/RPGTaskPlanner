package edu.put.rpgtaskplanner.shop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import edu.put.rpgtaskplanner.MainActivity
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.CharacterActivity
import edu.put.rpgtaskplanner.character.equipment.EquipmentFragment
import edu.put.rpgtaskplanner.character.equipment.ItemDetailsFragment
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.shop.ui.theme.RPGTaskPlannerTheme
import edu.put.rpgtaskplanner.task_list.TaskListActivity
import edu.put.rpgtaskplanner.utility.ShopSupplier

class ShopActivity : AppCompatActivity(), ShopFragment.ShopItemClickListener, ShopSupplier.OnDeleteItemListener {

    var shopItemList: List<EquipmentFragment.EquipmentItem> = listOf()

    override fun onDestroy() {
        super.onDestroy()
        ShopSupplier.listeners -= this
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ShopSupplier.listeners += this

        setContentView(R.layout.activity_shop)
        shopItemList = EquipmentFragment.getItems()
        onShopItemClick(0)



        // navigation
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
            drawer.closeDrawer(GravityCompat.START)
            when (menuItem.itemId)
            {
                R.id.menu_task_list ->
                {
                    val intent = Intent(this, TaskListActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
                R.id.menu_character ->
                {
                    val intent = Intent(this, CharacterActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
                R.id.menu_shop ->
                {
                    true
                }
                R.id.menu_logout ->
                {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }


        }

    override fun onShopItemClick(position: Int) {

        val itemList = ShopFragment.shopItemList
        if(itemList.isNotEmpty())
        {
            selectedItem = itemList[position]

            val fragment = TransactionDetailsFragment()

            val bundle = Bundle()
            bundle.putString("itemName",selectedItem!!.item_name)
            bundle.putDouble("price", selectedItem!!.price)
            bundle.putInt("itemType",selectedItem!!.type)
            bundle.putDouble("itemStat", selectedItem!!.base_bonus)

            //TODO change it to true character bonus when equipping items will be implemented
            bundle.putDouble("currentStat", 50.0)

            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.transactionDetailsFragment, fragment)
                .commit()
        }


    }
    companion object
    {
        var selectedItem: Item? = null
    }

    override fun onDeleteItem(item: Item) {
        selectedItem = null
    }

}

