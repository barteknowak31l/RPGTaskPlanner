package edu.put.rpgtaskplanner

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.put.rpgtaskplanner.databinding.FragmentSignInFormBinding
import java.lang.RuntimeException


class SignInFormFragment : Fragment() {

    private lateinit var binding : FragmentSignInFormBinding


    interface Listener
    {
        fun signInClicked(email: String, password: String)
        fun signInGoogleClicked()
        fun signUpClicked()
    }

    private var listener : Listener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener)
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
        // Inflate the layout for this fragment
        binding = FragmentSignInFormBinding.inflate(inflater,container, false)
        val view = binding.root


        binding.buttonSignInGoogle.setOnClickListener{
            listener?.signInGoogleClicked()
        }

        binding.buttonSignIn.setOnClickListener{

            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            listener?.signInClicked(email, password)
        }

        binding.textViewSignUp.setOnClickListener{
            listener?.signUpClicked()
        }

        return view
    }

}