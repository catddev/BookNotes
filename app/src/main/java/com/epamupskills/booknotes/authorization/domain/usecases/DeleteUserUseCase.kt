package com.epamupskills.booknotes.authorization.domain.usecases

import com.epamupskills.booknotes.authorization.domain.AccountRepository
import com.epamupskills.booknotes.core.UidRepository
import com.epamupskills.booknotes.core.UserCacheRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val cacheRepository: UserCacheRepository,
    private val uidRepository: UidRepository,
) {

    suspend operator fun invoke(): Result<Unit> = try {
        coroutineScope {
            val result = async { accountRepository.deleteAccount() }.run {
                await()
                cacheRepository.clearCachedUserData(uidRepository.getUserId())
            }
            Result.success(result)
        }
    } catch (t: Throwable) {
        Result.failure(t)
    }
}