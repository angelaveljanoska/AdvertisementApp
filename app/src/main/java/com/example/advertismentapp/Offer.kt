package com.example.advertismentapp

class Offer {

    var advCreatorId: String? = ""
    var buyerId: String? = ""
    var amount: String? = ""
    var advertisementId: String? = ""

    constructor(
        advCreatorId: String?,
        buyerId: String?,
        amount: String?,
        advertisementId: String?
    ) {
        this.advCreatorId = advCreatorId
        this.buyerId = buyerId
        this.amount = amount
        this.advertisementId = advertisementId
    }

    constructor()

    override fun toString(): String {
        return "Offer(advCreatorId=$advCreatorId, buyerId=$buyerId, amount=$amount, advertisementId=$advertisementId)"
    }


}