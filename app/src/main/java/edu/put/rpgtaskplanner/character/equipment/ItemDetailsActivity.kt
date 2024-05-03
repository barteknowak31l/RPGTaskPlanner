package edu.put.rpgtaskplanner.character.equipment

import android.os.Bundle
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
import edu.put.rpgtaskplanner.character.equipment.ui.theme.RPGTaskPlannerTheme

class ItemDetailsActivity : AppCompatActivity() {

    private var itemName: String = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        intent.getStringExtra("name").let { itemName = it.toString() };

        val fragment = ItemDetailsFragment()
        val bundle = Bundle()
        bundle.putString("name", itemName)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.itemDetailsFragment, fragment)
            .commit()

    }
}

