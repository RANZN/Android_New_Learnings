package dev.ranjan.socialmedia.data

data class Response(
	val response: List<ResponseItem?>? = null
)

data class FieldSetItem(
	val header: String? = null,
	val fields: List<FieldsItem?>? = null
)

data class FieldsItem(
	val regex: String? = null,
	val fieldName: String? = null,
	val enable: Boolean? = null,
	val length: Int? = null,
	val placeholder: Any? = null,
	val fieldValue: Any? = null,
	val fieldType: String? = null,
	val required: Boolean? = null,
	val fieldId: String? = null,
	val errorMsg: String? = null,
	val dependentValidations: DependentValidationsItem? = null
)

data class DependentValidationsItem(
	val fieldRef: String? = null,
	val type: String? = null,
	val fieldType: String? = null,
	val fieldId: String? = null,
	val errorMsg: String? = null
)

data class ResponseItem(
	val fieldSet: List<FieldSetItem?>? = null
)

