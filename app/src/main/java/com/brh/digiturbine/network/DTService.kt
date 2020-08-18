package com.brh.digiturbine.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface DTService {

    //default values, can be changed at call time, otherwise the call is simple
    @GET("/getAds")
    suspend fun getItems(@Query("id") id:Int=236,
                         @Query("password") password: String="OVUJ1DJN",
                         @Query("siteId") siteId:Int=10777, @Query("deviceId") deviceId: Int=4230,
                         @Query("sessionId") sessionId:String="techtestsession",
                         @Query("totalCampaignsRequested") totalRequests: Int=10,
                         @Query("lname") name: String="Hargett"): ResponseBody
}