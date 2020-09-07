package com.mindorks.bootcamp.learndagger.data.local.dao

import androidx.room.*
import com.mindorks.bootcamp.learndagger.data.local.entity.User
import io.reactivex.Single

@Dao
interface UserDao {

    /*
     * return the id of the user which inserted
     */

    @Insert
    fun insert(user : User) : Single<Long>

    /*
     * return the number of column which got updated
     */

    @Update
    fun update(user: User) : Single<Int>

    /*
     * return the number of column which got deleted
     */

    @Delete
    fun delete(user : User) : Single<Int>

    /*
     * retuen the multiple list of id which have inserted
     */

    @Insert
    fun insertMAny(vararg user: User) : Single<List<Long>>

    @Query("SELECT * FROM users")
    fun getAllUser() : Single<List<User>>

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    fun getUserByID(userId : String) : Single<User>

    @Query("SELECT count(*) FROM users")
    fun count() : Single<Int>


}