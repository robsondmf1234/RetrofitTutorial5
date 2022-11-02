package com.example.retrofittutorial.api

import com.example.retrofittutorial.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface SimpleApi {

    @GET("posts/1")
    suspend fun getPost(): Response<Post>

    //Testar Erro
//    @GET("postaaaas/1")
    //  suspend fun getPost(): Post

}