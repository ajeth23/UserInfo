package com.ajeproduction.codetest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ajeproduction.codetest.databinding.ActivityUpdateUserBinding
import com.ajeproduction.codetest.db.App
import com.ajeproduction.codetest.db.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditUserDetails : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserBinding
    private lateinit var userInfo: UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userInfo = intent.getParcelableExtra("userInfo")!!
        userInfo.let {
            binding.firstname.setText(it.first_name)
            binding.lastname.setText(it.last_name)
            binding.age.setText(it.age.toString())
        }

        updateUserDetails()

    }


    private fun updateUserDetails() {
        binding.update.setOnClickListener {
            if (binding.firstname.text.toString().isEmpty() && binding.lastname.text.toString()
                    .isEmpty() && binding.age.text.toString().isEmpty()
            ) {

                binding.firstname.error = "Please enter your first name"
                binding.lastname.error = "Please enter your last name"
                binding.age.error = "Please enter your age"
            } else {
                val firstName = binding.firstname.text.toString()
                val lastName = binding.lastname.text.toString()
                val age = binding.age.text.toString().toInt()

                val updatedUserInfo =
                    userInfo.copy(
                        first_name = firstName,
                        last_name = lastName,
                        age = age
                    )


                binding.firstname.text?.clear()
                binding.lastname.text?.clear()
                binding.age.text?.clear()

                CoroutineScope(Dispatchers.IO).launch {
                    App.database.userInfoDao().updateUserInfo(updatedUserInfo)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@EditUserDetails,
                            "Details updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@EditUserDetails, UserDetails::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }


}