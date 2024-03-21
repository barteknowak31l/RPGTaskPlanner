package edu.put.rpgtaskplanner

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.put.rpgtaskplanner.databinding.FragmentSignInFormBinding
import edu.put.rpgtaskplanner.databinding.FragmentSignUpFormBinding
import java.lang.RuntimeException


class SignUpFormFragment : Fragment() {

    private lateinit var binding: FragmentSignUpFormBinding

    interface Listener
    {
        fun signUpClicked(email: String, password: String, confirmPassword: String)
        fun signInClicked()
    }

    private var listener : SignUpFormFragment.Listener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignUpFormFragment.Listener)
        {
            listener = context
        }
        else{
            throw RuntimeException("$context must implement Listener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpFormBinding.inflate(inflater,container, false)
        val view = binding.root

        binding.buttonSignUp.setOnClickListener{
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()

            listener?.signUpClicked(email, password, confirmPassword)

        }

        binding.textViewSignIn.setOnClickListener{
            listener?.signInClicked()
        }


        return view
    }
}