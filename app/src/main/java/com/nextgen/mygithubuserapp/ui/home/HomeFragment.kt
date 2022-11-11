package com.nextgen.mygithubuserapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.mygithubuserapp.adapter.ItemFollowAdapter
import com.nextgen.mygithubuserapp.adapter.UserAdapter
import com.nextgen.mygithubuserapp.data.Result
import com.nextgen.mygithubuserapp.data.local.entity.UserEntity
import com.nextgen.mygithubuserapp.databinding.FragmentHomeBinding
import com.nextgen.mygithubuserapp.ui.ViewModelFactory
import java.lang.Exception

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = _binding?.etUsername?.text.toString()
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext(), query)
        val viewModel: HomeViewModel by viewModels {
            factory
        }

        val userAdapter = UserAdapter{user ->
            if (user.isFavorite!!){
                viewModel.deleteUser(user)
            }else{
                viewModel.saveUser(user)
            }
        }

        viewModel.getUser().observe(viewLifecycleOwner){result ->
            if (result != null){
                when(result){
                    is Result.Loading -> _binding?.pbItem?.visibility = View.VISIBLE
                    is Result.Success -> {
                        _binding?.pbItem?.visibility = View.GONE
                        val userData = result.data
                        userAdapter.submitList(userData)
                    }
                    is Result.Error -> {
                        _binding?.pbItem?.visibility = View.GONE
                        _binding?.tvNodata?.visibility = View.VISIBLE
                    }
                }
            }
        }


        viewModel.loading.observe(viewLifecycleOwner, {loading ->
            isLoading(loading)
        })

        _binding?.etUsername?.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                val query = _binding?.etUsername?.text.toString()
                viewModel.getSearchUser(query)
                viewModel.listSearch.observe(viewLifecycleOwner, {result->
                    val itemAdapter = ItemFollowAdapter(result)
                    try {
                        _binding?.rvItem?.apply {
                            layoutManager = LinearLayoutManager(context)
                            setHasFixedSize(true)
                            adapter = itemAdapter
                        }
                    }catch (e: Exception){
                        Log.e("HomeFragment", "onfailuer: ${e.message.toString()}")
                    }
                })
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        _binding?.rvItem?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading){
            _binding?.pbItem?.visibility = View.VISIBLE
        }else{
            _binding?.pbItem?.visibility = View.GONE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}