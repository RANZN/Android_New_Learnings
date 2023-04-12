package dev.ranjan.androidnewlearnings.data.entities.relations

import androidx.room.Entity

@Entity(primaryKeys = ["studentID", "subject"])
data class StudentAndSubjectCrossRel(
    val studentID: String,
    val subject: String
)
