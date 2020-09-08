package com.mindorks.bootcamp.learndagger.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mindorks.bootcamp.learndagger.data.local.dao.AddressDao
import com.mindorks.bootcamp.learndagger.data.local.dao.UserDao
import com.mindorks.bootcamp.learndagger.data.local.entity.Address
import com.mindorks.bootcamp.learndagger.data.local.entity.User

import com.mindorks.bootcamp.learndagger.di.ApplicationContext
import com.mindorks.bootcamp.learndagger.di.DatabaseInfo

import javax.inject.Inject
import javax.inject.Singleton

@Database(entities = [
    User::class,
    Address::class
], version = 2,
        exportSchema = false
)
@TypeConverters(Converter::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun addressDao() : AddressDao
}