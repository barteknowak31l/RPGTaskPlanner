package edu.put.rpgtaskplanner.character.character_creator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.put.rpgtaskplanner.MainActivity
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.model.Character
import edu.put.rpgtaskplanner.model.CharacterClass
import edu.put.rpgtaskplanner.repository.CharacterRepository
import edu.put.rpgtaskplanner.repository.UserRepository
import edu.put.rpgtaskplanner.utility.CharacterBuilder
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.UserManager

class CharacterCreatorFragment : Fragment() {

    interface CharacterClassChangeListener{
        fun onClassChange()
    }

    private var characterClassListener: CharacterClassChangeListener? = null


    private var currentClass: CharacterClass = CharacterClass.WARRIOR
    private lateinit var characterDisplay: ImageView
    private lateinit var classNameTextView: TextView
    private lateinit var characterNameEditText: EditText

    private var characterName: String = ""

    private val db = Firebase.firestore
    private val characterRepository = CharacterRepository(db)
    private val userRepository = UserRepository(db)

    private lateinit var characterBaseMage: Character
    private lateinit var characterBaseRogue: Character
    private lateinit var characterBaseWarrior: Character


    override fun onAttach(context: Context) {
        super.onAttach(context)
        createBaseCharacters()
        val character = getBaseCharacter()
        CharacterManager.setCurrentCharacter(character)

        // Sprawdź, czy aktywność implementuje interfejs
        if (context is CharacterClassChangeListener) {
            characterClassListener = context
            characterClassListener!!.onClassChange()
        } else {
            throw RuntimeException("$context must implement CharacterClassChangeListener")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_character_creator, container, false)


        characterDisplay = view.findViewById(R.id.characterDisplay)
        setCharacterDisplay()



        classNameTextView = view.findViewById(R.id.textViewClass)
        setClassNameTextView()

        characterNameEditText = view.findViewById(R.id.editTextCharacterName)


        val buttonPrevClass = view.findViewById<Button>(R.id.buttonPrevClass)
        buttonPrevClass.setOnClickListener {
            previousOnClick()
            setCharacterDisplay()
            setClassNameTextView()
            CharacterManager.setCurrentCharacter(getBaseCharacter())
            characterClassListener!!.onClassChange()

        }

        val buttonNextClass = view.findViewById<Button>(R.id.buttonNextClass)
        buttonNextClass.setOnClickListener {
            nextClassOnClick()
            setCharacterDisplay()
            setClassNameTextView()
            CharacterManager.setCurrentCharacter(getBaseCharacter())
            characterClassListener!!.onClassChange()
        }

        return view
    }

    fun nextClassOnClick()
    {
        var index = currentClass.id
        index = if (index == CharacterClass.entries.toTypedArray().maxOf{it.id})  0 else index + 1
        currentClass = CharacterClass.entries.getOrNull(index)!!
    }

    fun previousOnClick()
    {
        var index = currentClass.id
        index = if (index == 0)  CharacterClass.entries.toTypedArray().maxOf{it.id} else index - 1
        currentClass = CharacterClass.entries.getOrNull(index)!!
    }


    fun setClassNameTextView()
    {
        when (currentClass)
        {
            CharacterClass.WARRIOR -> {
                classNameTextView.text = getText(R.string.class_warrior)
            }

            CharacterClass.ROGUE -> {
                classNameTextView.text = getText(R.string.class_rogue)
            }

            CharacterClass.MAGE -> {
                classNameTextView.text = getText(R.string.class_mage)
            }
        }
    }

    fun createBaseCharacters(){

        var builder = CharacterBuilder()
        characterBaseWarrior = builder.buildBaseWarrior()
        builder = CharacterBuilder()
        characterBaseRogue = builder.buildBaseRogue()
        builder = CharacterBuilder()
        characterBaseMage = builder.buildBaseMage()
    }

    fun getBaseCharacter(): Character
    {
         return when (currentClass) {
            CharacterClass.WARRIOR -> characterBaseWarrior
            CharacterClass.ROGUE -> characterBaseRogue
            CharacterClass.MAGE -> characterBaseMage
        }
    }

    public fun submitCharacterOnClick()
    {
        characterName = characterNameEditText.text.toString()

        if (characterName.trim() == "")
        {
            Toast.makeText(context, "Nazwa postaci nie może być pusta!", Toast.LENGTH_SHORT).show()
            return
        }

        var character = getBaseCharacter()

        character.character_name = characterName

        characterRepository.saveCharacter(character) { success, id ->
            if (success) {

                val currentUser = UserManager.getCurrentUser()
                val updates = mapOf(
                    UserRepository.UserFields.character_id to id.toString()
                )
                if (currentUser != null) {
                    userRepository.updateUser(currentUser.email, updates) { success ->
                        if(success) {
                            CharacterManager.setCurrentCharacter(character)
                            val intent = Intent(context, MainActivity::class.java)
                            intent.putExtra("email", currentUser.email)
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(context,getText(R.string.database_save_error),Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            } else {

                Toast.makeText(context,getText(R.string.database_save_error),Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun setCharacterDisplay()
    {
        val character = CharacterManager.getCurrentCharacter()
        if(character != null)
        {
                Character.setCharacterDisplay(currentClass,character.character_name, characterDisplay,null)
        }
    }



}