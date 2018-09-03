package br.com.carmo.wallison.task.views

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.carmo.wallison.task.R
import br.com.carmo.wallison.task.controller.PriorityController
import br.com.carmo.wallison.task.controller.TaskController
import br.com.carmo.wallison.task.controller.UserController
import br.com.carmo.wallison.task.entities.PriorityEntity
import br.com.carmo.wallison.task.entities.TaskEntity
import br.com.carmo.wallison.task.extension.formatPT
import br.com.carmo.wallison.task.utils.SecurityPreferences
import kotlinx.android.synthetic.main.activity_task_form.*
import java.util.*

class TaskFormActivity : AppCompatActivity() {

    private val priorityController: PriorityController by lazy {
        PriorityController(this)
    }

    private val taskController: TaskController by lazy {
        TaskController(this)
    }

    private val userController: UserController by lazy {
        UserController(this)
    }

    private var mListPrioritiesEntity: MutableList<PriorityEntity> = mutableListOf()
    private var mListPrioritiesId: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        setListeners()

        loadPriorities()

    }

    private fun setListeners() {
        buttonSave.setOnClickListener {
            handleSave()
        }

        buttonDate.setOnClickListener {
            openDatePickerDialog()
        }
    }

    private fun openDatePickerDialog() {

        val current = Calendar.getInstance()

        val year = current.get(Calendar.YEAR)
        val month = current.get(Calendar.MONTH)
        val day = current.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    buttonDate.text = calendar.formatPT()

                }, year, month, day)
                .show()
    }


    private fun handleSave() {
        try {

            val descriptor = editDescription.text.toString()
            val keySelected = spinnerPriority.selectedItemPosition
            val priorityId = mListPrioritiesId[keySelected]
            val complete = checkComplete.isChecked
            var dueDate = buttonDate.text.toString()
            var userId = userController.getUserLoggerId()

            val taskEntity = TaskEntity(0,
                    userId = userId,
                    complete = complete,
                    description = descriptor,
                    dueDate = dueDate,
                    priorityId = priorityId)

            taskController.insert(taskEntity)

            Toast.makeText(this, getString(R.string.tarefa_inserida), Toast.LENGTH_LONG).show()
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.erro_inesperado), Toast.LENGTH_LONG).show()
        }
    }

    private fun loadPriorities() {
        mListPrioritiesEntity = priorityController.getList()
        val listPriorities = mListPrioritiesEntity.map { it.description }
        mListPrioritiesId = mListPrioritiesEntity.map { it.id }.toMutableList()

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listPriorities)
        spinnerPriority.adapter = adapter
    }


}
