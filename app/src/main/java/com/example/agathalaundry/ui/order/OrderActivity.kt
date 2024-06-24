package com.example.agathalaundry.ui.order

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agathalaundry.R
import com.example.agathalaundry.adapter.OrderAdapter
import com.example.agathalaundry.data.Result
import com.example.agathalaundry.databinding.ActivityOrderBinding
import com.example.agathalaundry.ui.ViewModelFactory
import com.example.agathalaundry.ui.main.MainActivity
import com.example.agathalaundry.ui.main.ProfileFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class OrderActivity : AppCompatActivity() {

    private var _activityOrderBinding: ActivityOrderBinding? = null
    private val binding get() = _activityOrderBinding!!

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val orderViewModel: OrderViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityOrderBinding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBarOrder.setNavigationOnClickListener  { finish() }

        orderViewModel.listPackages.observe(this) { listPackages ->
            if (listPackages != null) {
                when (listPackages) {
                    is Result.Loading -> {
                        binding.pbOrder.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        //Setting items for packages dropdown
                        val ddPackageItems = listPackages.data.data.map { it.`package`.name }.distinct().toTypedArray()
                        (binding.ddOrderPackages as MaterialAutoCompleteTextView).setSimpleItems(ddPackageItems)

                        //Set default value for selected package and listServices
                        var selectedPackage = ddPackageItems[0]
                        var listServices =  listPackages.data.data.filter { it.`package`.name == selectedPackage }.toTypedArray()
                        (binding.ddOrderPackages as MaterialAutoCompleteTextView).setText(selectedPackage, false)

                        //Set service list dialog
                        var selectedServiceIndex = 0
                        binding.btnAddService.setOnClickListener {
                            MaterialAlertDialogBuilder(this@OrderActivity)
                                .setTitle(resources.getString(R.string.app_name))
                                .setNegativeButton("Cancel") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .setPositiveButton("Accept") { dialog, which ->
                                    Toast.makeText(this@OrderActivity,"${listServices[selectedServiceIndex].id}",Toast.LENGTH_SHORT).show()
                                    orderViewModel.addOrder(listServices[selectedServiceIndex].id)
                                }
                                .setSingleChoiceItems(listServices.map { it.service.name }.toTypedArray(), 0) { _, which ->
                                    selectedServiceIndex = which
                                }.show()
                        }

                        //Set on listener when dropdown package updated and update listServices dialog
                        binding.ddOrderPackages.setOnItemClickListener { adapterView, view, i, l ->
                            selectedPackage = ddPackageItems[i]
                            listServices = listPackages.data.data.filter { it.`package`.name == selectedPackage }.toTypedArray()
                        }

                        //Observe list orders
                        orderViewModel.listOrders.observe(this) { listOrders ->
                            //Update list order adapter
                            binding.tvTotalService.text = getString(R.string.order_total_orders, listOrders.size.toString())
                            val listOrderAdapter = OrderAdapter(
                                listServices = listPackages.data.data,
                                listOrders = listOrders,
                                onClick = { pos ->
                                    Toast.makeText(this@OrderActivity,"Deleted",Toast.LENGTH_SHORT).show()
                                    orderViewModel.deleteOrder(pos)
                                }
                            )
                            binding.listOrders.apply {
                                layoutManager = LinearLayoutManager(this@OrderActivity)
                                adapter = listOrderAdapter
                            }
                        }

                        binding.btnOrder.setOnClickListener {
                            orderViewModel.order().observe(this) { result ->
                                when (result) {
                                    is Result.Loading -> {
                                            binding.pbOrder.visibility = View.VISIBLE
                                    }
                                    is Result.Success -> {
                                        binding.pbOrder.visibility = View.GONE
                                        Toast.makeText(this@OrderActivity, result.data.message, Toast.LENGTH_SHORT).show()
                                        setResult(MainActivity.RESULT_OK, Intent())
                                        finish()
                                    }
                                    is Result.Error -> {
                                        binding.pbOrder.visibility = View.GONE
                                        Toast.makeText(this@OrderActivity, result.error, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }

                        binding.pbOrder.visibility = View.GONE
                    }

                    is Result.Error -> {
                        binding.pbOrder.visibility = View.GONE
                        Toast.makeText(this, listPackages.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityOrderBinding = null
    }
}