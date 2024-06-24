package com.example.agathalaundry.ui.detailorder

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agathalaundry.adapter.DetailOrderAdapter
import com.example.agathalaundry.adapter.OrderActiveAdapter
import com.example.agathalaundry.data.Result
import com.example.agathalaundry.databinding.ActivityDetailOrderBinding
import com.example.agathalaundry.ui.ViewModelFactory
import com.example.agathalaundry.ui.history.HistoryFragment
import com.example.agathalaundry.ui.main.MainActivity
import com.example.agathalaundry.ui.payment.PaymentActivity
import com.example.agathalaundry.utils.addDaysToDate
import com.example.agathalaundry.utils.formatDate
import com.example.agathalaundry.utils.formatToRupiah

class DetailOrderActivity: AppCompatActivity() {

    private var _activityDetailOrderBinding: ActivityDetailOrderBinding? = null
    private val binding get() = _activityDetailOrderBinding!!

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    private val detailOrderViewModel: DetailOrderViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailOrderBinding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = if (Build.VERSION.SDK_INT >= 33) {
            intent.getStringExtra(EXTRA_ORDER_ID)
        }else{
            @Suppress("DEPRECATION")
            intent.getStringExtra(EXTRA_ORDER_ID)
        }!!

        binding.topAppBarDetailOrder.setNavigationOnClickListener  { finish() }

        detailOrderViewModel.getOrderById(orderId).observe(this@DetailOrderActivity){ result ->
            when(result){
                is Result.Loading ->{
                    binding.pbOrderDetail.visibility = View.VISIBLE
                }
                is Result.Success ->{
                    binding.pbOrderDetail.visibility = View.GONE

                    binding.tvIdDetailOrder.text = result.data.data.id
                    binding.tvStatusDetailOrder.text = result.data.data.orderStatus
                    binding.tvCreatedAtDetailOrder.text = formatDate(result.data.data.createdAt)
                    binding.tvEstimateDetailOrder.text= addDaysToDate(result.data.data.createdAt,  result.data.data.orderDetail.maxOf { it.packageOnService.`package`.duration })
                    binding.tvDetailOrderAmount.text = formatToRupiah(result.data.data.amount)

                    val listDetailOrderAdapter = DetailOrderAdapter(result.data.data.orderDetail)
                    binding.listDetailOrders.apply {
                        layoutManager = LinearLayoutManager(this@DetailOrderActivity)
                        adapter = listDetailOrderAdapter
                    }
                }
                is Result.Error ->{
                    binding.pbOrderDetail.visibility = View.GONE
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailOrderBinding = null
    }

    companion object{
        const val EXTRA_ORDER_ID = "extra_order_id"
    }
}