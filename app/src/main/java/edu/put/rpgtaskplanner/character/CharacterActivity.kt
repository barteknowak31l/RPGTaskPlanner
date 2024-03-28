package edu.put.rpgtaskplanner.character

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import edu.put.rpgtaskplanner.databinding.ActivityCharacterBinding

class CharacterActivity : FragmentActivity() {

    private lateinit var binding: ActivityCharacterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
