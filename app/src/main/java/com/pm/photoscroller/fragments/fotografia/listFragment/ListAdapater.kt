package com.pm.photoscroller.fragments.fotografia.listFragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pm.photoscroller.R
import com.pm.photoscroller.data.entities.Fotografia
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    private var fotografiaList = emptyList<Fotografia>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.itemView.lst_fotografiatId.text = currentItem.id.toString()
        holder.itemView.lst_fotografiaName.text = currentItem.name

        if(position%2 == 0){
            holder.itemView.rowLayout.setBackgroundColor(Color.parseColor("#d6d4e0"))
        }
        else {
            holder.itemView.rowLayout.setBackgroundColor(Color.parseColor("#b8a9c9"))
        }

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return fotografiaList.size
    }

    fun setData(products: List<Fotografia>){
        this.fotografiaList = fotografia
        notifyDataSetChanged()
    }
}