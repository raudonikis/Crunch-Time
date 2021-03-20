package com.raudonikis.activity

import androidx.lifecycle.ViewModel
import com.raudonikis.data_domain.activity.repo.ActivitiesRepository
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserActivityViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val activitiesRepository: ActivitiesRepository,
) : ViewModel() {
}