package com.epamupskills.authorization.domain.usecases

import com.epamupskills.authorization.domain.AccountRepository
import com.epamupskills.core.repository.UidRepository
import com.epamupskills.core.repository.UserCacheRepository
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