package edu.put.rpgtaskplanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import edu.put.rpgtaskplanner.character.CharacterActivity
import edu.put.rpgtaskplanner.databinding.ActivityMainBinding
import edu.put.rpgtaskplanner.shop.ShopActivity
import edu.put.rpgtaskplanner.task_list.TaskListActivity
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.EquipmentManager
import edu.put.rpgtaskplanner.utility.UserManager
import java.net.URLEncoder


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val character = CharacterManager.getCurrentCharacter()
        if(character != null)
        {
            binding.textViewCharacterName.text = getString(R.string.menu_character_hello,character.character_name)
        }


        binding.buttonSignOut.setOnClickListener{
            firebaseAuth.signOut()
            UserManager.logout()
            CharacterManager.setToNull()
            EquipmentManager.setToNull()
            EquipmentManager.clearEquippedItems()
            startActivity(Intent(this, SignInActivity::class.java))
            Toast.makeText(this, "Logout succesful",Toast.LENGTH_SHORT).show()
            finish()
        }


        binding.menuTaskList.setOnClickListener{
            val intent = Intent(this, TaskListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)

        }
        binding.menuCharacter.setOnClickListener{
            val intent = Intent(this, CharacterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        binding.menuShop.setOnClickListener{
            val intent = Intent(this, ShopActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.menuShare.setOnClickListener{

            val user = UserManager.getCurrentUser()
            val character = CharacterManager.getCurrentCharacter()
            if(user != null && character != null)
            {
                val post = getString(R.string.facebook_post_content,
                    character.character_name,
                    character.level.toString(),
                    user.easy_task_done.toString(),
                    user.medium_task_done.toString(),
                    user.hard_task_done.toString())

                val hashtag = getString(R.string.app_hashtag)
                val encodedPost = URLEncoder.encode(post, "UTF-8")
                val encodedHashtag = URLEncoder.encode(hashtag, "UTF-8")
                val tweetUrl = "https://twitter.com/intent/tweet?text=" + encodedPost + "&hashtags=" + encodedHashtag
                val uri = Uri.parse(tweetUrl)
                startActivity(Intent(Intent.ACTION_VIEW, uri))


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
                    true
                }

                else -> false
            }
        }

    }
}

