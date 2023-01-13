package dev.ranjan.androidnewlearnings.data

import com.google.gson.annotations.SerializedName

data class ResponseItem(

	@field:SerializedName("NewRecovered")
	val newRecovered: Double? = null,

	@field:SerializedName("Infection_Risk")
	val infectionRisk: Double? = null,

	@field:SerializedName("TotCases_1M_Pop")
	val totCases1MPop: Any? = null,

	@field:SerializedName("one_Testevery_X_ppl")
	val oneTesteveryXPpl: Double? = null,

	@field:SerializedName("TotalCases")
	val totalCases: Double? = null,

	@field:SerializedName("Tests_1M_Pop")
	val tests1MPop: Double? = null,

	@field:SerializedName("Deaths_1M_pop")
	val deaths1MPop: Any? = null,

	@field:SerializedName("ActiveCases")
	val activeCases: Double? = null,

	@field:SerializedName("one_Caseevery_X_ppl")
	val oneCaseeveryXPpl: Double? = null,

	@field:SerializedName("Recovery_Proporation")
	val recoveryProporation: Any? = null,

	@field:SerializedName("Case_Fatality_Rate")
	val caseFatalityRate: Any? = null,

	@field:SerializedName("Serious_Critical")
	val seriousCritical: Double? = null,

	@field:SerializedName("rank")
	val rank: Double? = null,

	@field:SerializedName("Population")
	val population: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("TotalDeaths")
	val totalDeaths: Double? = null,

	@field:SerializedName("NewDeaths")
	val newDeaths: Double? = null,

	@field:SerializedName("TotalRecovered")
	val totalRecovered: String? = null,

	@field:SerializedName("one_Deathevery_X_ppl")
	val oneDeatheveryXPpl: Double? = null,

	@field:SerializedName("TwoLetterSymbol")
	val twoLetterSymbol: Any? = null,

	@field:SerializedName("Continent")
	val continent: String? = null,

	@field:SerializedName("Country")
	val country: String? = null,

	@field:SerializedName("TotalTests")
	val totalTests: String? = null,

	@field:SerializedName("NewCases")
	val newCases: Double? = null,

	@field:SerializedName("ThreeLetterSymbol")
	val threeLetterSymbol: Any? = null,

	@field:SerializedName("Test_Percentage")
	val testPercentage: Double? = null
)
