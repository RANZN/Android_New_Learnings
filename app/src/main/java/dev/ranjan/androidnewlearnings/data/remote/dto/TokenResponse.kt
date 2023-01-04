package dev.ranjan.androidnewlearnings.data.remote.dto

data class TokenResponse(
	val accessToken: String? = null,
	val scope: String? = null,
	val tokenType: String? = null,
	val expiresIn: Int? = null
)

