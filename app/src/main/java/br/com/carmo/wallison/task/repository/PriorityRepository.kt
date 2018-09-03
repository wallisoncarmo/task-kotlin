package br.com.carmo.wallison.task.repository

import android.content.Context
import android.database.Cursor
import br.com.carmo.wallison.task.constants.DataBaseConstant
import br.com.carmo.wallison.task.entities.PriorityEntity

class PriorityRepository private constructor(context: Context) {

    private var taskDatabaseHelper: TaskDatabaseHelper = TaskDatabaseHelper(context)
    private val readDB = taskDatabaseHelper.readableDatabase

    companion object {

        fun getInstance(context: Context): PriorityRepository {
            if (INSTANCE == null) {
                INSTANCE = PriorityRepository(context)
            }

            return INSTANCE as PriorityRepository
        }
        private var INSTANCE: PriorityRepository? = null

    }

    fun findAll(): MutableList<PriorityEntity> {
        var list = mutableListOf<PriorityEntity>()

        try {
            val cursor: Cursor = queryFindAll()
            list = setListPriority(cursor)

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }

    private fun queryFindAll() =
            readDB.rawQuery("SELECT * FROM ${DataBaseConstant.PRIORITY.TABLE_NAME}", null)

    private fun setListPriority(cursor: Cursor):MutableList<PriorityEntity>{

        val list = mutableListOf<PriorityEntity>()
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val priority: PriorityEntity = setPriorityEntity(cursor)
                list.add(priority)
            }
        }

        return list
    }

    private fun setPriorityEntity(cursor: Cursor): PriorityEntity {
        val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstant.PRIORITY.COLUMNS.ID))
        val description = cursor.getString(cursor.getColumnIndex(DataBaseConstant.PRIORITY.COLUMNS.DESCRIPTION))
        return PriorityEntity(id, description)
    }
}