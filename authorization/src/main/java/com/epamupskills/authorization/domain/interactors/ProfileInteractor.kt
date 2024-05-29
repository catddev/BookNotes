package com.epamupskills.authorization.domain.interactors

import com.epamupskills.authorization.domain.usecases.DeleteUserUseCase
import com.epamupskills.authorization.domain.usecases.GetUserEmailUseCase
import com.epamupskills.authorization.domain.usecases.SignOutUseCase
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val signOutUseCase: SignOutUseCase,
) {

    fun getUserEmail(): Result<String> = getUserEmailUseCase.invoke()
    suspend fun deleteAccount(): Result<Unit> = deleteUserUseCase.invoke()
    suspend fun signOut(): Result<Unit> = signOutUseCase.invoke()
}