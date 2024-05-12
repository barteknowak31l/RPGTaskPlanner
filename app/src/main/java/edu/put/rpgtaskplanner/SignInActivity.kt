package edu.put.rpgtaskplanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.put.rpgtaskplanner.character.character_creator.CharacterCreatorActivity
import edu.put.rpgtaskplanner.databinding.ActivitySignInBinding
import edu.put.rpgtaskplanner.model.Item
import edu.put.rpgtaskplanner.model.User
import edu.put.rpgtaskplanner.repository.CharacterRepository
import edu.put.rpgtaskplanner.repository.UserRepository
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.EquipmentHandler
import edu.put.rpgtaskplanner.utility.UserManager
import java.util.Date

class SignInActivity : AppCompatActivity(), SignInFormFragment.Listener, EquipmentHandler.EquipmentHandlerCallback {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    val db = Firebase.firestore
    val userRepository = UserRepository(db)
    val characterRepository = CharacterRepository(db)

    private var equipmentHandler: EquipmentHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        equipmentHandler = EquipmentHandler(this, this, lifecycleScope )

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result ->
            if(result.resultCode == Activity.RESULT_OK)
            {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful)
        {
            val account : GoogleSignInAccount? = task.result
            if(account != null)
            {
                updateUI(account)
            }
        }
        else
        {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful)
            {
                onLoginSuccess(account.email.toString())
            }
            else
            {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onStart()
    {
        super.onStart();
        if(firebaseAuth.currentUser != null && UserManager.getCurrentUser() == null)
        {
            onLoginSuccess(firebaseAuth.currentUser!!.email.toString())
        }
    }

    override fun signInClicked(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onLoginSuccess(email)

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        } else {
            Toast.makeText(this, "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()

        }
    }

    override fun signInGoogleClicked() {
        googleSignInClient.signOut().addOnCompleteListener()
        {
            signInGoogle()
        }
    }

    override fun signUpClicked() {
        val intent = Intent(this, SignUpActivity::class.java)

        startActivity(intent)
    }


    fun onLoginSuccess(email: String)
    {

        // check if user already has had character created
        userRepository.getUserByEmail(email) { user ->
            if (user != null)
            {
                UserManager.setCurrentUser(user)


                if (user.character_id != "")
                {
                    characterRepository.getCharacter(user.character_id) { character ->
                        if (character != null) {
                            CharacterManager.setCurrentCharacter(character)
                            // fetch character items
                            equipmentHandler?.fetchEquippedItemsFromFirestore(user.character_id, this)
                        }
                    }

                    val intent : Intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }
                else
                {
                    val intent : Intent = Intent(this, CharacterCreatorActivity::class.java)
                    startActivity(intent)
                }
            } else
            {
                val newUser = User()
                newUser.email = email
                newUser.character_id = ""
                newUser.next_shop_refresh = Date()
                userRepository.saveUser(newUser) { success ->
                    if (success) {
                        val intent : Intent = Intent(this, CharacterCreatorActivity::class.java)
                        startActivity(intent)
                        UserManager.setCurrentUser(newUser)
                    } else {
                        Toast.makeText(this,getText(R.string.database_save_error),Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    override fun onItemEquipped(item: Item?) {
    }

    override fun onItemsFetchedFromFirestore(items: List<Item>?) {
    }


}

