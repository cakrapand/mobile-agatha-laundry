package com.example.agathalaundry2.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agathalaundry2.adapter.OrderActiveAdapter
import com.example.agathalaundry2.data.Result
import com.example.agathalaundry2.databinding.FragmentHomeBinding
import com.example.agathalaundry2.ui.ViewModelFactory
import com.example.agathalaundry2.ui.detailorder.DetailOrderActivity
import com.example.agathalaundry2.ui.history.HistoryActivity

class HomeFragment : Fragment() {
    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val binding get() = _fragmentHomeBinding!!

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val mainViewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity().dataStore)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launcherOrderDetailActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                mainViewModel.getHome()
                Toast.makeText(requireActivity(), "RESULT_OK Main", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAllOrdersHome.setOnClickListener {
            startActivity(Intent(requireActivity(), HistoryActivity::class.java))
        }

        mainViewModel.homeData.observe(requireActivity()) {
            when(it){
                is Result.Loading -> {
                    binding.pbHome.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.pbHome.visibility = View.GONE
                    binding.tvNameHome.text = it.data.data.user.userProfile.name
                    binding.tvAddressHome.text = it.data.data.user.userProfile.address

                    if(it.data.data.orders.isNullOrEmpty()) binding.listActiveOrdersEmptyHome.visibility = View.VISIBLE
                    else binding.listActiveOrdersEmptyHome.visibility = View.GONE

                    val listOrderActiveAdapter = OrderActiveAdapter(requireContext(), it.data.data.orders) { order ->
                        val intent = Intent(requireActivity(), DetailOrderActivity::class.java)
                        intent.putExtra(DetailOrderActivity.EXTRA_ORDER_ID, order.id)
                        launcherOrderDetailActivity.launch(intent)
                    }
                    binding.listActiveOrdersHome.apply {
                        layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                        adapter = listOrderActiveAdapter
                    }
                }

                is Result.Error -> {
                    binding.pbHome.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentHomeBinding = null
    }

    companion object{
        const val RESULT_OK = 200
    }
}



