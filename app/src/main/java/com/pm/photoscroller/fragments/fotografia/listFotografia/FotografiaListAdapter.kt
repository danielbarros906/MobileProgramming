package com.pm.photoscroller.fragments.fotografia.listFotografia

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pm.photoscroller.R
import com.pm.photoscroller.api.models.Fotografia
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.custom_row_fotografias_list.view.*

class FotografiaListAdapter ( userIdInSession: String?) : RecyclerView.Adapter<FotografiaListAdapter.MyViewHolder>(){
    private var fotografiaList = emptyList<Fotografia>()
    private val _userIdInSession = userIdInSession
    private var _ctx : Context? = null

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _ctx = parent.context

        return FotografiaListAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_row_fotografias_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = fotografiaList[position]
        holder.itemView.fotografia_list_title .text = currentItem.title
        holder.itemView.fotografia_list_description .text = currentItem.description
        holder.itemView.fotografia_list_iso.text = currentItem.iso
        holder.itemView.fotografia_list_created_at.text = currentItem.created_at
        holder.itemView.fotografia_list_shutter .text = currentItem.shutter
        holder.itemView.fotografia_list_aperture .text = currentItem.aperture
        holder.itemView.fotografia_list_location .text = currentItem.location
        holder.itemView.fotografia_list_created_by .text = currentItem.user_name
        holder.itemView.fotografia_list_photo_path .text = currentItem.photo_path

        if(position%2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#d6d4e0"))
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#b8a9c9"))
        }

        holder.itemView.rowLayout_fotografias_list.setOnClickListener{
            if (_userIdInSession == currentItem.users_id.toString()){
                val action = FotografiaListFragmentDirections.actionFotografiaListFragmentToUpdateFotografiaFragment(
                    currentItem
                )
                holder.itemView.findNavController().navigate(action)
            }else{
                Toast.makeText(_ctx,R.string.only_edit_your_fotografias, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return fotografiaList.size
    }
    fun setData(fotografiaList: List<Fotografia>){
        this.fotografiaList = fotografiaList
        notifyDataSetChanged()
    }
}
