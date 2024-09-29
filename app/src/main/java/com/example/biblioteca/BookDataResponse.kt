package com.example.biblioteca

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class BookDataResponse(
    @SerializedName("data") val books: List<BookItemResponse>
)
data class BookShowResponse(@SerializedName("data") val book: BookItemResponse)

data class BookItemResponse(
   @SerializedName("id") val bookId: String,
    @SerializedName("title") val bookTitle: String,
    @SerializedName("disponible") val bookAvailable: String,
   @SerializedName("author") val bookAutor: String,
    @SerializedName("editorial") val bookEditorial: String
)