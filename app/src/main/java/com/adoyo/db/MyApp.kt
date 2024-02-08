package com.adoyo.db

import android.app.Application
import android.os.Parcel
import android.os.Parcelable
import com.adoyo.db.models.Address
import com.adoyo.db.models.Course
import com.adoyo.db.models.Student
import com.adoyo.db.models.Teacher
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.migration.RealmMigration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(dbModule)
        }
    }
}