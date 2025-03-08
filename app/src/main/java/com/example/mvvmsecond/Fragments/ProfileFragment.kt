package com.example.mvvmsecond.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mvvmsecond.AllDataModel.UserProfileDataModel
import com.example.mvvmsecond.AllViewModel.GetUserViewModel
import com.example.mvvmsecond.ProfileEditActivity
import com.example.mvvmsecond.R
import com.example.mvvmsecond.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {
        lateinit var binding: FragmentProfileBinding
        lateinit var viewModel: GetUserViewModel
        var list=ArrayList<UserProfileDataModel>()
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[GetUserViewModel::class.java]

        viewModel.getUserProfileData()

        binding.progressBar.visibility = View.VISIBLE

        viewModel.user.observe(viewLifecycleOwner){
            binding.profileNameTV.text = it.username
            binding.profileEmailTV.text = it.email
            Glide.with(requireContext()).load(it.image).into(binding.profileImageView)
            Glide.with(requireContext()).load(it.image).into(binding.profileImageEdit)
            list.add(it)
            binding.progressBar.visibility = View.GONE
        }

        binding.editProfileLinearLayout.setOnClickListener {
            val intent= Intent(requireContext(), ProfileEditActivity::class.java)
            intent.putExtra("username",binding.profileNameTV.text.toString())
            intent.putExtra("email",binding.profileEmailTV.text.toString())
            intent.putExtra("image",list[0].image)
            startActivity(intent)
        }
        binding.logoutLinearLayout.setOnClickListener {
            auth.signOut()
        }

        return view

    }

}