package com.epamupskills.booknotes.authorization.domain.usecases

import com.epamupskills.booknotes.authorization.domain.AccountRepository
import com.epamupskills.booknotes.booknotes.domain.BooksRepository
import com.epamupskills.booknotes.core.abstraction.UidRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val booksRepository: BooksRepository,
    private val uidRepository: UidRepository,
) {

    suspend operator fun invoke(): Result<Unit> = try {
        coroutineScope {
            val result = async { accountRepository.deleteAccount() }.run {
                await()
                booksRepository.clearCachedUserData(uidRepository.getUserId())
            }
            Result.success(result)
        }
    } catch (t: Throwable) {
        Result.failure(t)
    }
}