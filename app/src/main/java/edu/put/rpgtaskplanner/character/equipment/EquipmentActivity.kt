package edu.put.rpgtaskplanner.character.equipment

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.CharacterInventoryFragment
import edu.put.rpgtaskplanner.character.equipment.ui.theme.RPGTaskPlannerTheme

class EquipmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipment)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)

        var type = intent.getStringExtra(CharacterInventoryFragment.INTENT_DATA.EQUIPMENT_TYPE.toString())

        type?.let {
            titleTextView.text = getString(R.string.header_activity_equipment) + " (" + it.toString() +")"
        }

    }
}

