package jeevanS3340278.development.organdonation

import android.content.Context


object OrganDonorProfileData {

    fun putDonorState(context: Context, value: Boolean) {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putBoolean("DONOR_STATUS", value).apply()
    }

    fun getDonorState(context: Context): Boolean {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        return userLogin.getBoolean("DONOR_STATUS", false)
    }

    fun putDonorName(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("DONOR_NAME", value).apply()
    }

    fun getDonorName(context: Context): String {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        return userLogin.getString("DONOR_NAME", "")!!
    }

    fun putDonorMail(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("DONOR_MAIL", value).apply()
    }

    fun getDonorMail(context: Context): String {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        return userLogin.getString("DONOR_MAIL", "")!!
    }
}