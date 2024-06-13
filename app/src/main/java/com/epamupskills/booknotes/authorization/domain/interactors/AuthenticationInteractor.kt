package com.epamupskills.booknotes.authorization.domain.interactors

import com.epamupskills.booknotes.authorization.domain.models.UserCredentials
import com.epamupskills.booknotes.authorization.domain.usecases.SignInUseCase
import com.epamupskills.booknotes.authorization.domain.usecases.SignUpUseCase
import javax.inject.Inject

class AuthenticationInteractor @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
) {

    suspend fun signIn(userCredentials: UserCredentials) = signInUseCase.invoke(userCredentials)

    suspend fun signUp(userCredentials: UserCredentials) = signUpUseCase.invoke(userCredentials)
}