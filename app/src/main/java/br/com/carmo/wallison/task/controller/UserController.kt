package br.com.carmo.wallison.task.controller

import android.content.Context
import android.util.Log
import br.com.carmo.wallison.task.R
import br.com.carmo.wallison.task.constants.TaskConstants
import br.com.carmo.wallison.task.entities.UserEntity
import br.com.carmo.wallison.task.repository.UserRepository
import br.com.carmo.wallison.task.utils.SecurityPreferences
import br.com.carmo.wallison.task.utils.ValidationException
import kotlin.math.log

class UserController(private val context: Context) {

    private val userRepository: UserRepository = UserRepository.getInstance(context)
    private val securityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun login(email: String, password: String): Boolean {

        val user: UserEntity? = userRepository.login(email, password)

        if (user != null) {
            saveSharedPreferences(user.id, user.name, user.email)
            return true
        }

        return false
    }

    fun logout() {
        removeSharedPreferences()
    }

    fun verifyLoggerUser(): Boolean {
        val userId = securityPreferences.getStrorage(TaskConstants.KEY.USER_ID)

        if (userId != "") {
            try {
                userRepository.findById(userId.toInt())
                removeSharedPreferences()
                return true
            } catch (e: Exception) {
                Log.d("Erro", e.message)
            }

        }
        return false
    }

    fun insert(name: String, email: String, password: String) {
        try {
            validFieldEmpty(name, email, password)
            validEmailUnique(email)

            val userId = userRepository.insert(name, email, password)

            saveSharedPreferences(userId, name, email)
        } catch (e: Exception) {
            throw e
        }
    }

    fun getUserLoggerId(): Int {
        return securityPreferences.getStrorage(TaskConstants.KEY.USER_ID).toInt()
    }

    private fun validEmailUnique(email: String) {
        if (userRepository.isEmailExistent(email)) {
            throw ValidationException(context.getString(R.string.email_em_uso))
        }
    }

    private fun validFieldEmpty(name: String, email: String, password: String) {
        if (name == "" || email == "" || password == "") {
            throw ValidationException(context.getString(R.string.informe_todos_campos))
        }
    }

    private fun saveSharedPreferences(userId: Int, name: String, email: String) {
        securityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
        securityPreferences.storeString(TaskConstants.KEY.NAME, name)
        securityPreferences.storeString(TaskConstants.KEY.EMAIL, email)
    }

    private fun removeSharedPreferences() {
        securityPreferences.storeString(TaskConstants.KEY.USER_ID, "")
        securityPreferences.storeString(TaskConstants.KEY.NAME, "")
        securityPreferences.storeString(TaskConstants.KEY.EMAIL, "")
    }


}