package br.com.carmo.wallison.task.controller

import android.content.Context
import br.com.carmo.wallison.task.entities.PriorityEntity
import br.com.carmo.wallison.task.repository.PriorityRepository

class PriorityController(context: Context) {

    private val priorityRepository: PriorityRepository = PriorityRepository.getInstance(context)

    fun getList(): MutableList<PriorityEntity> = priorityRepository.findAll()

}