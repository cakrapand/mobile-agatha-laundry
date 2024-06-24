package com.example.agathalaundry.ui.history

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.agathalaundry.R
import com.example.agathalaundry.adapter.SectionPagerAdapter
import com.example.agathalaundry.databinding.ActivityHistoryBinding
import com.example.agathalaundry.ui.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class HistoryActivity : AppCompatActivity() {

    private var _activityHistoryBinding: ActivityHistoryBinding? = null
    private val binding get() = _activityHistoryBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityHistoryBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBarHistory.setNavigationOnClickListener  { finish() }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position ->
            when(position) {
                0 -> {
                    tab.text = resources.getString(R.string.history_active)
//                    tab.icon = ContextCompat.getDrawable(this@HistoryActivity, R.drawable.baseline_access_time_24)
                }
                1 -> {
                    tab.text = resources.getString(R.string.history_all)
//                    tab.icon = ContextCompat.getDrawable(this@HistoryActivity, R.drawable.baseline_assignment_24)
                }
            }
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityHistoryBinding = null
    }
}