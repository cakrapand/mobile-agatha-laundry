package com.example.agathalaundry.ui.history

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
import com.example.agathalaundry.adapter.HistoryAdapter
import com.example.agathalaundry.data.Result
import com.example.agathalaundry.data.remote.response.BaseResponse
import com.example.agathalaundry.data.remote.response.model.Order
import com.example.agathalaundry.databinding.FragmentHistoryBinding
import com.example.agathalaundry.ui.ViewModelFactory
import com.example.agathalaundry.ui.detailorder.DetailOrderActivity

class HistoryFragment : Fragment() {
    private var _fragmentHistoryBinding: FragmentHistoryBinding? = null
    private val binding get() = _fragmentHistoryBinding!!

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val historyViewModel: HistoryViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity().dataStore)
    }

//    private val launcherOrderDetailActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        if (it.resultCode == RESULT_OK) {
//            historyViewModel.getOrders()
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _fragmentHistoryBinding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (arguments?.getInt(ARG_SECTION_NUMBER, 0)) {
            0 -> getOrders(true)
            1 -> getOrders(false)
        }
    }

    private fun getOrders(activeOnly: Boolean){
        historyViewModel.listOrders.observe(requireActivity()) { result ->
            when(result){
                is Result.Loading -> {
                    binding.pbHistory.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.pbHistory.visibility = View.GONE
                    if(result.data.data.isEmpty()) binding.listOrdersEmptyHistory.visibility = View.VISIBLE
                    else {
                        val listOrders = if(activeOnly)filterActiveOrders(result.data.data) else result.data.data
                        setListAdapter(listOrders)
                    }
                }
                is Result.Error -> {
                    binding.pbHistory.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setListAdapter(listOrders: List<Order>){
        val listHistoryAdapter = HistoryAdapter(requireContext(), listOrders) { order ->
            val intent = Intent(requireActivity(), DetailOrderActivity::class.java)
            intent.putExtra(DetailOrderActivity.EXTRA_ORDER_ID, order.id)
            startActivity(intent)
        }
        binding.listOrders.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = listHistoryAdapter
        }
    }

    private fun filterActiveOrders(listOrders: List<Order>): List<Order>{
         return listOrders.filter { it.orderStatus != "DONE" && it.orderStatus != "CANCEL" }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentHistoryBinding = null
    }

    companion object{
        const val ARG_SECTION_NUMBER = "section_number"
//        const val RESULT_OK = 200
    }

}