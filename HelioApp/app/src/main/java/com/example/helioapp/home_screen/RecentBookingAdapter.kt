package com.example.helioapp.home_screen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.helioapp.R
import com.example.helioapp.database.HotelBookingDataClass
import com.example.helioapp.database.HotelBookingDetailsViewModel
import com.example.helioapp.databinding.ItemOnBoardingScreenBinding
import com.example.helioapp.databinding.ItemRecentBookingListBinding
import com.example.helioapp.onboarding_screen.OnBoardingModel

class RecentBookingAdapter(val viewModel: HotelBookingDetailsViewModel): RecyclerView.Adapter<RecentBookingAdapter.ViewPagerHolder>(){

    var recentBookingList = emptyList<HotelBookingDataClass>()
    lateinit var binding: ItemRecentBookingListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        binding = ItemRecentBookingListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
       binding.viewModel = recentBookingList[position]
        var hotel = recentBookingList[position]
        binding.imageBtnBookmark.setOnClickListener {
            it.isSelected = !it.isSelected
            viewModel.updateData(position,it.isSelected )
            Log.d("Update","Yes")
        }
        binding.imageBtnBookmark.isSelected = hotel.bookmarkValue

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