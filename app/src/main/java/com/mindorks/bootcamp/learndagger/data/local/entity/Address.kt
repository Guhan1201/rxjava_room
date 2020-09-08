package com.mindorks.bootcamp.learndagger.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class Address(

        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,

        @ColumnInfo(name = "city")
        val city: String,

        @ColumnInfo(name = "country")
        val country: String,

        @ColumnInfo(name = "code")
        val code: Int

) {
    constructor() : this(0, "", "", 0)
}