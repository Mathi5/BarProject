package com.example.projetbar

import com.beust.klaxon.*

private val klaxon = Klaxon()

data class Welcome1 (
    @Json(name = "html_attributions")
    val htmlAttributions: List<Any?>,

    val results: List<Result>,
    val status: String
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<Welcome1>(json)
    }
}

data class Result (
    @Json(name = "business_status")
    val businessStatus: String,

    val geometry: Geometry,
    val icon: String,

    @Json(name = "icon_background_color")
    val iconBackgroundColor: String,

    @Json(name = "icon_mask_base_uri")
    val iconMaskBaseURI: String,

    val name: String,

    //@Json(name = "opening_hours")
    //val openingHours: OpeningHours,

    val opening_hours: OpeningHours,

    val photos: List<Photo>,

    @Json(name = "place_id")
    val placeID: String,

    @Json(name = "plus_code")
    val plusCode: PlusCode,

    @Json(name = "price_level")
    val priceLevel: Long,

    val rating: Double,
    val reference: String,
    val scope: String,
    val types: List<String>,

    @Json(name = "user_ratings_total")
    val userRatingsTotal: Long,

    val vicinity: String
)

data class Geometry (
    val location: Location,
    val viewport: Viewport
)

data class Location (
    val lat: Double,
    val lng: Double
)

data class Viewport (
    val northeast: Location,
    val southwest: Location
)

data class OpeningHours (
    @Json(name = "open_now")
    val openNow: Boolean
)

data class Photo (
    val height: Long,

    //@Json(name = "html_attributions")
    //val htmlAttributions: List<String>,
    val html_attributions: List<String>,

    //@Json(name = "photo_reference")
    //val photoReference: String,
    val photo_reference: String,

    val width: Long
)

data class PlusCode (
    @Json(name = "compound_code")
    val compoundCode: String,

    @Json(name = "global_code")
    val globalCode: String
)
