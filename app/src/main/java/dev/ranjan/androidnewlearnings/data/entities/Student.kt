package dev.ranjan.androidnewlearnings.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    val studentName: String,
    @PrimaryKey(autoGenerate = false)
    val studentID: Long
)
