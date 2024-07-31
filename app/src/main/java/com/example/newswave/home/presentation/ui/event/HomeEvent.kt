package com.example.newswave.home.presentation.ui.event


sealed class HomeEvent {

    data class CategoryChanged(val category:String): HomeEvent()
}