package com.adoyo.db

import android.app.Application
import com.adoyo.db.models.Address
import com.adoyo.db.models.Course
import com.adoyo.db.models.Student
import com.adoyo.db.models.Teacher
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MyApp : Application() {
    companion object {
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()
        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    Address::class,
                    Course::class,
                    Student::class,
                    Teacher::class,
                )
            )
        )
    }
}