package com.example.advertismentapp

import com.google.firebase.firestore.DocumentId

class Advertisement {
    @DocumentId
    var documentId: String = ""

    var title: String? = ""
    var description: String? = ""
    var imageUrl: String? = ""
    var price: String? = ""
    var creatorId: String? = ""

    constructor(
        title: String?,
        description: String?,
        imageUrl: String?,
        price: String?,
        creatorId: String?,
        documentId: String
    ) {
        this.title = title ?: "No title provided"
        this.description = description ?: "No description provided"
        this.imageUrl = imageUrl ?: "https://image.shutterstock.com/image-vector/ui-image-placeholder-wireframes-apps-260nw-1037719204.jpg"
        this.price = price ?: "No price provided"
        this.creatorId = creatorId ?: "No creatorId (error)"
        this.documentId = documentId
    }

    constructor()

    override fun toString(): String {
        return "Advertisement(documentId='$documentId', title=$title, description=$description, imageUrl=$imageUrl, price=$price, creatorId=$creatorId)"
    }


}
