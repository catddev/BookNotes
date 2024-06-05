package com.epamupskills.authorization.presentation

import com.epamupskills.authorization.domain.models.UserCredentials
import com.epamupskills.authorization.presentation.models.UserCredentialsUi
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class UserCredentialsUiMapper @Inject constructor() :
    BaseMapper<UserCredentialsUi, UserCredentials>() {

    override fun transform(input: UserCredentialsUi): UserCredentials = UserCredentials(
        email = input.email,
        password = input.password,
    )
}