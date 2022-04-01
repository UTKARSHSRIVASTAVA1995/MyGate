package com.utkarsh.myGate.ui.fragments.userList

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
import com.utkarsh.myGate.R
import com.utkarsh.myGate.databinding.FragmentUserListBinding
import com.utkarsh.myGate.di.utils.IResult
import com.utkarsh.myGate.ui.adapter.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private lateinit var mBinding: FragmentUserListBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var mUserListAdapter: UserListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_list,
            container, false
        )
        initialize()
        return mBinding.root
    }

    private fun initialize() {
        mBinding.progressBar.visibility = View.VISIBLE
        fetchUsers()
    }

    private fun fetchUsers() {
        try {
            viewModel.fetchUsersFromServer().observe(viewLifecycleOwner, Observer { users ->
                when (users.status) {
                    IResult.Status.SUCCESS -> {
                        mBinding.progressBar.visibility = View.GONE
                        if (users.data != null) {
                            val mUsers = users.data
                            var layoutManager = LinearLayoutManager(requireContext())
                            val recyclerView: RecyclerView = mBinding.rvUserList
                            recyclerView.layoutManager = layoutManager
                            mUserListAdapter = UserListAdapter(mUsers)
                            recyclerView.adapter = mUserListAdapter

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