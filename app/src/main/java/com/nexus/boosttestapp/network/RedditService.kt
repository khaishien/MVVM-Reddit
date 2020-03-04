package com.nexus.boosttestapp.network

import com.nexus.boosttestapp.network.response.SubredditListResponse
import com.nexus.boosttestapp.network.response.UpdateVoteResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface RedditService {
    @GET("hot.json")
    fun getSubredditList(): Call<SubredditListResponse>


    @FormUrlEncoded
    @POST("api/vote")
    fun updateVote(
        @Field("dir") voteType: String,
        @Field("id") id: String
    ): Call<UpdateVoteResponse>

}