package com.epamupskills.booknotes.authorization.domain.usecases

import com.epamupskills.booknotes.authorization.domain.AccountRepository
import javax.inject.Inject

class GetUserEmailUseCase @Inject constructor(
    private val repository: AccountRepository,
) {

    operator fun invoke(): Result<String> = try {
        Result.success(repository.getUserEmail())
    } catch (t: Throwable) {
        Result.failure(t)
    }
}