package com.sample.billeasy.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.billeasy.database.Movies.MovieRepo
import com.sample.billeasy.ui.BHPViewmodel

class BilleasyHomeViewModelFactory (private val iur : MovieRepo)
    :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BHPViewmodel::class.java)) {
            return BHPViewmodel(iur) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}