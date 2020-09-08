package com.mindorks.bootcamp.learndagger.data.local.entity

import androidx.room.*
import java.util.*

@Entity(tableName = "users",
        foreignKeys = [
            ForeignKey(entity = Address::class,
                    parentColumns = ["id"],
                    childColumns = ["address_id"],
                    onDelete = ForeignKey.CASCADE)])
data class User(

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "address_id")
        var addressId: Long,

        @ColumnInfo(name = "date_of_birth")
        var dateOfBirth: Date,

        @Ignore
        var isSelected: Boolean = false
) {
    constructor() : this(id = 0, name = "",addressId =  0,dateOfBirth = Date(), isSelected = false)
}