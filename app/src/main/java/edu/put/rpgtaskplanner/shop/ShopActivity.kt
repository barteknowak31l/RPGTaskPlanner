package edu.put.rpgtaskplanner.shop

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import edu.put.rpgtaskplanner.MainActivity
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.CharacterActivity
import edu.put.rpgtaskplanner.model.Character
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.task_list.TaskListActivity
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.ShopSupplier

class ShopActivity : AppCompatActivity(), ShopFragment.ShopItemClickListener, ShopSupplier.OnDeleteItemListener {

    override fun onDestroy() {
        super.onDestroy()
        ShopSupplier.listeners -= this
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ShopSupplier.listeners += this

        setContentView(R.layout.activity_shop)
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
                    val root = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(root)

                    val intent = Intent(this, TaskListActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.menu_character ->
                {
                    val root = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(root)

                    val intent = Intent(this, CharacterActivity::class.java)
                    startActivity(intent)
                    finish()

                    true
                }
                R.id.menu_shop ->
                {
                    true
                }
                R.id.menu_main ->
                {
                    val root = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(root)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

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

            val character = CharacterManager.getCurrentCharacter()
            if(character != null)
            {
                val currentStat = Character.resolveStatOnItemType(selectedItem!!.type, character)
                bundle.putDouble("currentStat", currentStat)
            }

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

