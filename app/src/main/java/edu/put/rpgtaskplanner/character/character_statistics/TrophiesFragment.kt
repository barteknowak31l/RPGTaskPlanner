package edu.put.rpgtaskplanner.character.character_statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.utility.UserManager


class TrophiesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_trophies, container, false)

        val easyTextView = rootView.findViewById<TextView>(R.id.textView1)
        val mediumTextView = rootView.findViewById<TextView>(R.id.textView2)
        val hardTextView = rootView.findViewById<TextView>(R.id.textView3)

        val user = UserManager.getCurrentUser()
        if(user != null)
        {
            easyTextView.text = user.easy_task_done.toString()
            mediumTextView.text = user.medium_task_done.toString()
            hardTextView.text = user.hard_task_done.toString()
        }
        return rootView
    }

}