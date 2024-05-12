package edu.put.rpgtaskplanner.character.equipment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import edu.put.rpgtaskplanner.MainActivity
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.CharacterActivity
import edu.put.rpgtaskplanner.character.CharacterInventoryFragment
import edu.put.rpgtaskplanner.model.ItemType
import edu.put.rpgtaskplanner.shop.ShopActivity
import edu.put.rpgtaskplanner.task_list.TaskListActivity

class EquipmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipment)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)

        var type = intent.getIntExtra(CharacterInventoryFragment.INTENT_DATA.EQUIPMENT_TYPE.toString(), 0)

        type?.let {
            titleTextView.text = getString(R.string.header_activity_equipment) + " (" + ItemType.getItemTypeNameById(it) +")"
        }

        val equipmentFragment = EquipmentFragment()
        val bundle = Bundle()
        bundle.putInt("type", type)
        equipmentFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.equipmentFragment, equipmentFragment)
            .commit()

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
                    val intent = Intent(this, ShopActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
                R.id.menu_main ->
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
}

