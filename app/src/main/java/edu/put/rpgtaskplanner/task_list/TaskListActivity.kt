package edu.put.rpgtaskplanner.task_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.put.rpgtaskplanner.MainActivity
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.CharacterActivity
import edu.put.rpgtaskplanner.databinding.ActivityTaskListBinding
import edu.put.rpgtaskplanner.repository.CharacterRepository
import edu.put.rpgtaskplanner.shop.ShopActivity
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.CircleFillView
import edu.put.rpgtaskplanner.utility.UserManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class TaskListActivity : AppCompatActivity(), TaskListFragment.Listener {

    private lateinit var binding: ActivityTaskListBinding

    private val db = Firebase.firestore
    private val characterRepository = CharacterRepository(db)

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


        refreshResources()

        val character = CharacterManager.getCurrentCharacter()
        if(character != null)
        {
            healthOrb.value = ((character.current_health / character.max_health) * 100.0).toInt()
            resourceOrb.value = ((character.current_energy / character.max_energy) * 100.0).toInt()

            healthOrb.setOnClickListener{
                val current = character.current_health.toString()
                val max = character.max_health.toString()
                val refreshVal = character.health_regen.toString()
                val calendar = Calendar.getInstance()
                calendar.time = character.last_resource_refresh_date
                calendar.add(Calendar.MINUTE, character.resource_refresh_cooldown_minutes)
                val nextRefresh = calendar.time
                val sdf = SimpleDateFormat("dd/M hh:mm:ss")
                val nextRefreshString = sdf.format(nextRefresh)

                Toast.makeText(this,getString(R.string.toast_orb_refresh,current,max,refreshVal,nextRefreshString),Toast.LENGTH_SHORT).show()
            }

            resourceOrb.setOnClickListener{
                val current = character.current_energy.toString()
                val max = character.max_energy.toString()
                val refreshVal = character.energy_regen.toString()
                val calendar = Calendar.getInstance()
                calendar.time = character.last_resource_refresh_date
                calendar.add(Calendar.MINUTE, character.resource_refresh_cooldown_minutes)
                val nextRefresh = calendar.time
                val sdf = SimpleDateFormat("dd/M hh:mm:ss")
                val nextRefreshString = sdf.format(nextRefresh)

                Toast.makeText(this,getString(R.string.toast_orb_refresh,current,max,refreshVal,nextRefreshString),Toast.LENGTH_SHORT).show()
            }

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
                R.id.menu_main ->
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


    private fun refreshResources()
    {
        // sprawdz ostatnia date refresha
        // sprawdz refresh cooldown
        // tak dlugo az last refresh += ref_cd < Date
        // zwieksz zawartosc orbow o refresh value + bonus ze statow
        // jesli oba max, przerwij, ustaw last refresh na Date

        val character = CharacterManager.getCurrentCharacter()
        if(character != null)
        {
            val currentDate = Date()
            var lastRefreshDate = character.last_resource_refresh_date
            val cooldown = character.resource_refresh_cooldown_minutes
            val calendar = Calendar.getInstance()

            while(lastRefreshDate < currentDate)
            {
                // add resources
                if(character.current_energy < character.max_energy)
                {
                    character.current_energy += character.energy_regen
                    Log.d("ORB", "ENERGY REFRESFED FOR: " + character.energy_regen)
                    if(character.current_energy > character.max_energy)
                    {
                        character.current_energy = character.max_energy
                    }

                }
                if(character.current_health < character.max_health)
                {
                    character.current_health += character.health_regen
                    Log.d("ORB", "HEALTH REFRESFED FOR: " + character.health_regen)

                    if(character.current_health > character.max_health)
                    {
                        character.current_health = character.max_health
                    }

                }

                // check cooldown
                calendar.time = lastRefreshDate
                calendar.add(Calendar.MINUTE, cooldown)
                lastRefreshDate = calendar.time

                if(character.current_energy >= character.max_energy && character.current_health >= character.max_health)
                {
                    lastRefreshDate = Date()
                    break
                }

            }

            character.last_resource_refresh_date = lastRefreshDate

            val user = UserManager.getCurrentUser()
            if(user != null)
            {
                val updates = mapOf(CharacterRepository.CharacterFields.current_energy to character.current_energy,
                    CharacterRepository.CharacterFields.current_health to character.current_health,
                    CharacterRepository.CharacterFields.last_resource_refresh_date to character.last_resource_refresh_date)

                characterRepository.updateCharacter(user.character_id, updates) {}
            }

            healthOrb.value = ((character.current_health / character.max_health) * 100.0).toInt()
            resourceOrb.value = ((character.current_energy / character.max_energy) * 100.0).toInt()

        }

    }


}
