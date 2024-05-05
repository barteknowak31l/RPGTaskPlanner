package edu.put.rpgtaskplanner.task_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.put.rpgtaskplanner.MainActivity
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.CharacterActivity
import edu.put.rpgtaskplanner.databinding.ActivityTaskDetailBinding
import edu.put.rpgtaskplanner.model.TaskStatus
import edu.put.rpgtaskplanner.repository.CharacterRepository
import edu.put.rpgtaskplanner.repository.TaskRepository
import edu.put.rpgtaskplanner.repository.UserRepository
import edu.put.rpgtaskplanner.shop.ShopActivity
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.Constants
import edu.put.rpgtaskplanner.utility.TaskManager
import edu.put.rpgtaskplanner.utility.UserManager
import java.util.Date

class TaskDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskDetailBinding
    private lateinit var endButton: Button

    val db = Firebase.firestore
    val userRepository = UserRepository(db)
    val characterRepository = CharacterRepository(db)
    val taskRepository = TaskRepository(db)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
                    startActivity(intent)
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

        endButton = findViewById<Button>(R.id.buttonCreate)
        endButton.setOnClickListener {
            onEndTaskOnClick()
        }
    }

    fun onEndTaskOnClick()
    {
        val task = TaskManager.getCurrentTask()
        val user = UserManager.getCurrentUser()
        val character = CharacterManager.getCurrentCharacter()
        val currentDate = Date()

        if(task != null && user != null && character != null)
        {
           if(task.estimated_end_date < currentDate)
           {

               when(task.difficulty)
               {
                   0 -> {
                       user.easy_task_done += 1
                   }
                   1 -> {
                       user.medium_task_done += 1
                   }
                   2 -> {
                       user.hard_task_done += 1
                   }
               }

               character.current_gold += task.gold_reward
               character.current_experience += task.gold_reward
               if(character.current_experience > character.level * character.level * Constants.NEXT_LEVEL_EXP_REQUIRED_MULT)
               {
                   character.level += 1
                   if(character.level > Constants.MAXIMUM_LEVEL) character.level = Constants.MAXIMUM_LEVEL
               }

               task.status = TaskStatus.DONE.id

               val userUpdateMap = mapOf(
                   UserRepository.UserFields.easy_task_done to user.easy_task_done,
                   UserRepository.UserFields.medium_task_done to user.medium_task_done,
                   UserRepository.UserFields.hard_task_done to user.hard_task_done,
               )
               userRepository.updateUser(user.email,userUpdateMap) {success ->
                   if(success){

                   } else {

                   }
               }

               val characterUpdateMap = mapOf(
                   CharacterRepository.CharacterFields.current_gold to character.current_gold,
                   CharacterRepository.CharacterFields.current_experience to character.current_experience,
                   CharacterRepository.CharacterFields.level to character.level
               )
               characterRepository.updateCharacter(user.character_id, characterUpdateMap) { success ->
                   if (success) {

                   } else {

                   }
               }

               val taskUpdateMap = mapOf(
                   TaskRepository.TaskFields.status to task.status
               )
               taskRepository.updateTaskByCharacterId(task.task_name, user.character_id, taskUpdateMap ) {success ->
                   if(success) {

                   } else {

                   }
               }

               Toast.makeText(this, getText(R.string.toast_task_done),Toast.LENGTH_SHORT).show()
               val intent = Intent(this, TaskListActivity::class.java)
               startActivity(intent)

           }
           else
           {
               Toast.makeText(this, getText(R.string.toast_cant_finish_task),Toast.LENGTH_SHORT).show()
           }
        }

    }

}

