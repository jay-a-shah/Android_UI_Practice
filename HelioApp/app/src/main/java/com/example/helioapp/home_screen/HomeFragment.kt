package com.example.helioapp.home_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helioapp.R
import com.example.helioapp.database.HotelBookingDataClass
import com.example.helioapp.database.HotelBookingDetailsViewModel
import com.example.helioapp.databinding.FragmentHomeBinding
import com.example.helioapp.onboarding_screen.OnBoardingModel
import com.example.helioapp.utils.showMessage

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    var recentBookingList: ArrayList<RecentBookingModelClass> = arrayListOf()
    var hotelBookingList: ArrayList<HotelBookingDataClass> = arrayListOf()
    lateinit var hotelBookingDetailsViewModel: HotelBookingDetailsViewModel
    val myAdapter = RecentBookingAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home ,container,false)
        hotelBookingDetailsViewModel = ViewModelProvider(this).get(HotelBookingDetailsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        hotelBookingDetailsViewModel.addData(hotelBookingList)
        showMessage(requireContext(),"SuccessFull Data Added in Room")
        binding.apply {
            recyclerViewRecentlyBooked.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewRecentlyBooked.adapter = myAdapter
        }
        setAdapter()
    }
    fun setAdapter(){
        hotelBookingDetailsViewModel.readAllData.observe(viewLifecycleOwner){
            myAdapter.setRoomData(hotelBookingList)
        }
    }
    fun setData(){
//        recentBookingList.apply {
//            add(RecentBookingModelClass("President Hotel","Paris, France","4.8","(4,378 reviews)","$35",R.drawable.first_item_image))
//            add(RecentBookingModelClass("Palms Casino","Amsterdam, Netherlands","4.9","(5,283 reviews)","$29",R.drawable.second_item_image))
//            add(RecentBookingModelClass("Palazzo Versace","Rome, Italia","4.7","(3,277 reviews)","$36",R.drawable.third_item_image))
//            add(RecentBookingModelClass("Bulgari Resort","Istanbul, Turkiye","4.8","(4,981 reviews)","$27",R.drawable.fourth_item_image))
//            add(RecentBookingModelClass("Martinez Cannes","London, United Kingdom","4.6","(3,672 reviews)","$32",R.drawable.fifth_item_image))
//        }
        hotelBookingList.apply {
            add(HotelBookingDataClass("President Hotel","Paris, France","4.8","(4,378 reviews)","$35",R.drawable.first_item_image,1))
            add(HotelBookingDataClass("Palms Casino","Amsterdam, Netherlands","4.9","(5,283 reviews)","$29",R.drawable.second_item_image,2))
            add(HotelBookingDataClass("Palazzo Versace","Rome, Italia","4.7","(3,277 reviews)","$36",R.drawable.third_item_image,3))
            add(HotelBookingDataClass("Bulgari Resort","Istanbul, Turkiye","4.8","(4,981 reviews)","$27",R.drawable.fourth_item_image,4))
            add(HotelBookingDataClass("Martinez Cannes","London, United Kingdom","4.6","(3,672 reviews)","$32",R.drawable.fifth_item_image,5))
        }
    }
}