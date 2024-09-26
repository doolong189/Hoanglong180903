package com.hoanglong180903.vnc_project.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hoanglong180903.vnc_project.model.Ban
import com.hoanglong180903.vnc_project.repository.RetrofitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RetrofitViewModel(private val application: Application) : ViewModel() {

    private val repository: RetrofitRepository = RetrofitRepository()
    private val _ban = MutableLiveData<List<Ban>>()
    val bans: LiveData<List<Ban>> get() = _ban
    fun getAllBan() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllBan("/tablet_lish")
            _ban.postValue(response)
        }
    }


    class RetrofitViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RetrofitViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RetrofitViewModel(application) as T
            } else {
                throw IllegalArgumentException("viewmodel not found")
            }

        }
    }
}