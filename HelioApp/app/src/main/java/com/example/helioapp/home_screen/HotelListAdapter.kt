package com.example.helioapp.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helioapp.databinding.ItemOnBoardingScreenBinding
import com.example.helioapp.onboarding_screen.OnBoardingModel

class HotelListAdapter(val list: ArrayList<HotelListModelClass>): RecyclerView.Adapter<HotelListAdapter.ViewPagerHolder>() {

    lateinit var binding: ItemOnBoardingScreenBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        binding = ItemOnBoardingScreenBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        binding.onBoardingViewModel = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewPagerHolder(binding: ItemOnBoardingScreenBinding) : RecyclerView.ViewHolder(binding.root) {
        //implement Later when needed
    }
}