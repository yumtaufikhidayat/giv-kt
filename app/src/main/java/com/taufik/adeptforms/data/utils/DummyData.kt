package com.taufik.adeptforms.data.utils

import com.taufik.adeptforms.R
import com.taufik.adeptforms.data.model.profile.Profile
import com.taufik.adeptforms.data.model.settings.Settings

object DummyData {

    fun getAllSettings() : List<Settings> {
        return mutableListOf(
            Settings(
                settingsImageIcon = R.drawable.ic_translate,
                settingsDesc = "Language",
                settingsInfo = "English"
            ),
            Settings(
                settingsImageIcon = R.drawable.ic_notification,
                settingsDesc = "Notifications",
                settingsInfo = "On"
            ),
            Settings(
                settingsImageIcon = R.drawable.ic_outline_privacy_policy,
                settingsDesc = "Privacy Policy",
                settingsInfo = ""
            ),
            Settings(
                settingsImageIcon = R.drawable.ic_outline_terms_of_service,
                settingsDesc = "Terms of Services",
                settingsInfo = ""
            ),
            Settings(
                settingsImageIcon = R.drawable.ic_outline_share,
                settingsDesc = "Share The App",
                settingsInfo = ""
            ),
            Settings(
                settingsImageIcon = R.drawable.ic_outline_star,
                settingsDesc = "Rate Us",
                settingsInfo = ""
            )
        )
    }

    fun getAllProfiles(): List<Profile> {
        return mutableListOf(
            Profile(
                profileTitle = "HRMLabs",
                profileDesc = "Adeptforms pulls your employment data"
            ),
            Profile(
                profileTitle = "Adept Cloud",
                profileDesc = "Adeptforms pull your files data and documents"
            )
        )
    }
}