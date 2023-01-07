package com.example.projetbar

import android.location.Location

class Bar(result: Result, crntLocation: Location) {
    var name: String = ""
    var openingHours: Boolean? = null
    var rating: Double = 0.0
    var vicinity: String = ""
    var lat: Double = 0.0
    var lng: Double = 0.0
    var photo: String = ""
    var distance: Double = 0.0

    init {

        name = result.name
        if (result.opening_hours != null) {
            openingHours = result.opening_hours.openNow
        }
        rating  = result.rating
        vicinity = result.vicinity
        lat = result.geometry.location.lat
        lng = result.geometry.location.lng
        photo = result.photos[0].photo_reference
        println("debug : "+photo)

        //Récupérer la distance du bar

        val currentLocation = Location("currentLocation")
        currentLocation.setLatitude(crntLocation.latitude)
        currentLocation.setLongitude(crntLocation.longitude)

        val newLocation = Location("newLocation")
        newLocation.latitude = lat
        newLocation.longitude = lng

        val distTmp = currentLocation.distanceTo(newLocation) / 1000 // in km
        distance = Math.round(distTmp * 10.0) / 10.0

        //distance = currentLocation.distanceTo(newLocation) / 1000 // in km
    }

    override fun toString(): String {
        return this.name + " " + this.openingHours + " " + this.rating + " " + this.vicinity + " " + this.photo
    }

    
}