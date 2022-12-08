package com.example.certification.jungchugi.data

data class Body(
    val items: List<Item>,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)