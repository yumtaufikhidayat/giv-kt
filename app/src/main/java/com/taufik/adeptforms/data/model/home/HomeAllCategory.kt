package com.taufik.adeptforms.data.model.home

data class HomeAllCategory(
    var categoryTitle: String,
    var horizontalCategory: List<HomeChildCategory>,
    var verticalCategory: List<HomeChildCategory>
)
