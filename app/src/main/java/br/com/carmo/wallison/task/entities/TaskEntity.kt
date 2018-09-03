package br.com.carmo.wallison.task.entities

class TaskEntity(val id: Int,
                 val userId: Int,
                 val priorityId: Int,
                 var description: String,
                 var dueDate: String,
                 var complete: Boolean)