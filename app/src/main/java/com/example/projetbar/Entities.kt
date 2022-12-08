package com.example.projetbar

class Bar {
    var name: String = ""
    var price_level: Long = 0
    var rating: Double = 0.0
    //var loca: String = ""
    //var x: Int = 0
    //var y: Int = 0


    constructor(result: com.example.projetbar.Result) {
        name = result.name
        price_level = result.priceLevel
        rating  = result.rating
    }

    override fun toString(): String {
        return this.name + " " + this.price_level + " " + this.rating
    }
}