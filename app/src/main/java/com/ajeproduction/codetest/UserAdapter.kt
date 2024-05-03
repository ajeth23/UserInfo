package com.ajeproduction.codetest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajeproduction.codetest.databinding.ItemUpdateBinding
import com.ajeproduction.codetest.db.UserInfo

class UserAdapter(
    private var userList: List<UserInfo>,
    private val onDeleteClick: (UserInfo) -> Unit,
    private val onEditClick: (UserInfo) -> Unit,
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(val binding: ItemUpdateBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUpdateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userDetails = userList[position]

        // Set Editable values to TextViews
        holder.binding.firstname.text = userDetails.first_name
        holder.binding.lastname.text = userDetails.last_name
        holder.binding.age.text = userDetails.age.toString()

        holder.binding.delete.setOnClickListener {
            onDeleteClick(userDetails)
        }

        holder.binding.update.setOnClickListener {
            onEditClick(userDetails)
        }


    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateList(newList: List<UserInfo>) {
        userList = newList
        notifyDataSetChanged()
    }


}
