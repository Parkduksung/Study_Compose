package utils

import com.example.practice1.data.remote.dto.*

object MockUtil {

    fun mockCoinDto() = CoinDto(
        id = "1",
        isActive = true,
        isNew = false,
        name = "Bitcoin (BTC)",
        rank = 1,
        symbol = "",
        type = ""
    )

    fun mockCoinDtoList() = listOf(mockCoinDto())

    fun mockCoinDetailDto() = CoinDetailDto(
        description = "",
        developmentStatus = "",
        firstDataAt = "",
        hardwareWallet = false,
        hashAlgorithm = "",
        id = "",
        isActive = false,
        isNew = false,
        lastDataAt = "",
        links = Links(emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()),
        linksExtended = listOf(LinksExtended(Stats(0, 0, 0, 0), "", "")),
        message = "",
        name = "",
        openSource = false,
        orgStructure = "",
        proofType = "",
        rank = 1,
        startedAt = "",
        symbol = "",
        tags = listOf(Tag(0, 0, "", "")),
        team = listOf(TeamMember("","","")),
        type = "",
        whitepaper = Whitepaper("", "")
    )
}