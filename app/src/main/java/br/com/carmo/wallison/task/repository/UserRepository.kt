package br.com.carmo.wallison.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import br.com.carmo.wallison.task.constants.DataBaseConstant
import br.com.carmo.wallison.task.entities.UserEntity

class UserRepository private constructor(context: Context) {

    private var taskDatabaseHelper: TaskDatabaseHelper = TaskDatabaseHelper(context)
    private val writeDB = taskDatabaseHelper.writableDatabase
    private val readDB = taskDatabaseHelper.readableDatabase

    companion object {
        fun getInstance(context: Context): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }

            return INSTANCE as UserRepository
        }

        private var INSTANCE: UserRepository? = null

    }

    fun findById(id:Int):UserEntity{
        var user:UserEntity
        try {
            val cursor: Cursor
            val projection = getAllProjection
            val selection = getByIdSelection
            val selectionArgs = getByIdSelectionArgs(id)

            cursor = find(projection, selection, selectionArgs)
            cursor.moveToFirst()
            user = setUserEntity(cursor)
            cursor.close()

        } catch (e: Exception) {
            throw e
        }

        return user

    }

    fun insert(name: String, email: String, password: String): Int {

        var insertValues = ContentValues()
        insertValues = setContentValeus(insertValues, name, email, password)

        return writeDB.insert(DataBaseConstant.USER.TABLE_NAME, null, insertValues).toInt()
    }

    fun isEmailExistent(email: String): Boolean {
        val ret: Boolean

        try {
            val cursor: Cursor
            val projection = getIdProjection()
            val selection = getByEmailSelection
            val selectionArgs = getByEmailSelectionArgs(email)

            cursor = find(projection, selection, selectionArgs)
            ret = cursor.count > 0
            cursor.close()

        } catch (e: Exception) {
            throw e
        }

        return ret
    }


    fun login(email: String, password: String): UserEntity? {

        var userEntity: UserEntity? = null

        try {

            val projection = getAllProjection
            val selection = getByEmailPasswordSelection
            val selectionArgs = getByEmailPassordSelectionArgs(email, password)

            val cursor: Cursor = find(projection, selection, selectionArgs)

            if (cursor.count > 0) {
                cursor.moveToFirst()
                userEntity = setUserEntity(cursor)
            }

            cursor.close()

        } catch (e: Exception) {
            return userEntity;
        }

        return userEntity
    }

    private fun setUserEntity(cursor: Cursor): UserEntity {
        val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstant.USER.COLUMNS.ID))
        val userName = cursor.getString(cursor.getColumnIndex(DataBaseConstant.USER.COLUMNS.NAME))
        val userEmail = cursor.getString(cursor.getColumnIndex(DataBaseConstant.USER.COLUMNS.EMAIL))
        return UserEntity(userId, userName, userEmail)
    }

    private fun setContentValeus(insertValues: ContentValues, name: String, email: String, password: String): ContentValues {
        insertValues.put(DataBaseConstant.USER.COLUMNS.NAME, name)
        insertValues.put(DataBaseConstant.USER.COLUMNS.EMAIL, email)
        insertValues.put(DataBaseConstant.USER.COLUMNS.PASSWORD, password)
        return insertValues
    }

    private fun find(projection: Array<String>, selection: String, selectionArgs: Array<String>, groupBy: String? = null, having: String? = null, orderBy: String? = null) =
            readDB.query(DataBaseConstant.USER.TABLE_NAME, projection, selection, selectionArgs, groupBy, having, orderBy)

    private fun getIdProjection() = arrayOf(DataBaseConstant.USER.COLUMNS.ID)

    private val getAllProjection = arrayOf(DataBaseConstant.USER.COLUMNS.ID,
            DataBaseConstant.USER.COLUMNS.NAME,
            DataBaseConstant.USER.COLUMNS.EMAIL,
            DataBaseConstant.USER.COLUMNS.PASSWORD)

    private val getByEmailSelection = "${DataBaseConstant.USER.COLUMNS.EMAIL} = ?"

    private val getByIdSelection = "${DataBaseConstant.USER.COLUMNS.ID} = ?"

    private val getByEmailPasswordSelection = "${DataBaseConstant.USER.COLUMNS.EMAIL} = ? AND ${DataBaseConstant.USER.COLUMNS.PASSWORD} = ?"

    private fun getByEmailSelectionArgs(email: String) = arrayOf(email)

    private fun getByEmailPassordSelectionArgs(email: String, password: String) = arrayOf(email, password)

    private fun getByIdSelectionArgs(id: Int) = arrayOf(id.toString())

}