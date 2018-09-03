package br.com.carmo.wallison.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.carmo.wallison.task.R
import br.com.carmo.wallison.task.controller.UserController
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val userController: UserController by lazy {
        UserController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setListeners()
        verifyLoggerUser()
    }

    private fun verifyLoggerUser() {
        val auth = userController.verifyLoggerUser()
        if (auth) {
            moveToMain()
        }
    }

    private fun setListeners() {
        buttonEntrar.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        val auth = userController.login(email, password)

        if(!auth){
            Toast.makeText(this,R.string.usuario_incorreto,Toast.LENGTH_LONG).show()
            return
        }
        moveToMain()

    }

    private fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
