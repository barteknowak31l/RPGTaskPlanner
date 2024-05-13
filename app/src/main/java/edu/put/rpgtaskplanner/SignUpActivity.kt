package edu.put.rpgtaskplanner

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import edu.put.rpgtaskplanner.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(), SignUpFormFragment.Listener  {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

    }

    override fun signUpClicked(email: String, password: String, confirmPassword: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (password.equals(confirmPassword)) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, getString(R.string.toast_account_created), Toast.LENGTH_SHORT)
                                .show()
                            finish()

                        } else {
                            Toast.makeText(this, it.exception?.message.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, getString(R.string.toast_password_not_matching), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.toast_empty_fields), Toast.LENGTH_SHORT).show()

        }
    }

    override fun signInClicked() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}
