package edu.put.rpgtaskplanner.task_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import edu.put.rpgtaskplanner.MainActivity
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.CharacterActivity
import edu.put.rpgtaskplanner.databinding.ActivityTaskListBinding
import edu.put.rpgtaskplanner.shop.ShopActivity
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.CircleFillView


class TaskListActivity : AppCompatActivity(), TaskListFragment.Listener {

    private lateinit var binding: ActivityTaskListBinding

    private lateinit var healthOrb: CircleFillView
    private lateinit var resourceOrb: CircleFillView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCreate.setOnClickListener{
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        healthOrb = binding.circleFillViewHealth
        resourceOrb = binding.circleFillViewResource

        val character = CharacterManager.getCurrentCharacter()
        if(character != null)
        {
            healthOrb.value = ((character.current_health / character.max_health) * 100.0).toInt()
            resourceOrb.value = ((character.current_energy / character.max_energy) * 100.0).toInt()

            when(character.character_class)
            {
                0->{
                    resourceOrb.fillColor = getColor(R.color.resource_orb_fill_warrior)
                    resourceOrb.strokeColor = getColor(R.color.resource_orb_stroke_warrior)
                }
                1->{
                    resourceOrb.fillColor = getColor(R.color.resource_orb_fill_rogue)
                    resourceOrb.strokeColor = getColor(R.color.resource_orb_stroke_rogue)
                }
                2->{
                    resourceOrb.fillColor = getColor(R.color.resource_orb_fill_mage)
                    resourceOrb.strokeColor = getColor(R.color.resource_orb_stroke_mage)
                }
            }
        }



        // navigation
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
            drawer.closeDrawer(GravityCompat.START)
            when (menuItem.itemId)
            {
                R.id.menu_task_list ->
                {
                    true
                }
                R.id.menu_character ->
                {
                    val intent = Intent(this, CharacterActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_shop ->
                {
                    val intent = Intent(this, ShopActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_logout ->
                {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

    }

    override fun itemClicked(id: Int) {
        val intent = Intent(this, TaskDetailActivity::class.java)
        startActivity(intent)
    }
}
