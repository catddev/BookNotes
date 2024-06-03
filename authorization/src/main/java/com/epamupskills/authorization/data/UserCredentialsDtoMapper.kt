package com.epamupskills.authorization.data

import com.epamupskills.authorization.data.models.UserCredentialsDto
import com.epamupskills.authorization.domain.models.UserCredentials
import com.epamupskills.core.base.BaseMapper
import javax.inject.Inject

class UserCredentialsDtoMapper @Inject constructor() :
    BaseMapper<UserCredentialsDto, UserCredentials>() {

    override fun mapFrom(input: UserCredentialsDto): UserCredentials = UserCredentials(
        email = input.email,
        password = input.password,
    )

    override fun mapTo(input: UserCredentials): UserCredentialsDto = UserCredentialsDto(
        email = input.email,
        password = input.password,
    )
}