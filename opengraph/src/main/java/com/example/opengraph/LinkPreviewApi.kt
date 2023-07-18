package com.example.opengraph

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

// Retrofit을 사용하기 위해 인터페이스를 정의합니다.
interface LinkPreviewApi {
    @GET
    suspend fun getLinkPreviewData(@Url url: String): LinkPreviewResponse
}

data class LinkPreviewResponse(
    @SerializedName("og:image")
    val image: String?,
    @SerializedName("og:title")
    val title: String?,
    @SerializedName("og:description")
    val description: String?
)

// Retrofit 인스턴스를 생성합니다.
val retrofit = Retrofit.Builder()
    .baseUrl("https://yourapi.com/") // 실제 API 엔드포인트에 맞게 변경해주세요.
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// Retrofit 인스턴스를 사용하여 LinkPreviewApi 인터페이스의 구현체를 생성합니다.
val linkPreviewApi = retrofit.create(LinkPreviewApi::class.java)

suspend fun fetchLinkPreview(url: String): LinkPreviewState? {
    try {
        // Retrofit을 사용하여 네트워크 요청을 수행합니다.
        val response = linkPreviewApi.getLinkPreviewData(url)
        // 응답 데이터를 LinkPreviewState 객체로 변환하여 반환합니다.
        return LinkPreviewState(
            image = response.image,
            title = response.title,
            description = response.description
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
