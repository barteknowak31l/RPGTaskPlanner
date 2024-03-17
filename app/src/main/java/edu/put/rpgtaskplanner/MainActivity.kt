package edu.put.rpgtaskplanner

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import edu.put.rpgtaskplanner.databinding.ActivityMainBinding
import edu.put.rpgtaskplanner.databinding.ActivitySignInBinding
import edu.put.rpgtaskplanner.ui.theme.RPGTaskPlannerTheme

class MainActivity : ComponentActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("name")

        binding.textView.text = "Hello " + displayName + "\n" + email

        binding.buttonSignOut.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
//            finish()
            Toast.makeText(this, "Logout succesful",Toast.LENGTH_SHORT).show()
        }

    }
}

