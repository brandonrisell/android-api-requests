/*
 *  Copyright 2019, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.android.marsrealestate.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.android.marsrealestate.network.MarsPropertyListing

/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
class DetailViewModel(@Suppress("UNUSED_PARAMETER") property: MarsPropertyListing, app: Application) : AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<MarsPropertyListing>()
    val selectedProperty: LiveData<MarsPropertyListing>
        get() = _selectedProperty

    init {
        _selectedProperty.value = property
    }


}

class DetailViewModelFactory(
    private val property: MarsPropertyListing,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(property, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
