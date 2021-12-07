package com.example.salon_ease.dashboard_vendor.classesDatabase

class userProfile {
    var name: String? = null
    var email: String? = null
    var mobile: String? = null
    var location: String? = null

    var city: String? = null
    var pin: String? = null
    var openingTime: String? = null
    var closingTime: String? = null

    constructor(Name: String?, Email: String?, Mobile: String?, Location: String?, City: String?, Pin: String?, OpeningTime: String?, ClosingTime: String?) {

        name = Name
        email = Email
        mobile = Mobile
        location = Location
        city = City
        pin = Pin
        openingTime = OpeningTime
        closingTime = ClosingTime

    }

    constructor() {}

    @JvmName("setName1")
    fun setName(name: String?) {
        this.name = name
    }
    @JvmName("setEmail1")
    fun setEmail(email: String?) {
        this.email = email
    }
    @JvmName("setMobile1")
    fun setMobile(name: String?) {
        this.mobile = mobile
    }
    @JvmName("setLocation1")
    fun setLocation(name: String?) {
        this.location = location
    }
    @JvmName("setCity1")
    fun setCity(name: String?) {
        this.city = city
    }
    @JvmName("setPin1")
    fun setPin(name: String?) {
        this.pin = pin
    }
    @JvmName("setOpeningTime1")
    fun setOpeningTime(name: String?) {
        this.openingTime = openingTime
    }
    @JvmName("setClosingTime1")
    fun setClosingTime(name: String?) {
        this.closingTime = closingTime
    }
}
