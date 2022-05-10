package com.example.helioapp.onboarding_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helioapp.R
import com.example.helioapp.databinding.ItemOnBoardingScreenBinding

class OnBoardingAdapter(val list: ArrayList<OnBoardingModel>): RecyclerView.Adapter<OnBoardingAdapter.ViewPagerHolder>() {

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

    }
}