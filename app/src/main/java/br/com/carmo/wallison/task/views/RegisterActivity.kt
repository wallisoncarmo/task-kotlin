package br.com.carmo.wallison.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.carmo.wallison.task.R
import br.com.carmo.wallison.task.controller.UserController
import br.com.carmo.wallison.task.utils.ValidationException
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val userController: UserController by lazy {
        UserController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setListners()
    }

    private fun setListners() {
        buttonSave.setOnClickListener{
            handleSave()
        }
    }

    private fun handleSave() {
        try {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            userController.insert(name, email, password)

            moveToMain()

        } catch (e: ValidationException) {
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }catch (e:Exception){
            Toast.makeText(this,getString(R.string.erro_inesperado),Toast.LENGTH_LONG).show()
        }
    }

    private fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
