package edu.put.rpgtaskplanner.character.character_creator

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.character_statistics.CharacterStatisticsDisplayFragment
import edu.put.rpgtaskplanner.databinding.ActivityCharacterCreatorBinding

class CharacterCreatorActivity : AppCompatActivity(), CharacterCreatorFragment.CharacterClassChangeListener {


    private lateinit var binding : ActivityCharacterCreatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCharacterCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root )

        val submitButton = findViewById<Button>(R.id.buttonCreate)
        submitButton.setOnClickListener {

            val fragment = supportFragmentManager.findFragmentById(R.id.characterCreatorFragment)
            if(fragment is CharacterCreatorFragment)
            {
                fragment.submitCharacterOnClick()
            }
        }




    }

    override fun onClassChange() {
        val fragment = CharacterStatisticsDisplayFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.characterStatisticsFragment, fragment)
            .commit()
    }
}
