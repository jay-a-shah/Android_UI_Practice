package com.example.helioapp.onboarding_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helioapp.R
import com.example.helioapp.databinding.ItemOnBoardingScreenBinding

class OnBoardingAdapter(val list: ArrayList<OnBoardingAdapter>): RecyclerView.Adapter<OnBoardingAdapter.ViewPagerHolder>() {
    lateinit var binding: ItemOnBoardingScreenBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val viewHolder = ItemOnBoardingScreenBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewPagerHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: OnBoardingAdapter.ViewPagerHolder, position: Int) {
       // binding.itemImage =
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ViewPagerHolder(binding: ItemOnBoardingScreenBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}