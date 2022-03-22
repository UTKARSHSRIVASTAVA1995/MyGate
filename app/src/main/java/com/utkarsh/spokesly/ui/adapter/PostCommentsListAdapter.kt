package com.utkarsh.spokesly.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utkarsh.spokesly.R
import com.utkarsh.spokesly.data.models.PostCommentsResponse
import com.utkarsh.spokesly.data.models.UserResponse


class PostCommentsListAdapter(
    private var userListList: PostCommentsResponse,
) : RecyclerView.Adapter<PostCommentsListAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val email: TextView = itemView.findViewById(R.id.tvEmail)
        val userName: TextView = itemView.findViewById(R.id.tvUserName)
        val id: TextView = itemView.findViewById(R.id.iuserid)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_post_list, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        if (userListList != null) {
            return userListList!!.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = userListList?.get(position)
        holder.name.text = "Name : ${item.name}"
        holder.email.text = "Email-Id :  ${item.email}"
        holder.userName.text = "Comments :  ${item.body}"
        holder.id.text = item.id.toString()
    }
}