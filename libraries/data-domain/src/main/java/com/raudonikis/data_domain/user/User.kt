package com.raudonikis.data_domain.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uuid: String,
    val name: String,
    val email: String,
    val isFollowed: Boolean,
) : Parcelable