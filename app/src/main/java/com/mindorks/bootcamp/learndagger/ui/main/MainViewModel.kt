package com.mindorks.bootcamp.learndagger.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mindorks.bootcamp.learndagger.data.local.DatabaseService
import com.mindorks.bootcamp.learndagger.data.local.entity.Address
import com.mindorks.bootcamp.learndagger.data.local.entity.User
import com.mindorks.bootcamp.learndagger.data.remote.NetworkService
import com.mindorks.bootcamp.learndagger.di.ActivityScope
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import java.util.*

import javax.inject.Inject

@ActivityScope
class MainViewModel @Inject constructor(
        private val disposable: CompositeDisposable,
        private val databaseService: DatabaseService,
        private val networkService: NetworkService) {

    val user = MutableLiveData<User>()

    val users = MutableLiveData<List<User>>()
    val addresss = MutableLiveData<List<Address>>()

    private var allList: List<User> = emptyList()
    private var allAddress: List<Address> = emptyList()

    companion object {
        const val TAG = "MainViewModel"
    }

    init {
        disposable.add(databaseService.userDao()
                .count()
                .flatMap {
                    if (it == 0) {

                        databaseService.addressDao()
                                .insertMany(Address(city = "Chennai", country = "India", code = 0),
                                        Address(city = "Coimbatore", country = "India", code = 1),
                                        Address(city = "Delhi", country = "India",code = 2),
                                        Address(city = "London", country = "UK", code = 1))
                                .flatMap { addressId ->
                                    databaseService.userDao()
                                            .insertMAny(User(name = "one",addressId = addressId[0],dateOfBirth =  Date(8765)),
                                                    User(name = "two", addressId = addressId[1], dateOfBirth = Date(1234)),
                                            User(name = "three",addressId = addressId[2], dateOfBirth = Date(5678)))
                                }

                    } else {
                        Single.just(0)
                    }
                }
                .subscribeOn(io())
                .subscribe({
                    Log.d(TAG, "users esisiting")
                }, {
                    Log.d(TAG, it.toString())
                }))
    }

    fun getAllUser() {
        disposable.add(
                databaseService.userDao()
                        .getAllUser()
                        .subscribeOn(io())
                        .subscribe({
                            allList = it
                            users.postValue(it)
                        }, {
                            Log.d(TAG, it.toString())
                        })
        )
    }

    fun getAllAddress() {
        disposable.add(
                databaseService.addressDao()
                        .getAddress()
                        .subscribeOn(io())
                        .subscribe({
                            allAddress = it
                            addresss.postValue(it)
                        },{
                            Log.d(TAG, it.toString())
                        })
        )
    }

    fun deleteAddress() {
        if(allAddress.isNotEmpty())
        disposable.add(
                databaseService.addressDao()
                        .delete(allAddress[0])
                        .flatMap {
                            databaseService.addressDao()
                                    .getAddress()
                        }
                        .subscribeOn(io())
                        .subscribe({
                            allAddress = it
                            addresss.postValue(it)
                        }, {
                            Log.d(TAG, it.toString())
                        })
        )
    }

    fun deleteUser() {
        if (allList.isNullOrEmpty())
        disposable.add(
                databaseService.userDao()
                        .delete(allList[0])
                        .flatMap {
                            databaseService.userDao()
                                    .getAllUser()
                        }
                        .subscribeOn(io())
                        .subscribe({
                            allList = it
                            users.postValue(it)
                        }, {
                            Log.d(TAG, it.toString())
                        })
        )
    }

    fun onDestroy() {
        disposable.dispose()
    }

}
