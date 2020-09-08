package com.mindorks.bootcamp.learndagger.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mindorks.bootcamp.learndagger.data.local.entity.Address
import io.reactivex.Single

@Dao
interface AddressDao {

    @Delete
    fun delete(address: Address) : Single<Int>

    @Insert
    fun insertMany(vararg address: Address) : Single<List<Long>>

    @Query("SELECT * FROM addresses")
    fun getAddress() : Single<List<Address>>
}