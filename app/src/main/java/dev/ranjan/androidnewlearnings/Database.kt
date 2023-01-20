package dev.ranjan.androidnewlearnings

import java.util.regex.Pattern

class Database {
    private val userList = arrayListOf(
        User("ranjan@gmail.com", "Pass@123"), User("prakash@gmail.com", "Ranjan@123")
    )

    fun login(email: String?, password: String?): LoginStatus {
        val pattern: Regex =
            Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
                .toRegex()
        return if (email.isNullOrBlank() && password.isNullOrBlank()) LoginStatus.ENTER_DETAILS
        else if (password.isNullOrBlank()) return LoginStatus.ENTER_PASSWORD
        else if (email?.matches(pattern) == false) return LoginStatus.INVALID_EMAIL
        else if (!email.isNullOrBlank() && userList.contains(
                User(
                    email, password
                )
            )
        ) LoginStatus.SUCCESS
        else if (userList.find { it.email == email } != null) LoginStatus.WRONG_PASSWORD
        else LoginStatus.USER_NOT_FOUND
    }
}

enum class LoginStatus(val value: String) {
    SUCCESS("Login Successful"),
    WRONG_PASSWORD("Password Wrong"),
    USER_NOT_FOUND("Email not found"),
    ENTER_DETAILS("Required Fields"),
    ENTER_PASSWORD("Please enter Password"),
    INVALID_EMAIL("Invalid Email")
}

data class User(val email: String, val password: String)
