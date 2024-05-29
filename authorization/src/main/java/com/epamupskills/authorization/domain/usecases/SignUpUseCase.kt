package com.epamupskills.authorization.domain.usecases

import com.epamupskills.authorization.domain.AccountRepository
import com.epamupskills.authorization.domain.models.UserCredentials
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend operator fun invoke(userCredentials: UserCredentials): Result<Unit> = try {
        Result.success(repository.signUp(userCredentials))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}