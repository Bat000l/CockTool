package com.supdevinci.cocktool.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "my_cocktails")
data class CocktailEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val instructions: String,
    
    val thumbUrl: String? = null,

    val ingredient1: String? = null,
    val measure1: String? = null,
    val ingredient2: String? = null,
    val measure2: String? = null,
    val ingredient3: String? = null,
    val measure3: String? = null,
    val ingredient4: String? = null,
    val measure4: String? = null,
    val ingredient5: String? = null,
    val measure5: String? = null,
    val ingredient6: String? = null,
    val measure6: String? = null,
    val ingredient7: String? = null,
    val measure7: String? = null,
    val ingredient8: String? = null,
    val measure8: String? = null,
    val ingredient9: String? = null,
    val measure9: String? = null,
    val ingredient10: String? = null,
    val measure10: String? = null,
    val ingredient11: String? = null,
    val measure11: String? = null,
    val ingredient12: String? = null,
    val measure12: String? = null,
    val ingredient13: String? = null,
    val measure13: String? = null,
    val ingredient14: String? = null,
    val measure14: String? = null,
    val ingredient15: String? = null,
    val measure15: String? = null,

    val isFavorite: Boolean = false,

    val createdAt: Date = Date(),
    val updatedAt: Date? = null,
    val deletedAt: Date? = null
)