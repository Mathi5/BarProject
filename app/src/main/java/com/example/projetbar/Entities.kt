package com.example.projetbar

class Bar {
    var name: String = ""
    var price_level: String = ""
    var rating: String = ""
    //var loca: String = ""
    //var x: Int = 0
    //var y: Int = 0

    constructor(dict:Map<String,*>) {
        this.name = dict["name"].toString()
        this.price_level = dict["price_level"].toString()
        this.rating = dict["rating"].toString()

        //this.x = x
        //this.y = y
        //this.loca = loca
        }

    override fun toString(): String {
        return this.name + " " + this.price_level + " " + this.rating
    }
}