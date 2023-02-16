package com.app.cineverse

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.cineverse.databinding.ActivityMainBinding
import com.app.cineverse.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    var vibrator: Vibrator? = null

    private lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModels()

    private val negativeButtonClick = { _: DialogInterface, _: Int ->
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val bottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.homeNavHostFragment)
        bottomNavigationView.setupWithNavController(navController)

        val logoutItem = bottomNavigationView.menu.findItem(R.id.logoutFragment)
        logoutItem.setOnMenuItemClickListener {
            authViewModel.logout {
                val dialogBuilder = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.logout))
                    .setMessage(getString(R.string.sure_logout))
                    .setNegativeButton(getString(R.string.no), negativeButtonClick)
                    // if the dialog is cancelable
                    .setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener { _, _ ->
                        if (Build.VERSION.SDK_INT >= 26) {
                            vibrator?.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            vibrator?.vibrate(150);
                        }
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                        for (fragment in supportFragmentManager.fragments) {
                            supportFragmentManager.beginTransaction().remove(fragment).commit()
                        }
                    })
                val alert = dialogBuilder.create()
                alert.setCancelable(false)
                alert.setTitle(getString(R.string.logout))
                alert.show()
            }
            true
        }
        bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.loginFragment || nd.id == R.id.registerFragment) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}


