package com.epamupskills.booknotes.authorization.domain.usecases

import com.epamupskills.booknotes.authorization.domain.AccountRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend operator fun invoke(): Result<Unit> = try {
        Result.success(repository.signOut())
    } catch (t: Throwable) {
        Result.failure(t)
    }
}