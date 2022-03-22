package com.utkarsh.spokesly.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utkarsh.spokesly.R
import com.utkarsh.spokesly.data.models.PostCommentsResponse
import com.utkarsh.spokesly.data.models.PostImageResponse
import com.utkarsh.spokesly.data.models.UserResponse


class PostPhotosListAdapter(
    private val argContext: Context,
    private var photoList: PostImageResponse,
) : RecyclerView.Adapter<PostPhotosListAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_image)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val album_id: TextView = itemView.findViewById(R.id.tv_album_id)
        val id: TextView = itemView.findViewById(R.id.tv_id)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_post_photo_list, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        if (photoList != null) {
            return photoList!!.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = photoList?.get(position)
        Log.e("Photo Url","${item.thumbnailUrl}")
        Glide.with(argContext)
            .load(item.thumbnailUrl)
            .placeholder(R.drawable.ic_image_preview) //When there is No Image
            .error(R.drawable.ic_image_preview) // When Some URL fails to Load.
            .into(holder.image)
        holder.title.text =  "Title : ${item.title}"
        holder.album_id.text = "Album id: ${item.albumId}"
        holder.id.text = "Id : ${item.id}"
    }
}