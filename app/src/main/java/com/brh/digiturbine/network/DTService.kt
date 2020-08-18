package com.brh.digiturbine.network

import com.brh.digiturbine.model.AdHolder
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface DTService {

    @GET("/getAds")
    suspend fun getItems(@Query("id") id:Int=236,
                         @Query("password") password: String="OVUJ1DJN",
                         @Query("siteId") siteId:Int=10777, @Query("deviceId") deviceId: Int=4230,
                         @Query("sessionId") sessionId:String="techtestsession",
                         @Query("totalCampaignsRequested") totalRequests: Int=10,
                         @Query("lname") name: String="Hargett"): ResponseBody
}