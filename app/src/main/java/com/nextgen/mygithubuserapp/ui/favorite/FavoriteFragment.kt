package com.nextgen.mygithubuserapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.mygithubuserapp.adapter.UserAdapter
import com.nextgen.mygithubuserapp.databinding.FragmentFavoriteBinding
import com.nextgen.mygithubuserapp.ui.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        val userAdapter = UserAdapter{user ->
            if (user.isFavorite!!){
                viewModel.deleteUser(user)
            }
        }

        viewModel.getFavoriteUser().observe(viewLifecycleOwner){favorited ->
            if (favorited != null){
                userAdapter.submitList(favorited)
            }else{
                showNoData(true)
            }
        }

        _binding?.rvItemFavorite?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    private fun showNoData(noData: Boolean) {
        _binding?.tvNodataFavorite?.visibility = if (noData) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}