package com.example.helioapp.home_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helioapp.R
import com.example.helioapp.databinding.FragmentHomeBinding
import com.example.helioapp.onboarding_screen.OnBoardingModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    var recentBookingList: ArrayList<RecentBookingModelClass> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home ,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        binding.apply {
            recyclerViewRecentlyBooked.layoutManager = LinearLayoutManager(requireContext())

        }
    }
    fun setAdapter(){
        setData()
        binding.recyclerViewRecentlyBooked.adapter = RecentBookingAdapter(recentBookingList)
    }
    fun setData(){
        recentBookingList.apply {
            add(RecentBookingModelClass("President Hotel","Paris, France","4.8","(4,378 reviews)","$35",R.drawable.first_item_image))
            add(RecentBookingModelClass("Palms Casino","Amsterdam, Netherlands","4.9","(5,283 reviews)","$29",R.drawable.second_item_image))
            add(RecentBookingModelClass("Palazzo Versace","Rome, Italia","4.7","(3,277 reviews)","$36",R.drawable.third_item_image))
            add(RecentBookingModelClass("Bulgari Resort","Istanbul, Turkiye","4.8","(4,981 reviews)","$27",R.drawable.fourth_item_image))
            add(RecentBookingModelClass("Martinez Cannes","London, United Kingdom","4.6","(3,672 reviews)","$32",R.drawable.fifth_item_image))
        }
    }
}