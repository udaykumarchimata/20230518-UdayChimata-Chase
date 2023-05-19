package com.example.weather.api


import com.example.weather.response.City
import com.example.weather.response.Weather
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("data/2.5/weather")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit: String = "metric"
    ): Weather

    @GET("geo/1.0/direct")
    suspend fun getLocationCoordinates(
        @Query("q") city: String, @Query("limit") limit: Int = 5
    ): List<City>

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"

        fun create(): Service {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client =
                OkHttpClient.Builder().addInterceptor(logger).addInterceptor(Interceptor { chain ->
                    val request = chain.request()
                    val url = chain.request().url.newBuilder()
                        .addQueryParameter("appid", "c3b258b0355cb9782702b1480846d3f2").build()
                    chain.proceed(request.newBuilder().url(url).build())
                }).build()

            return Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(Service::class.java)
        }
    }
}