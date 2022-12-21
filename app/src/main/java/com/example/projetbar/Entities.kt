package com.example.projetbar

class Bar(result: com.example.projetbar.Result) {
    var name: String = ""
    var openingHours: Boolean? = null
    var rating: Double = 0.0
    var vicinity: String = ""
    var lat: Double = 0.0
    var lng: Double = 0.0
    var photo: String = ""
    //var loca: String = ""
    //var x: Int = 0
    //var y: Int = 0


    init {
        name = result.name
        openingHours = result.openingHours.openNow
        rating  = result.rating
        vicinity = result.vicinity
        lat = result.geometry.location.lat
        lng = result.geometry.location.lng
        photo = result.photos[0].photoReference
        this.toString()
    }

    override fun toString(): String {
        return this.name + " " + this.openingHours + " " + this.rating + " " + this.vicinity + " " + this.photo
    }
}