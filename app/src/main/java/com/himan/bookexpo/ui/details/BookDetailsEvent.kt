package com.himan.bookexpo.ui.details

sealed interface BookDetailsEvent {

    data class OpenBookUrl(
        val url: String
    ) : BookDetailsEvent
}