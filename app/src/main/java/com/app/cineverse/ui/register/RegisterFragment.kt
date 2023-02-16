package com.app.cineverse.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.cineverse.R
import com.app.cineverse.data.firebase.User
import com.app.cineverse.databinding.FragmentRegisterBinding
import com.app.cineverse.utils.*
import com.app.cineverse.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var binding : FragmentRegisterBinding by autoCleared()

    private val authViewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.btnRegister.setOnClickListener {
            if (validation()){
                authViewModel.register(
                    email = binding.emailInput.text.toString(),
                    password = binding.passwordInput.text.toString(),
                    rePassword = binding.passwordInput.text.toString(),
                    user = getUserObj()
                )
            }
        }
    }

    private fun observer() {
        authViewModel.register.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.btnRegister.text = getString(R.string.Connecting)
                    binding.btnRegister.disable()
                    binding.registerProgress.show()
                }
                is UiState.Failure -> {
                    binding.btnRegister.text = getString(R.string.submit)
                    binding.btnRegister.enabled()
                    binding.registerProgress.hide()
                    toast(getString(R.string.user_reg_succ_but))
                }
                is UiState.Success -> {
                    binding.btnRegister.text = getString(R.string.submit)
                    binding.registerProgress.hide()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    toast(getString(R.string.user_reg_succ))
                }
            }
        }
    }

    fun getUserObj(): User {
        return User(
            id = "",
            first_name = binding.firstNameInput.text.toString(),
            last_name = binding.lastNameInput.text.toString(),
            email = binding.emailInput.text.toString(),
        )
    }

    fun validation(): Boolean {
        var isValid = true

        if (binding.firstNameInput.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_first_name))
        }

        if (binding.lastNameInput.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_last_name))
        }

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
            else if (binding.repasswordInput.text.isNullOrEmpty()){
                isValid = false
                toast(getString(R.string.enter_password))
            }else{
                if (binding.repasswordInput.text.toString().length < 6){
                    isValid = false
                    toast(getString(R.string.invalid_password))
                }
                else if(binding.repasswordInput.text.toString() != binding.passwordInput.text.toString()){
                    isValid = false
                    toast(getString(R.string.invalid_repassword))
                }
            }
        }
        return isValid
    }
}