package edu.put.rpgtaskplanner.character

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.character.character_statistics.CharacterStatisticsDisplayFragment
import edu.put.rpgtaskplanner.databinding.ActivityCharacterBinding

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
