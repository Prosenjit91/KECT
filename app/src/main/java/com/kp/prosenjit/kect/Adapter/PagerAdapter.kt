package com.kp.prosenjit.kect.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kp.prosenjit.kect.Fragment.ActivityFragment
import com.kp.prosenjit.kect.Fragment.GallaryFragment
import com.kp.prosenjit.kect.Fragment.MemberChatFragment


class PagerAdapter(val context: Context,fm: FragmentManager,val totalTabs:Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return ActivityFragment(context)
            }
            1 -> {
                return MemberChatFragment()
            }
            2 -> {
                return GallaryFragment(context)
            }

            else -> return null
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}