package jeevanS3340278.development.organdonation

import android.content.Context


object DonorSP {

    fun persistLoginState(context: Context, value: Boolean) {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putBoolean("LOGIN_STATUS", value).apply()
    }

    fun fetchLoginState(context: Context): Boolean {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        return userLogin.getBoolean("LOGIN_STATUS", false)
    }

    fun persistUserName(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("USERNAME", value).apply()
    }

    fun fetchUserName(context: Context): String {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        return userLogin.getString("USERNAME", "")!!
    }

    fun persistUserMail(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("USERMAIL", value).apply()
    }

    fun fetchUserMail(context: Context): String {
        val userLogin = context.getSharedPreferences("ORGAN_DONATION", Context.MODE_PRIVATE)
        return userLogin.getString("USERMAIL", "")!!
    }
}