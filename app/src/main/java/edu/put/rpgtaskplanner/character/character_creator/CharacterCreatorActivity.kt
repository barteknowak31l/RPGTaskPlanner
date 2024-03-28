package edu.put.rpgtaskplanner.character.character_creator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import edu.put.rpgtaskplanner.databinding.ActivityCharacterCreatorBinding

class CharacterCreatorActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCharacterCreatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCharacterCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root )

    }
}
