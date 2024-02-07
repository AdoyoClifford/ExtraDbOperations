package com.adoyo.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.util.concurrent.CountDownLatch

class Teacher: RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var address: Address? = null
    var course: RealmList<Course> = realmListOf()

}