package ua.ck.zabochen.feedreader.data.network.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ua.ck.zabochen.feedreader.data.network.response.playlist.YoutubePlaylistVideoResponse
import ua.ck.zabochen.feedreader.data.network.interceptor.connection.ConnectionStateInterceptor
import ua.ck.zabochen.feedreader.internal.YOUTUBE_API_BASE_URL
import ua.ck.zabochen.feedreader.internal.YOUTUBE_API_KEY

// Request example
// https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId={ID}&key={KEY}
// https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=PLWz5rJ2EKKc9mxIBd0DRw9gwXuQshgmn2&key=AIzaSyDmgBcO6A1NSTFEAOyNTmOPTLD1SJ1d89g

interface YoutubeApiService {

    @GET("playlistItems")
    fun getYoutubePlaylistVideoAsync(
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 50,
        @Query("playlistId") playlistId: String
    ): Deferred<YoutubePlaylistVideoResponse>

    companion object {
        operator fun invoke(connectionStateInterceptor: ConnectionStateInterceptor): YoutubeApiService {

            val requestInterceptor = Interceptor { chain ->

                // Add Youtube API Key
                val updateUrl: HttpUrl = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", YOUTUBE_API_KEY)
                    .build()

                val updateRequest: Request = chain.request()
                    .newBuilder()
                    .url(updateUrl)
                    .build()

                return@Interceptor chain.proceed(updateRequest)
            }

            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectionStateInterceptor)
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(YOUTUBE_API_BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(YoutubeApiService::class.java)
        }
    }
}