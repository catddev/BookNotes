package com.epamupskills.booknotes

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SignInFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_initial_login_screen_ui() {
        onView(withId(R.id.sign_in_screen)).check(matches(isDisplayed()))

        onView(withId(R.id.sign_in_button)).check(matches(withText(R.string.sign_in_button_title)))

        onView(withId(R.id.sign_in_button)).check(matches(not(isEnabled())))

        onView(withContentDescription(R.string.auth_email_hint)).check(
            matches(withHint(R.string.auth_email_hint))
        )

        onView(withContentDescription(R.string.auth_password_hint)).check(
            matches(withHint(R.string.auth_password_hint))
        )
    }

    @Test
    fun after_invalid_input_login_button_should_stay_disabled() {
        onView(withContentDescription(R.string.auth_email_hint)).perform(
            typeText("user@gmail."),
            closeSoftKeyboard()
        )

        onView(withContentDescription(R.string.auth_password_hint)).perform(
            typeText("password"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.sign_in_button)).check(matches(not(isEnabled())))
    }

    @Test
    fun after_valid_input_login_button_should_become_enabled() {
        onView(withContentDescription(R.string.auth_email_hint)).perform(
            typeText("user@gmail.com"),
            closeSoftKeyboard()
        )

        onView(withContentDescription(R.string.auth_password_hint)).perform(
            typeText("Password123"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.sign_in_button)).check(matches(isEnabled()))
    }

    @Test
    fun after_signup_button_clicked_should_navigate_to_signup_screen() {
        onView(withId(R.id.sign_up_button)).perform(click())
        onView(withId(R.id.sign_up_screen)).check(matches(isDisplayed()))
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun ensureLoggedOut() {
            try {
                onView(withId(com.epamupskills.booknotes.R.id.profile)).perform(click())
                onView(withId(R.id.sign_out_button)).perform(click())
                onView(withText(R.string.confirm_button_title)).perform(click())
            } catch (t: Throwable) {
                println(t.message)
            }
        }
    }
}