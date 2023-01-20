package dev.ranjan.androidnewlearnings

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class DatabaseTest {

    private lateinit var database: Database

    private val emptyString = ""
    private val validEmail = "ranjan123@gmail.com"
    private val invalidEmail = "ranjan"
    private val correctEmail = "ranjan@gmail.com"
    private val password = "Pass@123"
    private val wrongPassword = "abc"

    @Before
    fun beforeTest() {
        database = Database()
    }

    @Test
    fun `both email and password empty return LoginStatus EnterDetails `() {
        val result = database.login(emptyString, emptyString)
        assertThat(result, `is`(LoginStatus.ENTER_DETAILS))
    }

    @Test
    fun `empty email return invalid email`() {
        val result = database.login(invalidEmail, "pass")
        assertThat(result, `is`(LoginStatus.INVALID_EMAIL))
    }

    @Test
    fun `wrong email return invalid email`() {
        val result = database.login(validEmail, "pass")
        assertThat(result, `is`(LoginStatus.USER_NOT_FOUND))
    }

    @Test
    fun `validEmail with wrong password return wrong password`() {
        val result = database.login(correctEmail, wrongPassword)
        assertThat(result, `is`(LoginStatus.WRONG_PASSWORD))
    }
    @Test
    fun `valid email with no password return enter_password`() {
        val result = database.login(validEmail, emptyString)
        assertThat(result, `is`(LoginStatus.ENTER_PASSWORD))
    }
    @Test
    fun `valid email and valid password return success`() {
        val result = database.login(correctEmail, password)
        assertThat(result, `is`(LoginStatus.ENTER_DETAILS))
    }
}