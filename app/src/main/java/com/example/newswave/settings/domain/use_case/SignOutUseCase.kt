package com.example.newswave.settings.domain.use_case

import com.example.newswave.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke() = settingsRepository.signOut()
}