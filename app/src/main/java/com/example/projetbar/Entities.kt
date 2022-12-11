package com.example.projetbar

class Bar {
    var name: String = ""
    var openingHours: Boolean? = null
    var rating: Double = 0.0
    var vicinity: String = ""
    var lat: Double = 0.0
    var lng: Double = 0.0
    //var loca: String = ""
    //var x: Int = 0
    //var y: Int = 0


    constructor(result: com.example.projetbar.Result) {
        name = result.name
        if (result.openingHours != null) {
            openingHours = result.openingHours.openNow
        }
        rating  = result.rating
        vicinity = result.vicinity
        lat = result.geometry.location.lat
        lng = result.geometry.location.lng
    }

    override fun toString(): String {
        return this.name + " " + this.openingHours + " " + this.rating + " " + this.vicinity
    }
}