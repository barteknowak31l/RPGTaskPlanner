package edu.put.rpgtaskplanner.character.character_statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.model.StatisticTypes
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.Constants
import edu.put.rpgtaskplanner.utility.UserManager

class CharacterStatisticsDisplayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_character_statistics_display, container, false)

        val statisticLeft1Fragment = StatisticListItemFragment()
        var bundle = Bundle()
        bundle.putInt("statisticType", StatisticTypes.MAX_HEALTH.id)
        statisticLeft1Fragment.arguments = bundle

        childFragmentManager.beginTransaction()
            .replace(R.id.statisticLeft1, statisticLeft1Fragment)
            .commit()


        val statisticLeft2Fragment = StatisticListItemFragment()
        bundle = Bundle()
        bundle.putInt("statisticType", StatisticTypes.CURRENT_HEALTH.id)
        statisticLeft2Fragment.arguments = bundle

        childFragmentManager.beginTransaction()
            .replace(R.id.statisticLeft2, statisticLeft2Fragment)
            .commit()

        val statisticLeft3Fragment = StatisticListItemFragment()
        bundle = Bundle()
        bundle.putInt("statisticType", StatisticTypes.HEALTH_REGEN.id)
        statisticLeft3Fragment.arguments = bundle

        childFragmentManager.beginTransaction()
            .replace(R.id.statisticLeft3, statisticLeft3Fragment)
            .commit()


        val statisticLeft4Fragment = StatisticListItemFragment()
        bundle = Bundle()
        bundle.putInt("statisticType", StatisticTypes.COOLDOWN_REDUCTION.id)
        statisticLeft4Fragment.arguments = bundle

        childFragmentManager.beginTransaction()
            .replace(R.id.statisticLeft4, statisticLeft4Fragment)
            .commit()

        val statisticLeft5Fragment = StatisticListItemFragment()
        bundle = Bundle()
        bundle.putInt("statisticType", StatisticTypes.EXP_MULTIPLIER.id)
        statisticLeft5Fragment.arguments = bundle

        childFragmentManager.beginTransaction()
            .replace(R.id.statisticLeft5, statisticLeft5Fragment)
            .commit()


        val statisticRight1Fragment = StatisticListItemFragment()
        bundle = Bundle()
        bundle.putInt("statisticType", StatisticTypes.MAX_ENERGY.id)
        statisticRight1Fragment.arguments = bundle

        childFragmentManager.beginTransaction()
            .replace(R.id.statisticRight1, statisticRight1Fragment)
            .commit()

        val statisticRight2Fragment = StatisticListItemFragment()
        bundle = Bundle()
        bundle.putInt("statisticType", StatisticTypes.CURRENT_ENERGY.id)
        statisticRight2Fragment.arguments = bundle

        childFragmentManager.beginTransaction()
            .replace(R.id.statisticRight2, statisticRight2Fragment)
            .commit()

        val statisticRight3Fragment = StatisticListItemFragment()
        bundle = Bundle()
        bundle.putInt("statisticType", StatisticTypes.ENERGY_REGEN.id)
        statisticRight3Fragment.arguments = bundle

        childFragmentManager.beginTransaction()
            .replace(R.id.statisticRight3, statisticRight3Fragment)
            .commit()

        val statisticRight4Fragment = StatisticListItemFragment()
        bundle = Bundle()
        bundle.putInt("statisticType", StatisticTypes.CURRENT_EXPERIENCE.id)
        statisticRight4Fragment.arguments = bundle

        childFragmentManager.beginTransaction()
            .replace(R.id.statisticRight4, statisticRight4Fragment)
            .commit()

        val statisticRight5Fragment = StatisticListItemFragment()
        bundle = Bundle()
        bundle.putInt("statisticType", StatisticTypes.GOLD_MULTIPLIER.id)
        statisticRight5Fragment.arguments = bundle

        childFragmentManager.beginTransaction()
            .replace(R.id.statisticRight5, statisticRight5Fragment)
            .commit()


        val character = CharacterManager.getCurrentCharacter()

        val currentLevelTextView = rootView.findViewById<TextView>(R.id.levelTextView)
        currentLevelTextView.text = getString(R.string.current_level_activity_character, character?.level.toString())

        val expTextView = rootView.findViewById<TextView>(R.id.expTextView)
        expTextView.text = getString(R.string.current_exp_activity_character, character?.current_experience.toString())

        val nextLevelExp = rootView.findViewById<TextView>(R.id.nextLevelTextView)
        var nextLevel = 0.0f
        if(character != null)
        {
            nextLevel = (character.level).toFloat() * Constants.NEXT_LEVEL_EXP_REQUIRED_MULT
        }
        nextLevelExp.text = getString(R.string.next_level_exp_activity_character, (nextLevel).toString())

        val currentGoldExp = rootView.findViewById<TextView>(R.id.currentGoldTextView)
        if(character != null)
        {
            currentGoldExp.text = getString(R.string.current_gold_activity_character, character.current_gold.toString())

        }

        // display more details only if character is availble (to prevent displaying it in character creation)
        val user = UserManager.getCurrentUser()
        if(user != null && user.character_id != "")
        {
            val trophiesFragment = TrophiesFragment()
            childFragmentManager.beginTransaction()
                .replace(R.id.trophiesFragment, trophiesFragment)
                .commit()
        }
        else {
            currentLevelTextView.height = 0
            expTextView.height = 0
            nextLevelExp.height = 0
            currentGoldExp.height = 0
            val trophiesFragment = childFragmentManager.findFragmentById(R.id.trophiesFragment)
            if(trophiesFragment != null)
                childFragmentManager.beginTransaction().remove(trophiesFragment).commit()
        }
        return rootView
    }
}