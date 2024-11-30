package com.steven.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserKeyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserKey(users: List<UserKey>)

    @Query("DELETE FROM UserKey")
    suspend fun deleteUserKey()

    @Query("SELECT * FROM UserKey ORDER BY id DESC LIMIT 1")
    suspend fun getLastUserKey(): UserKey?

}