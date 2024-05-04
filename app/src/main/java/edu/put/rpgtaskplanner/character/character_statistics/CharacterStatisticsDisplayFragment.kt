package edu.put.rpgtaskplanner.character.character_statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.model.StatisticTypes
import edu.put.rpgtaskplanner.utility.CharacterManager

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
        val nextLevel = character?.level!!.toFloat() * 1000
        nextLevelExp.text = getString(R.string.next_level_exp_activity_character, (nextLevel).toString())

        return rootView
    }


}