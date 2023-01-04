package dev.ranjan.androidnewlearnings.data.remote.dto

data class AssetTicker(
	val content: List<ContentItem?>? = null
)

data class ContentItem(
	val volume: Any? = null,
	val marketCap: Any? = null,
	val volumeRank: Int? = null,
	val assetId: String? = null,
	val marketCapRank: Int? = null,
	val price: Any? = null,
	val totalSupply: Int? = null,
	val totalMarketCap: Any? = null,
	val freeFloatSupply: Int? = null,
	val id: String? = null,
	val timestamp: String? = null
)

