package edu.put.rpgtaskplanner.shop

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
import edu.put.rpgtaskplanner.character.equipment.EquipmentFragment
import edu.put.rpgtaskplanner.character.equipment.ItemDetailsFragment
import edu.put.rpgtaskplanner.shop.ui.theme.RPGTaskPlannerTheme

class ShopActivity : AppCompatActivity(), ShopFragment.ShopItemClickListener {

    var shopItemList: List<EquipmentFragment.EquipmentItem> = listOf()
    private var selectedItem: EquipmentFragment.EquipmentItem = EquipmentFragment.EquipmentItem(-1,"empty",-1, -1, EquipmentFragment.ItemType.BOOTS, -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        shopItemList = EquipmentFragment.getItems()
        onShopItemClick(0)

        }

    override fun onShopItemClick(position: Int) {
        selectedItem = shopItemList[position]

        val fragment = TransactionDetailsFragment()

        val bundle = Bundle()
        bundle.putString("itemName",selectedItem.name.toString())
        bundle.putInt("price", selectedItem.price)
        bundle.putString("itemType",selectedItem.type.toString())
        bundle.putInt("itemStat", selectedItem.statisticBoost)
        bundle.putInt("currentStat", 50)

        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.transactionDetailsFragment, fragment)
            .commit()


    }
}

