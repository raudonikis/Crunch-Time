package com.raudonikis.data_domain.screenshots

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Screenshot(
    val url: String
) : Parcelable
