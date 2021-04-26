/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tiparo.tripway.repository.database

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiparo.tripway.MainCoroutineRule
import com.tiparo.tripway.models.Trip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TripsDaoTest: DbTest() {
    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun createTripAndGetById() = runBlockingTest {
        // GIVEN - insert a trip
        val trip = Trip(tripName = "Путешествие", firstPointName = "Бангок", photoUri = Uri.EMPTY)

        val id = db.tripDao().insertTrip(trip)
        // WHEN - Get the trip by id from the database
        val loaded = db.tripDao().getTripById(id)

        // THEN - The loaded data contains the expected values
        assertThat<Trip>(loaded, notNullValue())
        assertThat(loaded.tripName, `is`(trip.tripName))
    }
}
