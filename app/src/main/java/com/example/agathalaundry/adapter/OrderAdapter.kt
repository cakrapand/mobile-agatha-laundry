package com.example.agathalaundry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agathalaundry.data.remote.response.model.PackageOnService
import com.example.agathalaundry.databinding.ItemOrderBinding
import com.example.agathalaundry.utils.formatToRupiah

class OrderAdapter(
    private val listServices: List<PackageOnService>,
    private val listOrders: List<String>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = listServices.find { it.id === listOrders[position] }!!
        holder.binding.tvOrderItemService.text = "${service.`package`.name} - ${service?.service?.name}"
        holder.binding.tvDurationItemOrder.text = "${service.`package`.duration} Hari"
        holder.binding.tvPriceItemOrder.text = "${formatToRupiah(service.price)} @ Kg"
        holder.binding.btnOrderItemDelete.setOnClickListener {
            onClick(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = listOrders.size
}