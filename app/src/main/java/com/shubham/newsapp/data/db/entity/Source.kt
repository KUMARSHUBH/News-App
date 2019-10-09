package com.shubham.newsapp.data.db.entity


import androidx.room.Embedded

data class Source(
    @Embedded(prefix = "source_")
    val id: String?,

    @Embedded(prefix = "source_")
    val name: String?
)