package com.ajeproduction.codetest.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfo: UserInfo)

    @Update
    suspend fun updateUserInfo(userInfo: UserInfo)

    @Delete
    suspend fun deleteUserInfo(userInfo: UserInfo)

    @Query("SELECT * FROM user_info")
    fun getAllUserInfo(): List<UserInfo> // Use LiveData for observing changes

    @Query("SELECT * FROM user_info WHERE first_name LIKE '%' || :query || '%' OR last_name LIKE '%' || :query || '%'")
    suspend fun searchUsers(query: String): List<UserInfo>
}