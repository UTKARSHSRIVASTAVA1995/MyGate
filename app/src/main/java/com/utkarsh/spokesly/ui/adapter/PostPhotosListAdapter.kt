package com.utkarsh.spokesly.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.utkarsh.spokesly.R
import com.utkarsh.spokesly.data.models.PostImageResponse


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
        var trimImage = item.thumbnailUrl.trim()
        val picasoo = Picasso.get()
        picasoo.load(trimImage).into(holder.image);
        holder.title.text =  "Title : ${item.title}"
        holder.album_id.text = "Album id: ${item.albumId}"
        holder.id.text = "Id : ${item.id}"
    }
}