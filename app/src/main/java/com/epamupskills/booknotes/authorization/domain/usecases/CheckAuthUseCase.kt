package com.epamupskills.booknotes.authorization.domain.usecases

import com.epamupskills.booknotes.authorization.domain.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val repository: AccountRepository,
) {

    operator fun invoke(): Result<Flow<Boolean>> = try {
        Result.success(repository.isAuth)
    } catch (t: Throwable) {
        Result.failure(t)
    }
}