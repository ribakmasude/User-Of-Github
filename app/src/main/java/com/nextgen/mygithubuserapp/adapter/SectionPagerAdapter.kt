package com.nextgen.mygithubuserapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nextgen.mygithubuserapp.ui.follow.FollowFragment

class SectionPagerAdapter internal constructor(activity: AppCompatActivity):FragmentStateAdapter(activity){
    var username: String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        val bundle = Bundle()
        if (position == 0){
            bundle.putString(FollowFragment.ARG_TAB, FollowFragment.TAB_FOLLOWER)
            bundle.putString(FollowFragment.LOGIN, username)
        }else{
            bundle.putString(FollowFragment.ARG_TAB, FollowFragment.TAB_FOLLOWING)
            bundle.putString(FollowFragment.LOGIN, username)
        }
        fragment.arguments = bundle
        return fragment
    }
}