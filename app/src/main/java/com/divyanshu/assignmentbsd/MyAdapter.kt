package com.divyanshu.assignmentbsd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val longitude:ArrayList<String>,val latitude:ArrayList<String>,val address:ArrayList<String>,onClickInterface:onClickInterface): RecyclerView.Adapter<MyAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.custom_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.tvlong.text = longitude.get(position)
        holder.tvlati.text = latitude.get(position)
        holder.tvaddres.text = address.get(position)
        //holder.btnview.setOnClickListener { view -> (position) }

    }

    override fun getItemCount(): Int {
        return  longitude.size
    }
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val tvlong:TextView = itemView.findViewById(R.id.longlist)
        val tvlati:TextView = itemView.findViewById(R.id.latilist)
        val tvaddres:TextView = itemView.findViewById(R.id.addrlist)
        val btnview:Button = itemView.findViewById(R.id.btnview)
    }



}