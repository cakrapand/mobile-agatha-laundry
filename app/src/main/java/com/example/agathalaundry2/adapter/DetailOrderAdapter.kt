package com.example.agathalaundry2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agathalaundry2.data.remote.response.model.Order
import com.example.agathalaundry2.data.remote.response.model.OrderDetail
import com.example.agathalaundry2.data.remote.response.model.PackageOnService
import com.example.agathalaundry2.databinding.ItemDetailOrderBinding
import com.example.agathalaundry2.databinding.ItemOrderBinding
import com.example.agathalaundry2.utils.formatToRupiah

class DetailOrderAdapter(
    private val listDetailOrders: List<OrderDetail>,
) : RecyclerView.Adapter<DetailOrderAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemDetailOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemDetailOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvServiceItemDetailOrder.text = "${listDetailOrders[holder.adapterPosition].packageOnService.service.name} - ${listDetailOrders[holder.adapterPosition].packageOnService.`package`.name}"
        holder.binding.tvQuantityItemDetailOrder.text = "${listDetailOrders[holder.adapterPosition].quantity} x ${formatToRupiah( listDetailOrders[holder.adapterPosition].packageOnService.price)}"
        holder.binding.tvTotalItemDetailOrder.text = formatToRupiah(listDetailOrders[holder.adapterPosition].packageOnService.price * listDetailOrders[holder.adapterPosition].quantity)
    }

    override fun getItemCount(): Int = listDetailOrders.size
}