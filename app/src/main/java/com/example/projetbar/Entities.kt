package com.example.projetbar

class Bar {
    var name: String = ""
    var openingHours: String = ""
    var rating: Double = 0.0
    var vicinity: String = ""
    //var loca: String = ""
    //var x: Int = 0
    //var y: Int = 0


    constructor(result: com.example.projetbar.Result) {
        name = result.name
        openingHours = result.openingHours.toString()
        rating  = result.rating
        vicinity = result.vicinity
    }

    override fun toString(): String {
        return this.name + " " + this.openingHours + " " + this.rating + " " + this.vicinity
    }
}