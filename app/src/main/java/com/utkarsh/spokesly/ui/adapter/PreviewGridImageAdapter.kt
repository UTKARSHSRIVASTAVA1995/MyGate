package com.utkarsh.spokesly.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utkarsh.spokesly.R
import com.utkarsh.spokesly.data.models.PreviewGridImgRow
import com.utkarsh.spokesly.di.network.service.interfaces.PreviewImageInterface


class PreviewGridImageAdapter(var context: Context, mPreviewImageInterface: PreviewImageInterface) : RecyclerView.Adapter<PreviewGridImageAdapter.ViewHolder>() {

    var dataList = emptyList<PreviewGridImgRow>()
    private var previewGridView = mPreviewImageInterface
    internal fun setDataList(dataList: List<PreviewGridImgRow>) {
        this.dataList = dataList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var closeButton: ImageView
        init {
            image = itemView.findViewById(R.id.gridImg)
            closeButton = itemView.findViewById(R.id.closeBtn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewGridImageAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.preview_grid_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreviewGridImageAdapter.ViewHolder, position: Int) {
        var data = dataList[position]

        Glide.with(context)
            .load(Uri.parse(data.img.toString()))
            .placeholder(R.drawable.ic_image_preview)
            .into(holder.image)

        holder.closeButton.setOnClickListener {
            previewGridView.onCloseClick(data.img, position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = dataList.size

}