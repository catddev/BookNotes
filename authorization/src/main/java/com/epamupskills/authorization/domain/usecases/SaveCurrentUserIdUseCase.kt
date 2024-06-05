package com.epamupskills.authorization.domain.usecases

import com.epamupskills.authorization.domain.AccountRepository
import com.epamupskills.core.repository.UidRepository
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