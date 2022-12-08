package com.example.certification.jungchusangi

data class Body(
    val items: List<Item>,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)