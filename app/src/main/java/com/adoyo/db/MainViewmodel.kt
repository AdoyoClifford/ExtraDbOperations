package com.adoyo.db

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adoyo.db.models.Address
import com.adoyo.db.models.Course
import com.adoyo.db.models.Student
import com.adoyo.db.models.Teacher
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewmodel : ViewModel() {

    private val realm = MyApp.realm

    val courses = realm
        .query<Course>()
        .asFlow()
        .map { result ->
            result.list.toList()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

//    init {
//        createSampleEntries()
//    }

    var coursedetails: Course? by mutableStateOf(null)
        private set


    fun showDialog(course: Course) {
        coursedetails = course
    }

    fun hideDialog() {
        coursedetails = null
    }

    fun delete() {
        viewModelScope.launch {
            realm.write {
                val course = coursedetails?: return@write
                val latestCourse = findLatest(course)?: return@write
                delete(latestCourse)
                coursedetails = null

            }
        }
    }

    private fun createSampleEntries() {
        viewModelScope.launch {
            realm.write {
                val address1 = Address().apply {
                    fullNAme = "John Doe"
                    street = "Main Street"
                    houseNumber = 1
                    zip = 12345
                    city = "New York"
                }
                val address2 = Address().apply {
                    fullNAme = "Jane Doe"
                    street = "Main Street"
                    houseNumber = 2
                    zip = 12345
                    city = "New York"
                }

                val course1 = Course().apply {
                    name = "Math"

                }

                val course2 = Course().apply {
                    name = "Kotlin Basics"
                }

                val course3 = Course().apply {
                    name = "Data Structures"
                }

                val teacher1 = Teacher().apply {
                    address = address1
                    courses = realmListOf(course1, course2)
                }

                val teacher2 = Teacher().apply {
                    address = address2
                    courses = realmListOf(course3)
                }

                course1.teacher = teacher1
                course2.teacher = teacher1
                course3.teacher = teacher2

                address1.teacher = teacher1
                address2.teacher = teacher2

                val student1 = Student().apply {
                    name = "John Junior"
                }

                val student2 = Student().apply {
                    name = "Jane Junior"
                }

                course1.enrolledStudents.add(student1)
                course2.enrolledStudents.add(student2)
                course3.enrolledStudents.addAll(listOf(student1, student2))

                copyToRealm(teacher1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(teacher2, updatePolicy = UpdatePolicy.ALL)


                copyToRealm(course1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course2, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course3, updatePolicy = UpdatePolicy.ALL)

                copyToRealm(student1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(student2, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }
}