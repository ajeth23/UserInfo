package com.ajeproduction.codetest.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val first_name: String,
    val last_name: String,
    val age: Int,
) : Parcelable