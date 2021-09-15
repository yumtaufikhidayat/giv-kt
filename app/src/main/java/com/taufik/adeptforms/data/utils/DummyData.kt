package com.taufik.adeptforms.data.utils

import com.taufik.adeptforms.R
import com.taufik.adeptforms.data.model.home.HomeAllCategory
import com.taufik.adeptforms.data.model.home.HomeChildCategory
import com.taufik.adeptforms.data.model.profile.Profile
import com.taufik.adeptforms.data.model.settings.Settings

object DummyData {

    fun getHomeAllCategory(): MutableList<HomeAllCategory> {
        return mutableListOf(
            HomeAllCategory("", getHomeChildHorizontal1(), getHomeChildVertical1()),
            HomeAllCategory("Features", getHomeChildHorizontal2(), getHomeChildVertical2())
        )
    }

    private fun getHomeChildHorizontal1(): MutableList<HomeChildCategory> {
        return mutableListOf(
            HomeChildCategory(1, R.drawable.ic_documents, "Documents", ""),
            HomeChildCategory(2, R.drawable.ic_template, "Templates", ""),
            HomeChildCategory(3, R.drawable.ic_scan, "Scan Documents", "")
        )
    }

    private fun getHomeChildHorizontal2(): MutableList<HomeChildCategory> {
        return mutableListOf(
            HomeChildCategory(4, R.drawable.ic_clocking, "Clocking Reports", ""),
            HomeChildCategory(5, R.drawable.ic_incident, "Incident Reports", ""),
            HomeChildCategory(6, R.drawable.ic_sos, "Scan SOS Reports", "")
        )
    }

    private fun getHomeChildVertical1(): MutableList<HomeChildCategory> {
        return mutableListOf(
            HomeChildCategory(
                1,
                R.drawable.ic_workflow,
                "Workflow List",
                "You have 1 submission left to submit"
            ),
            HomeChildCategory(
                2,
                R.drawable.ic_attendance,
                "Attendance",
                "Check attendance and clock in - out"
            )
        )
    }

    private fun getHomeChildVertical2(): MutableList<HomeChildCategory> {
        return mutableListOf(
            HomeChildCategory(
                3,
                R.drawable.ic_report,
                "Reports History",
                "40 Saved and Submitted Reports"
            ),
            HomeChildCategory(
                4,
                R.drawable.ic_personel,
                "Personnel Managements",
                "32 Active Personnel"
            )
        )
    }

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