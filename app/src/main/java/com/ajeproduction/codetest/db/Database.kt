package com.ajeproduction.codetest.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [UserInfo::class],
    version = 1
)

abstract class Database : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

}