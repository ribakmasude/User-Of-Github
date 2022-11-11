package com.nextgen.mygithubuserapp.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.mygithubuserapp.adapter.ItemFollowAdapter
import com.nextgen.mygithubuserapp.data.remote.response.UserResponse
import com.nextgen.mygithubuserapp.databinding.FragmentFollowBinding


class FollowFragment : DialogFragment() {

    private lateinit var followViewModel: FollowViewModel
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var username: String
    val viewModel: FollowViewModel by viewModels{
        FollowViewModelFactory(username)
    }

    private var tabName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabName = arguments?.getString(ARG_TAB)
        username = arguments?.getString(LOGIN).toString()

        viewModel.isLoading.observe(viewLifecycleOwner, {isLoading->
            showLoading(isLoading)
        })

        viewModel.isDataNull.observe(viewLifecycleOwner, {isDataNull->
            showDataNull(isDataNull)
        })

        if (tabName == TAB_FOLLOWER){
            viewModel.follower.observe(viewLifecycleOwner, {result ->
                showLoading(true)
                if (result != null){
                    setAdapter(result)
                    showLoading(false)
                }else{
                    showLoading(false)
                    showDataNull(true)
                }
            })
        }else if (tabName == TAB_FOLLOWING){
            viewModel.following.observe(viewLifecycleOwner, {result->
                showLoading(true)
                if (result != null){
                    setAdapter(result)
                    showLoading(false)
                }else{
                    showLoading(false)
                    showDataNull(true)
                }
            })
        }
    }

    private fun showDataNull(dataNull: Boolean) {
        if (dataNull){
            _binding?.tvNodataFollow?.visibility = View.VISIBLE
        }else{
            _binding?.tvNodataFollow?.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            _binding?.pbItemFollow?.visibility = View.VISIBLE
        }else{
            _binding?.pbItemFollow?.visibility = View.GONE
        }
    }

    private fun setAdapter(result: List<UserResponse>) {
        val itemAdapter = ItemFollowAdapter(result)
        _binding?.rvUserFollow?.layoutManager = LinearLayoutManager(context)
        _binding?.rvUserFollow?.setHasFixedSize(true)
        _binding?.rvUserFollow?.adapter = itemAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val LOGIN = "login"
        const val ARG_TAB = "tab_name"
        const val TAB_FOLLOWER = "Follower"
        const val TAB_FOLLOWING = "Following"
    }
}