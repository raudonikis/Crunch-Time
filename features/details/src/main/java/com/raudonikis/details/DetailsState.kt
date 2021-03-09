package com.raudonikis.details

sealed class DetailsState {
    object Initial : DetailsState()
    object StatusUpdating : DetailsState()
    object StatusUpdateSuccess : DetailsState()
    object StatusUpdateFailure : DetailsState()
}