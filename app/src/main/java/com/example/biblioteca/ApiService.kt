package com.example.biblioteca
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/api/book/show/{id}")
    suspend fun getBook(@Path("id") bookId:String): Response<BookShowResponse>

    @GET("/api/books/{title}")
    suspend fun getBooks(@Path("title") bookTitle:String?):Response<BookDataResponse>

    @FormUrlEncoded
    @POST("/api/books")
    suspend fun addBook(@Field("title") title:String, @Field("author") author:String, @Field("editorial") editorial:String ):Response<BookItemResponse>

    @FormUrlEncoded
    @PUT("/api/book/update/{id}")
    suspend fun updateBook(@Path("id") bookId:String, @Field("title") title:String, @Field("author") author:String, @Field("editorial") editorial:String ):Response<BookItemResponse>

    @DELETE("/api/book/delete/{id}")
    suspend fun deleteBook(@Path("id") bookId:String): Response<BookItemResponse>
}