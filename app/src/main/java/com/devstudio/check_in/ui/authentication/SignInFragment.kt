package com.devstudio.check_in.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.devstudio.check_in.R
import com.devstudio.check_in.databinding.FragmentSignInBinding
import com.devstudio.check_in.viewModels.AuthenticationViewModel
class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding
    lateinit var viewModel:AuthenticationViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_sign_in, container, false)
        viewModel=ViewModelProviders.of(this).get(AuthenticationViewModel::class.java)
        binding.authenticationViewModel=viewModel
        return binding.root
    }
}