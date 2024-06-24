package com.example.agathalaundry.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.agathalaundry.R
import com.example.agathalaundry.databinding.ActivityMainBinding
import com.example.agathalaundry.ui.ViewModelFactory
import com.example.agathalaundry.ui.order.OrderActivity

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding!!

    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    private val launcherOrderActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            mainViewModel.getHome()
            Toast.makeText(this@MainActivity, "RESULT_OK Main", Toast.LENGTH_SHORT).show()
        }
    }

    private val mFragmentManager = supportFragmentManager
    private val mHomeFragment = HomeFragment()
    private val mProfileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setMode(R.id.home)

        binding.navBottom.setOnItemSelectedListener {
            setMode(it.itemId)
            it.itemId != R.id.order
        }
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode){
            R.id.home -> {
                navigateToFragment(mHomeFragment)
            }
            R.id.order -> {
                val intent = Intent(this, OrderActivity::class.java)
                launcherOrderActivity.launch(intent)
            }
            R.id.profile -> {
                navigateToFragment(mProfileFragment)
            }
        }
    }

    private fun navigateToFragment(mFragment: Fragment){
        mFragmentManager.commit {
            replace(R.id.frame_home, mFragment, mFragment::class.java.simpleName)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    companion object{
        const val RESULT_OK = 200
    }
}