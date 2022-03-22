package com.utkarsh.spokesly.ui.posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utkarsh.spokesly.R
import com.utkarsh.spokesly.databinding.FragmentPostImageBinding
import com.utkarsh.spokesly.di.utils.IResult
import com.utkarsh.spokesly.ui.adapter.PostCommentsListAdapter
import com.utkarsh.spokesly.ui.adapter.PostPhotosListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostImageFragment : Fragment() {

    private lateinit var mBinding: FragmentPostImageBinding
    private val viewModel: PostCommentsViewModel by viewModels()
    private lateinit var mPostPhotosAdapter: PostPhotosListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_post_image,
            container, false
        )
        initialize()
        return mBinding.root
    }

    private fun initialize() {
        mBinding.progressBar.visibility = View.VISIBLE
        fetchPostPhotos()
    }

    private fun fetchPostPhotos() {
        try {
            viewModel.fetchPostPhotosFromServer().observe(viewLifecycleOwner, Observer { users ->
                when (users.status) {
                    IResult.Status.SUCCESS -> {
                        mBinding.progressBar.visibility = View.GONE
                        if (users.data != null) {
                            val mPhotos = users.data
                            var layoutManager = LinearLayoutManager(requireContext())
                            val recyclerView: RecyclerView = mBinding.rvImageList
                            recyclerView.layoutManager = layoutManager
                            mPostPhotosAdapter = PostPhotosListAdapter(requireActivity(), mPhotos)
                            recyclerView.adapter = mPostPhotosAdapter

                        } else {
                        }
                    }
                    IResult.Status.ERROR -> {
                        mBinding.progressBar.visibility = View.GONE
                    }
                    IResult.Status.LOADING -> {

                    }
                }
            })
        } catch (E: Exception) {
            Log.e("Exception is", "$E")
        }
    }
}