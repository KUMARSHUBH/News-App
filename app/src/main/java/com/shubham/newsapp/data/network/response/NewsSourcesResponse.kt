package com.shubham.newsapp.data.network.response


import com.shubham.newsapp.data.db.entity.SourceX

data class NewsSourcesResponse(
    val status: String,
    val sources: List<SourceX>
)