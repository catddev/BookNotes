package com.epamupskills.authorization.domain.usecases

import com.epamupskills.authorization.domain.AccountRepository
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