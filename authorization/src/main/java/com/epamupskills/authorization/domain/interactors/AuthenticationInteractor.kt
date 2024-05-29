package com.epamupskills.authorization.domain.interactors

import com.epamupskills.authorization.domain.models.UserCredentials
import com.epamupskills.authorization.domain.usecases.SignInUseCase
import com.epamupskills.authorization.domain.usecases.SignUpUseCase
import javax.inject.Inject

class AuthenticationInteractor @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
) {

    suspend fun signIn(userCredentials: UserCredentials) = signInUseCase.invoke(userCredentials)

    suspend fun signUp(userCredentials: UserCredentials) = signUpUseCase.invoke(userCredentials)
}