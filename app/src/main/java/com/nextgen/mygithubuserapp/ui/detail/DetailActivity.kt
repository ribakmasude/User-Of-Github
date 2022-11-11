package com.nextgen.mygithubuserapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nextgen.mygithubuserapp.R
import com.nextgen.mygithubuserapp.data.Result
import com.nextgen.mygithubuserapp.databinding.ActivityDetailBinding
import com.nextgen.mygithubuserapp.adapter.SectionPagerAdapter
import com.nextgen.mygithubuserapp.ui.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding

    companion object{
        const val LOGIN = "login"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.text_follower, R.string.text_following)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(applicationContext)
        val viewModel: DetailViewModel by viewModels {
            factory
        }

        settingTabLayout()

        val username  = intent.getStringExtra(LOGIN)
        viewModel.getDetail(username!!).observe(this, { result->
            if (result != null){
                when(result){
                    is Result.Loading -> _binding?.pbItemdetail?.visibility = View.VISIBLE
                    is Result.Success -> {
                        val dataUser = result.data
                        _binding?.tvDetailUsername?.text = dataUser.login
                        _binding?.tvDetailName?.text = dataUser.name
                        _binding?.tvWorkAt?.text = dataUser.company
                        _binding?.tvLocation?.text = dataUser.location
                        _binding?.tvFollower?.text = dataUser.follower.toString()
                        _binding?.tvFollowing?.text = dataUser.following.toString()
                        _binding?.tvRepository?.text = dataUser.repository.toString()
                        Glide.with(this@DetailActivity)
                            .load(dataUser.avatar)
                            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                            .circleCrop()
                            .into(_binding?.ivDetailPhoto!!)
                        if (dataUser.isFavorite == true){
                            _binding?.ivDetailfavorite?.setImageDrawable(ContextCompat.getDrawable(
                                _binding?.ivDetailfavorite?.context!!, R.drawable.ic_baseline_stared_24))
                        }else{
                            _binding?.ivDetailfavorite?.setImageDrawable(ContextCompat.getDrawable(
                                _binding?.ivDetailfavorite?.context!!, R.drawable.ic_baseline_star_24))
                        }

                        _binding?.ivDetailfavorite?.setOnClickListener {
                            if (dataUser.isFavorite == true){
                                viewModel.deleteUser(dataUser)
                            }else{
                                viewModel.favUser(dataUser)
                            }
                        }
                        _binding?.pbItemdetail?.visibility = View.GONE
                    }
                    is Result.Error -> _binding?.pbItemdetail?.visibility = View.GONE
                }
            }
        })
    }

    private fun settingTabLayout() {
        with(_binding!!){
            val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity)
            sectionPagerAdapter.username = intent.getStringExtra(LOGIN).toString()
            val viewPager: ViewPager2 = viewPager
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = tabs
            TabLayoutMediator(tabs, viewPager){tab, position->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}