package com.raudonikis.details

sealed class DetailsState {
    object Initial : DetailsState()
    object DetailsUpdating : DetailsState()
    object DetailsUpdateSuccess : DetailsState()
    object DetailsUpdateFailure : DetailsState()
    object StatusUpdating : DetailsState()
    object StatusUpdateSuccess : DetailsState()
    object StatusUpdateFailure : DetailsState()
}