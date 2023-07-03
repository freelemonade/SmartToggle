package com.example.smarttoggle.feature_schedule.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
