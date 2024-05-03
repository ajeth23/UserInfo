package com.ajeproduction.codetest

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ajeproduction.codetest.databinding.ActivityUpdateDetailsBinding
import com.ajeproduction.codetest.db.App
import com.ajeproduction.codetest.db.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetails : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateDetailsBinding
    private lateinit var userAdapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpdateDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        setupRecyclerView()
        setupSearch()

    }

    private fun setupRecyclerView() {
        CoroutineScope(Dispatchers.IO).launch {
            val userList = App.database.userInfoDao().getAllUserInfo()
            withContext(Dispatchers.Main) {
                userAdapter = UserAdapter(userList, onDeleteClick, onUpdateClick)
                binding.recyclerview.adapter = userAdapter
                userAdapter.updateList(userList)
            }
            Log.d("Update_details123213", "User list: $userList")
            fetchUserList()
        }

    }


    private val onDeleteClick: (UserInfo) -> Unit = { userInfo ->
        CoroutineScope(Dispatchers.IO).launch {
            App.database.userInfoDao().deleteUserInfo(userInfo)
            val userList = App.database.userInfoDao().getAllUserInfo()
            withContext(Dispatchers.Main) {
                userAdapter.updateList(userList)
            }
        }
    }

    private val onUpdateClick: (UserInfo) -> Unit = { userInfo ->
        val intent = Intent(this, EditUserDetails::class.java)
        intent.putExtra("userInfo", userInfo)
        startActivity(intent)
    }

    private val fetchUserList: () -> Unit = {
        CoroutineScope(Dispatchers.IO).launch {
            val userList = App.database.userInfoDao().getAllUserInfo()
            withContext(Dispatchers.Main) {
                userAdapter.updateList(userList)
            }
        }
    }

    private fun setupSearch() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                if (query.isEmpty()) {
                    fetchUserList()
                } else {
                    searchUsers(query)
                }
            }
        })
    }

    private fun searchUsers(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val filteredList = App.database.userInfoDao().searchUsers("%$query%")
            withContext(Dispatchers.Main) {
                userAdapter.updateList(filteredList)
            }
        }
    }


}