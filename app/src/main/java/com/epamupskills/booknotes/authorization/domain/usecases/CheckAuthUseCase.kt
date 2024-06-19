package com.epamupskills.booknotes.authorization.domain.usecases

import com.epamupskills.booknotes.authorization.domain.AccountRepository
import com.epamupskills.booknotes.core.abstraction.UidRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val uidRepository: UidRepository,
    private val accountRepository: AccountRepository,
) {

    operator fun invoke(): Result<Flow<Boolean>> = try {
        val result = accountRepository.isAuth
            .distinctUntilChanged()
            .onEach {
                uidRepository.saveUserId(accountRepository.getUserId())
            }
        Result.success(result)
    } catch (t: Throwable) {
        Result.failure(t)
    }
}