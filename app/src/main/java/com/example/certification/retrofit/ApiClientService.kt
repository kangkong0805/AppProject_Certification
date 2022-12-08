package com.example.certification.retrofit

import com.example.certification.jungchugi.data.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClientService {
    @GET("getQualExamSchdList?serviceKey=UpXTrusNmgvpNNj1K2Xs8J%2FT6VWidYmInu73tyHyy3BMIuB27A5UX69o4srql4cuNqcLSXXh6fiF%2FUV390U2xw%3D%3D")
    fun getParkInfo(
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("dataFormat") dataFormat: String,
        @Query("implYy") implYy: Int,
        @Query("qualgbCd") qualgbCd: String,
        @Query("jmCd") jmCd: Int
    ): Call<Result>
}