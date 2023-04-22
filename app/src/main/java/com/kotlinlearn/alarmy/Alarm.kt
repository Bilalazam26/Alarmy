package com.kotlinlearn.alarmy

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var time: Long,
    var name: String,
    var state: Boolean
    ): Serializable