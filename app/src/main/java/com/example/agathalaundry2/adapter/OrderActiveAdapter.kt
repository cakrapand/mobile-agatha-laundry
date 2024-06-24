package com.example.agathalaundry2.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agathalaundry2.R
import com.example.agathalaundry2.data.remote.response.model.Order
import com.example.agathalaundry2.databinding.ItemOrderActiveBinding
import com.example.agathalaundry2.utils.formatDate
import com.example.agathalaundry2.utils.formatToRupiah

class OrderActiveAdapter(
    private val context: Context,
    private val listActiveOrders: List<Order>,
    private val onClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderActiveAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemOrderActiveBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOrderActiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvHistoryDate.text = formatDate(listActiveOrders[holder.adapterPosition].createdAt)
        holder.binding.tvHistoryAmount.text = formatToRupiah(listActiveOrders[holder.adapterPosition].amount)
        holder.binding.tvHistoryQuantity.text = "${listActiveOrders[holder.adapterPosition].orderDetail.sumOf { it.quantity }} Kg"
        holder.binding.tvHistoryStatus.text = when(listActiveOrders[holder.adapterPosition].orderStatus) {
            "PAYMENT" -> context.getString(R.string.order_status_payment)
            "PICKED_UP" -> context.getString(R.string.order_status_picked_up)
            "ON_PROGRESS" -> context.getString(R.string.order_status_on_progress)
            "ON_DELIVER" -> context.getString(R.string.order_status_on_deliver)
            "DONE" -> context.getString(R.string.order_status_done)
            "CANCEL" -> context.getString(R.string.order_status_cancel)
            else -> ""
        }

        holder.itemView.setOnClickListener {
            onClick(listActiveOrders[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listActiveOrders.size
}