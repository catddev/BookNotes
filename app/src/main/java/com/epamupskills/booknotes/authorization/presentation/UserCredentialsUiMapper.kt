package com.epamupskills.booknotes.authorization.presentation

import com.epamupskills.booknotes.authorization.domain.models.UserCredentials
import com.epamupskills.booknotes.authorization.presentation.models.UserCredentialsUi
import com.epamupskills.booknotes.core.base.BaseMapper
import javax.inject.Inject

class UserCredentialsUiMapper @Inject constructor() :
    BaseMapper<UserCredentialsUi, UserCredentials>() {

    override fun transform(input: UserCredentialsUi): UserCredentials =
        UserCredentials(
            email = input.email,
            password = input.password,
        )
}