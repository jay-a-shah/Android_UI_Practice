package com.example.helioapp.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helioapp.BR
import com.example.helioapp.databinding.ItemOnBoardingScreenBinding

class OnBoardingAdapter(private val onBoardingList: ArrayList<OnBoardingModel>) : RecyclerView.Adapter<OnBoardingAdapter.ViewPagerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding = ItemOnBoardingScreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) = holder.bind(onBoardingList[position])

    override fun getItemCount(): Int = onBoardingList.size

    class ViewPagerHolder(val binding: ItemOnBoardingScreenBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(itemViewModel: OnBoardingModel) {
            binding.setVariable(BR.onBoardingViewModel, itemViewModel)
        }
    }
}