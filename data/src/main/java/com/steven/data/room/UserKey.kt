package com.steven.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserKey(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var prex : Int? = 0,
    var next : Int? = 0
)