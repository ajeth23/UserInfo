package com.ajeproduction.codetest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ajeproduction.codetest.databinding.ActivityMainBinding
import com.ajeproduction.codetest.db.App
import com.ajeproduction.codetest.db.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        updateDetails()
        saveDetails()
    }


    private fun updateDetails() {
        binding.viewAllInformation.setOnClickListener {
            val intent = Intent(this, UserDetails::class.java)
            startActivity(intent)
        }

    }

    private fun saveDetails() {
        binding.saveInformation.setOnClickListener {
            Log.d("MainActivity1231232", "Save button clicked")
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

                val userInfo = UserInfo(
                    first_name = firstName,
                    last_name = lastName,
                    age = age
                )

                CoroutineScope(Dispatchers.IO).launch {
                    App.database.userInfoDao().insertUserInfo(userInfo)
                }


                binding.firstname.text?.clear()
                binding.lastname.text?.clear()
                binding.age.text?.clear()

                Toast.makeText(this, "Details saved successfully", Toast.LENGTH_SHORT).show()


                Log.d("saved clicked?", "Save button clicked $userInfo")

            }
        }
    }


}


