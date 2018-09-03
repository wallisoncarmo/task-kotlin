package br.com.carmo.wallison.task.controller

import android.content.Context
import br.com.carmo.wallison.task.entities.TaskEntity
import br.com.carmo.wallison.task.repository.TaskRepository

class TaskController(context: Context) {

    private val taskRepository: TaskRepository = TaskRepository.getInstance(context)

    private val mUserController: UserController by lazy {
        UserController(context)
    }

    fun findByUser(taskFilter: Int): MutableList<TaskEntity> {
        val userId = mUserController.getUserLoggerId()
        return taskRepository.findByUser(userId,taskFilter)
    }

    fun insert(taskEntity: TaskEntity) = taskRepository.insert(taskEntity)

}