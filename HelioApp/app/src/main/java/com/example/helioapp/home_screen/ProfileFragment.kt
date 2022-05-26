package com.example.helioapp.home_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.helioapp.R
import com.example.helioapp.databinding.FragmentProfileBinding
import com.example.helioapp.sign_in_screen.SignInActivity
import com.example.helioapp.utils.Constant

class ProfileFragment : Fragment(),View.OnClickListener {

    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            clickHandler = this@ProfileFragment
        }
    }

    override fun onClick(v: View?) {
      when(v?.id) {
          R.id.btnLogout -> {
              val prefs = activity?.getSharedPreferences(Constant.SHAREDKEY, Context.MODE_PRIVATE)
              prefs?.edit()?.putBoolean(Constant.MAINSCREENKEY,false)?.apply()
              startActivity(Intent(requireContext(),SignInActivity::class.java))
              requireActivity().finish()
          }
      }
    }


}