package edu.put.rpgtaskplanner

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import edu.put.rpgtaskplanner.character.CharacterActivity
import edu.put.rpgtaskplanner.databinding.ActivityMainBinding
import edu.put.rpgtaskplanner.databinding.ActivitySignInBinding
import edu.put.rpgtaskplanner.shop.ShopActivity
import edu.put.rpgtaskplanner.task_list.TaskListActivity
import edu.put.rpgtaskplanner.ui.theme.RPGTaskPlannerTheme
import edu.put.rpgtaskplanner.utility.CharacterManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("name")

        val character = CharacterManager.getCurrentCharacter()
        if(character != null)
        {
            binding.textViewCharacterName.text = getString(R.string.menu_character_hello,character.character_name)
        }


        binding.buttonSignOut.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            Toast.makeText(this, "Logout succesful",Toast.LENGTH_SHORT).show()
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

