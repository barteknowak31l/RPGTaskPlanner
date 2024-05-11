package edu.put.rpgtaskplanner.character

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import edu.put.rpgtaskplanner.MainActivity
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.character_statistics.CharacterStatisticsDisplayFragment
import edu.put.rpgtaskplanner.databinding.ActivityCharacterBinding
import edu.put.rpgtaskplanner.shop.ShopActivity
import edu.put.rpgtaskplanner.task_list.TaskListActivity

class CharacterActivity : FragmentActivity() {

    private lateinit var binding: ActivityCharacterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // viewpager and tablayout
        val pagerAdapter = CustomPagerAdapter(supportFragmentManager,applicationContext)
        val pager = findViewById<View>(R.id.pager) as ViewPager
        pager.setAdapter(pagerAdapter)
        val tablayout: TabLayout = findViewById(R.id.tabLayout)
        tablayout.setupWithViewPager(pager);

        // navigation
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
            drawer.closeDrawer(GravityCompat.START)
            when (menuItem.itemId)
            {
                R.id.menu_task_list ->
                {
                    val intent = Intent(this, TaskListActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
                R.id.menu_character ->
                {
                    true
                }
                R.id.menu_shop ->
                {
                    val intent = Intent(this, ShopActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
                R.id.menu_main ->
                {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }


    }

    private class CustomPagerAdapter(fm: FragmentManager?, private val context: Context):
        FragmentPagerAdapter(fm!!) {
        override fun getCount(): Int
        {
            return 2;
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return CharacterInventoryFragment()
                1 -> return CharacterStatisticsDisplayFragment()
            }
            return CharacterInventoryFragment()
        }

        override fun getPageTitle(position: Int): CharSequence {
            val title = when (position) {
                0 -> context.getString(R.string.tab1_activity_character)
                1 -> context.getString(R.string.tab2_activity_character)
                else -> ""
            }
            return title
        }

    }

}
