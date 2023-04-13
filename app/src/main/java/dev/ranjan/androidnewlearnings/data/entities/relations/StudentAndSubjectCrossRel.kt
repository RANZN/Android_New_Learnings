package dev.ranjan.androidnewlearnings.data.entities.relations

import androidx.room.Entity

@Entity(primaryKeys = ["studentID", "subjectName"])
data class StudentAndSubjectCrossRel(
    val studentID: Long,
    val subjectName: String
)
