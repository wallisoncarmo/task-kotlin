package br.com.carmo.wallison.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import br.com.carmo.wallison.task.constants.DataBaseConstant
import br.com.carmo.wallison.task.entities.TaskEntity

class TaskRepository private constructor(context: Context) {

    private var taskDatabaseHelper: TaskDatabaseHelper = TaskDatabaseHelper(context)
    private val writeDB = taskDatabaseHelper.writableDatabase
    private val readDB = taskDatabaseHelper.readableDatabase

    companion object {
        fun getInstance(context: Context): TaskRepository {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }

            return INSTANCE as TaskRepository
        }

        private var INSTANCE: TaskRepository? = null

    }

    fun findByUser(userId: Int, taskFilter: Int): MutableList<TaskEntity> {
        var list = mutableListOf<TaskEntity>()

        try {

            val projection = getFindByUserSelection
            val selection = getFindByUserProjection
            val selectionArgs = getFindByUserSelectionArgs(userId,taskFilter)

            val cursor = find(projection, selection, selectionArgs)

            list = setListTask(cursor)

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }

    fun findById(id: Int): TaskEntity? {

        val task: TaskEntity? = null

        val projection = getFindByUserSelection
        val where = getFindByIdWhere()
        val whereArgs = getFindByIdWhereArgs(id)

        val cursor = find(projection, where, whereArgs)
        cursor.moveToLast()

        return setTaskEntity(cursor)
    }

    fun insert(task: TaskEntity): Int {
        try {
            val insertValues = setContentValues(task)
            return writeDB.insert(DataBaseConstant.TASK.TABLE_NAME, null, insertValues).toInt()
        } catch (e: Exception) {
            throw e
        }
    }

    fun update(task: TaskEntity) {
        try {
            val insertValues = setContentValues(task)
            insertValues.put(DataBaseConstant.TASK.COLUMNS.ID, task.id)
            val where = getFindByIdWhere()
            val whereArgs = getFindByIdWhereArgs(task.id)

            writeDB.update(DataBaseConstant.TASK.TABLE_NAME, insertValues, where, whereArgs)
        } catch (e: Exception) {
            throw e
        }
    }

    fun delete(id: Int) {
        try {
            val where = getFindByIdWhere()
            val whereArgs = getFindByIdWhereArgs(id)
            writeDB.delete(DataBaseConstant.TASK.TABLE_NAME, where, whereArgs)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun getFindByIdWhereArgs(id: Int) =
            arrayOf(id.toString())

    private fun getFindByIdWhere() = "${DataBaseConstant.TASK.COLUMNS.ID} = ? "


    private fun setContentValues(task: TaskEntity): ContentValues {
        val complete = if (task.complete == true) 1 else 0

        val insertValues = ContentValues()
        insertValues.put(DataBaseConstant.TASK.COLUMNS.USER_ID, task.userId)
        insertValues.put(DataBaseConstant.TASK.COLUMNS.PRIORITY_ID, task.priorityId)
        insertValues.put(DataBaseConstant.TASK.COLUMNS.DESCRIPTION, task.description)
        insertValues.put(DataBaseConstant.TASK.COLUMNS.COMPLETE, complete)
        insertValues.put(DataBaseConstant.TASK.COLUMNS.DUE_DATE, task.dueDate)

        return insertValues
    }

    private val getFindByUserSelection = arrayOf(DataBaseConstant.TASK.COLUMNS.ID,
            DataBaseConstant.TASK.COLUMNS.USER_ID,
            DataBaseConstant.TASK.COLUMNS.PRIORITY_ID,
            DataBaseConstant.TASK.COLUMNS.COMPLETE,
            DataBaseConstant.TASK.COLUMNS.DUE_DATE,
            DataBaseConstant.TASK.COLUMNS.DESCRIPTION
    )

    private val getFindByUserProjection = "${DataBaseConstant.TASK.COLUMNS.USER_ID} = ? AND ${DataBaseConstant.TASK.COLUMNS.COMPLETE} = ?"

    private fun getFindByUserSelectionArgs(userId: Int, taskFilter: Int) = arrayOf(userId.toString(),taskFilter.toString())

    private fun find(projection: Array<String>, selection: String, selectionArgs: Array<String>, groupBy: String? = null, having: String? = null, orderBy: String? = null) =
            readDB.query(DataBaseConstant.TASK.TABLE_NAME, projection, selection, selectionArgs, groupBy, having, orderBy)

    private fun setListTask(cursor: Cursor): MutableList<TaskEntity> {

        val list = mutableListOf<TaskEntity>()
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val priority: TaskEntity = setTaskEntity(cursor)
                list.add(priority)
            }
        }

        return list
    }

    private fun setTaskEntity(cursor: Cursor): TaskEntity {
        val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstant.TASK.COLUMNS.ID))
        val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstant.TASK.COLUMNS.USER_ID))
        val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstant.TASK.COLUMNS.PRIORITY_ID))
        val description = cursor.getString(cursor.getColumnIndex(DataBaseConstant.TASK.COLUMNS.DESCRIPTION))
        val dueDate = cursor.getString(cursor.getColumnIndex(DataBaseConstant.TASK.COLUMNS.DUE_DATE))
        val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstant.TASK.COLUMNS.COMPLETE)) == 1)

        return TaskEntity(id, userId, priorityId, description, dueDate, complete)
    }


}