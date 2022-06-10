package com.example.helioapp.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helioapp.database.HotelBookingDataClass
import com.example.helioapp.databinding.ItemOnBoardingScreenBinding
import com.example.helioapp.databinding.ItemRecentBookingListBinding
import com.example.helioapp.onboarding_screen.OnBoardingModel

class RecentBookingAdapter(): RecyclerView.Adapter<RecentBookingAdapter.ViewPagerHolder>() {

    var recentBookingList = emptyList<HotelBookingDataClass>()
    lateinit var binding: ItemRecentBookingListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        binding = ItemRecentBookingListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        binding.apply {
            imageBtnBookmark.setOnClickListener {
                it.isSelected = !it.isSelected
            }
        }
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
       binding.viewModel = recentBookingList[position]
    }

    override fun getItemCount(): Int {
        return recentBookingList.count()
    }

    class ViewPagerHolder(binding: ItemRecentBookingListBinding) : RecyclerView.ViewHolder(binding.root) {
        //implement Later when needed
    }

    fun setRoomData(db: List<HotelBookingDataClass>) {
        this.recentBookingList = db
        notifyDataSetChanged()
    }
}