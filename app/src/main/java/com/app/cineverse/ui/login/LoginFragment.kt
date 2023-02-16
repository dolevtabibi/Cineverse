package com.app.cineverse.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.cineverse.R
import com.app.cineverse.databinding.FragmentLoginBinding
import com.app.cineverse.ui.forgotpassword.ForgotPasswordFragment
import com.app.cineverse.utils.*

import com.app.cineverse.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding : FragmentLoginBinding by autoCleared()

    private val authViewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.btnLogin.setOnClickListener {
            if (validation()) {
                authViewModel.login(
                    email = binding.emailInput.text.toString(),
                    password = binding.passwordInput.text.toString()
                )
            }
        }

        binding.forgotPassword.setOnClickListener {
            val showPopup = ForgotPasswordFragment()
            showPopup.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnGuest.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        authViewModel.logout {
        }
    }

    private fun observer(){
        authViewModel.login.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.btnLogin.text = getString(R.string.Loading)
                    binding.btnLogin.disable()
                    binding.loginProgress.show()
                }
                is UiState.Failure -> {
                    binding.btnLogin.text = getString(R.string.Login)
                    binding.btnLogin.enabled()
                    binding.loginProgress.hide()
                    toast(getString(R.string.check_email_pass))
                }
                is UiState.Success -> {
                    binding.btnLogin.text = getString(R.string.Login)
                    binding.loginProgress.hide()
                    toast(state.data)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true

        if (binding.emailInput.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_email))
        }else{
            if (!binding.emailInput.text.toString().isValidEmail()){
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }
        if (binding.passwordInput.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_password))
        }else{
            if (binding.passwordInput.text.toString().length < 6){
                isValid = false
                toast(getString(R.string.invalid_password))
            }
        }
        return isValid
    }

    override fun onStart() {
        super.onStart()
        authViewModel.getSession { user ->
            if (user != null){
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }
}