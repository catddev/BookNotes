package com.epamupskills.booknotes.authorization.domain.usecases

import com.epamupskills.booknotes.authorization.domain.AccountRepository
import com.epamupskills.booknotes.core.UidRepository
import javax.inject.Inject

class SaveCurrentUserIdUseCase @Inject constructor(
    private val uidRepository: UidRepository,
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke(): Result<Unit> = try {
        val uid = accountRepository.getUserId()
        Result.success(uidRepository.saveUserId(uid))
    } catch (t: Throwable) {
        Result.failure(t)
    }
}