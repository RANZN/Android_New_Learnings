package dev.ranjan.androidnewlearnings.data

import androidx.room.*
import dev.ranjan.androidnewlearnings.data.entities.Director
import dev.ranjan.androidnewlearnings.data.entities.School
import dev.ranjan.androidnewlearnings.data.entities.Student
import dev.ranjan.androidnewlearnings.data.entities.Subject
import dev.ranjan.androidnewlearnings.data.entities.relations.SchoolAndDirector
import dev.ranjan.androidnewlearnings.data.entities.relations.StudentAndSubjectCrossRel
import dev.ranjan.androidnewlearnings.data.entities.relations.StudentWithSubjects
import dev.ranjan.androidnewlearnings.data.entities.relations.SubjectWithStudents

@Dao
interface TheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchool(school: School)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDirector(director: Director)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: Subject)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudentSubjectCrossRel(studentSubjectCrossRel: StudentAndSubjectCrossRel)

    @Transaction
    @Query("Select * from school where schoolName=:schoolName LIMIT 1")
    suspend fun schoolAndDirector(schoolName: String): SchoolAndDirector

    @Transaction
    @Query("select * from director where directorName=:directorName LIMIT 1")
    suspend fun directorAndSchool(directorName: String): SchoolAndDirector

    @Transaction
    @Query("select * from student where studentID=:studentID")
    suspend fun studentWithSubjects(studentID: Long): List<StudentWithSubjects>

    @Transaction
    @Query("select * from subject where subjectName=:subjectName")
    suspend fun subjectWithStudents(subjectName: String): List<SubjectWithStudents>


}