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
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.FragmentDetailBinding

/**
 * This [Fragment] will show the detailed information about a selected piece of Mars real estate.
 */
class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewBinding = FragmentDetailBinding.bind(view)

        val application = requireNotNull(activity).application

        val property = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty

        val viewModel = ViewModelProvider(this, DetailViewModelFactory(property, application)).get(
            DetailViewModel::class.java
        )

        viewModel.selectedProperty.observe(viewLifecycleOwner) {
            viewBinding.mainPhotoImage.load(it.imgSrcUrl.replace("http", "https")) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
            viewBinding.propertyTypeText.text = formatType(it.isRental, application)
            viewBinding.priceValueText.text = formatPrice(it.price, it.isRental, application)
        }
    }

    private fun formatPrice(price: Double, rental: Boolean, application: Application): String {
        return application.applicationContext.getString(
            when (rental) {
                true -> R.string.display_price_monthly_rental
                false -> R.string.display_price
            }, price
        )
    }

    private fun formatType(rental: Boolean, application: Application): String {
        return application.applicationContext.getString(
            R.string.display_type,
            application.applicationContext.getString(
                when (rental) {
                    true -> R.string.type_rent
                    false -> R.string.type_sale
                }
            )
        )
    }
}
