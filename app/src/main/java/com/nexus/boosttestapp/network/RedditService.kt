package com.nexus.boosttestapp.network

import com.nexus.boosttestapp.network.response.RedditListResponse
import com.nexus.boosttestapp.network.response.UpdateVoteResponse
import retrofit2.Call
import retrofit2.http.*


interface RedditService {
    @GET("hot.json")
    fun getHot(@Query("limit") limit: Int): Call<RedditListResponse>


    @GET("hot.json")
    fun getAfterHot(
        @Query("after") after: String,
        @Query("limit") limit: Int
    ): Call<RedditListResponse>


    @FormUrlEncoded
    @POST("api/vote")
    fun updateVote(
        @Field("dir") voteType: String,
        @Field("id") id: String
    ): Call<UpdateVoteResponse>

}