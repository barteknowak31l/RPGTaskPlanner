package edu.put.rpgtaskplanner.character.character_statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import edu.put.rpgtaskplanner.R
import edu.put.rpgtaskplanner.model.CharacterClass
import edu.put.rpgtaskplanner.model.StatisticTypes
import edu.put.rpgtaskplanner.utility.CharacterManager
import edu.put.rpgtaskplanner.utility.UserManager

class StatisticListItemFragment : Fragment(){

    private var statisticType: StatisticTypes = StatisticTypes.COOLDOWN_REDUCTION
    private lateinit var statisticTextView: TextView
    private lateinit var statisticIcon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_statistic_list_item, container, false)
        statisticTextView = rootView.findViewById(R.id.tetViewStatisticName)
        statisticIcon = rootView.findViewById(R.id.imageViewStatisticIcon)

        arguments?.let {
            statisticType = StatisticTypes.entries.toTypedArray().getOrNull((it.getInt("statisticType")))!!
        }
        setupDisplay()




        return rootView
    }

    private fun setupDisplay()
    {
        val character = CharacterManager.getCurrentCharacter()
        if(character != null)
        {
            when(statisticType)
            {
                StatisticTypes.CURRENT_EXPERIENCE -> {
                    statisticTextView.text = getString(R.string.stats_experience,character.current_experience.toString())
                    statisticIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.exp))
                }

                StatisticTypes.COOLDOWN_REDUCTION -> {
                    statisticTextView.text = getString(R.string.stats_cooldown_reduction,character.cooldown_reduction.toString())
                    statisticIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.cooldown))
                }
                StatisticTypes.CURRENT_ENERGY ->
                {

                    statisticIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.energy))
                    when(character.character_class)
                    {
                        CharacterClass.WARRIOR.id -> {
                            statisticTextView.text = getString(R.string.stats_current_energy_warrior,character.current_energy.toString())
                        }
                        CharacterClass.ROGUE.id -> {
                            statisticTextView.text = getString(R.string.stats_current_energy_rogue,character.current_energy.toString())
                        }
                        CharacterClass.MAGE.id -> {
                            statisticTextView.text = getString(R.string.stats_current_energy_mage,character.current_energy.toString())
                        }
                    }
                }
                StatisticTypes.CURRENT_HEALTH ->
                {
                    statisticIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.current_heart))
                    statisticTextView.text = getString(R.string.stats_current_health,character.current_health.toString())

                }
                StatisticTypes.ENERGY_REGEN ->
                {
                    statisticIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.energy_regen))
                    when(character.character_class)
                    {
                        CharacterClass.WARRIOR.id -> {
                            statisticTextView.text = getString(R.string.stats_energy_regen_warrior,character.energy_regen.toString())
                        }
                        CharacterClass.ROGUE.id -> {
                            statisticTextView.text = getString(R.string.stats_energy_regen_rogue,character.energy_regen.toString())
                        }
                        CharacterClass.MAGE.id -> {
                            statisticTextView.text = getString(R.string.stats_energy_regen_mage,character.energy_regen.toString())
                        }
                    }
                }
                StatisticTypes.EXP_MULTIPLIER -> {
                    statisticIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.exp))
                    statisticTextView.text = getString(R.string.stats_exp_multiplier,character.gold_multiplier.toString())
                }
                StatisticTypes.GOLD_MULTIPLIER ->
                {
                    statisticIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.coin_icon))
                    statisticTextView.text = getString(R.string.stats_gold_multiplier,character.gold_multiplier.toString())
                }
                StatisticTypes.HEALTH_REGEN ->
                {
                    statisticIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.heart_regen))
                    statisticTextView.text = getString(R.string.stats_health_reneg,character.health_regen.toString())
                }
                StatisticTypes.LEVEL -> {
                    statisticTextView.text = getString(R.string.stats_level,character.level.toString())
                    statisticIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.exp))
                }
                StatisticTypes.MAX_ENERGY ->
                {
                    statisticIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.energy))
                    when(character.character_class)
                    {
                        CharacterClass.WARRIOR.id -> {
                            statisticTextView.text = getString(R.string.stats_max_energy_warrior,character.max_energy.toString())
                        }
                        CharacterClass.ROGUE.id -> {
                            statisticTextView.text = getString(R.string.stats_max_energy_rogue,character.max_energy.toString())
                        }
                        CharacterClass.MAGE.id -> {
                            statisticTextView.text = getString(R.string.stats_max_energy_mage,character.max_energy.toString())
                        }
                    }
                }
                StatisticTypes.MAX_HEALTH ->
                {
                    statisticIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.heart))
                    statisticTextView.text = getString(R.string.stats_max_health,character.max_health.toString())
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        setupDisplay()
    }


}