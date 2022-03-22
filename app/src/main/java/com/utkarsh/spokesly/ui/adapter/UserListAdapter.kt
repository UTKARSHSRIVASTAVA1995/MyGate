package com.utkarsh.spokesly.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.utkarsh.spokesly.R
import com.utkarsh.spokesly.data.models.UserResponse
import com.utkarsh.spokesly.di.utils.generateInitials


class UserListAdapter(
    private var userListList: UserResponse,
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.tvUserName)
        val email: TextView = itemView.findViewById(R.id.tvEmail)
        val userName: TextView = itemView.findViewById(R.id.tvName)
        val avatarName: TextView = itemView.findViewById(R.id.avatarName)
        val cvAvatar: CardView = itemView.findViewById(R.id.cvAvatar)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_user_item_list, parent, false)
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
        val color = String.format("%X", item?.name.toString().hashCode()).substring(0, 4)
        val randomAndroidColor = "#FF$color"
        holder.cvAvatar.background.setTint(Color.parseColor(randomAndroidColor))
        holder.avatarName.text = generateInitials(item?.name.toString())
        holder.name.text = "Name : ${item.name}"
        holder.email.text = "Email-Id :  ${item.email}"
        holder.userName.text = "User Name :  ${item.username}"
    }
}