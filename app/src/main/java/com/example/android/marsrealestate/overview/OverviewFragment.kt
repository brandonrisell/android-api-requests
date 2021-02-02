/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.FragmentOverviewBinding
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsPropertyResponse

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class OverviewFragment : Fragment(R.layout.fragment_overview) {

    lateinit var viewModel: OverviewViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val viewBinding = FragmentOverviewBinding.bind(view)
        viewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)

        val adapter = PhotoListAdapter() {
            this.findNavController().navigate(
                OverviewFragmentDirections.actionShowDetail(it)
            )
        }
        viewBinding.photosGrid.adapter = adapter
        viewModel.response.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    is MarsPropertyResponse.Loading -> {
                        // viewBinding.marsImage.load(R.drawable.loading_animation)
                    }
                    is MarsPropertyResponse.Error -> {
                        // viewBinding.marsImage.load(R.drawable.ic_broken_image)
                    }
                    is MarsPropertyResponse.Success -> {
                        adapter.submitList(it.listings)
                    }
                    is MarsPropertyResponse.Empty -> {
                        // TODO: Show empty state
                    }
                }
            }
        }
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
                R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
                else -> MarsApiFilter.SHOW_ALL
            }
        )
        return true
    }
}
