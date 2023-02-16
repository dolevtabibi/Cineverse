package com.app.cineverse.ui.forgotpassword

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.app.cineverse.R
import com.app.cineverse.databinding.FragmentForgotpasswordBinding
import com.app.cineverse.utils.*
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordFragment : DialogFragment() {

    private var binding : FragmentForgotpasswordBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotpasswordBinding.inflate(inflater, container, false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resetButton.setOnClickListener{
            val email: String = binding.emailToSend.text.toString().trim() {it <= ' '}
            if(email.isEmpty()){
                Toast.makeText(requireContext(),getString(R.string.enter_email),Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task->
                        if (task.isSuccessful){
                            Toast.makeText(requireContext(),
                                getString(R.string.Email_sent_succ),
                                Toast.LENGTH_LONG
                            ).show()
                            dialog!!.dismiss()
                        }else{
                            binding.resetButton.setText(getString(R.string.Reset_Password))
                            binding.barProgress.show()
                            Handler().postDelayed({
                                binding.barProgress.hide()
                            }, 2000L)
                            Toast.makeText(
                                requireContext(),
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true

        if (binding.emailToSend.text.trim().isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_email))
        }else{
            if (!binding.emailToSend.text.toString().isValidEmail()){
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }
        return isValid
    }
}