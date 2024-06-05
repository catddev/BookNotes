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

    suspend operator fun invoke(): Result<Unit> = try { //todo correct?
        coroutineScope {
            val result = async { accountRepository.deleteAccount() }
            async { cacheRepository.clearCachedUserData(uidRepository.getUserId()) }.await() //todo need wait or not?
//            async { uidRepository.clearUserId() }.await()
            Result.success(result.await())
        }
    } catch (t: Throwable) {
        Result.failure(t)
    }
}