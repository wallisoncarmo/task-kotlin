package br.com.carmo.wallison.task.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.carmo.wallison.task.constants.DataBaseConstant

class TaskDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION: Int = 1
        private val DATABASE_NAME: String = "task.db"

        private val createTableUser = """
          CREATE TABLE ${DataBaseConstant.USER.TABLE_NAME} (
            ${DataBaseConstant.USER.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${DataBaseConstant.USER.COLUMNS.NAME} TEXT,
            ${DataBaseConstant.USER.COLUMNS.EMAIL} TEXT,
            ${DataBaseConstant.USER.COLUMNS.PASSWORD} TEXT
          );"""

        private val createTablePriority = """
          CREATE TABLE ${DataBaseConstant.PRIORITY.TABLE_NAME} (
            ${DataBaseConstant.PRIORITY.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${DataBaseConstant.PRIORITY.COLUMNS.DESCRIPTION} TEXT
          );"""

        private val createTableTask = """
          CREATE TABLE ${DataBaseConstant.TASK.TABLE_NAME} (
            ${DataBaseConstant.TASK.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${DataBaseConstant.TASK.COLUMNS.USER_ID} INTEGER,
            ${DataBaseConstant.TASK.COLUMNS.PRIORITY_ID} INTEGER,
            ${DataBaseConstant.TASK.COLUMNS.DESCRIPTION} TEXT,
            ${DataBaseConstant.TASK.COLUMNS.COMPLETE} INTEGER,
            ${DataBaseConstant.TASK.COLUMNS.DUE_DATE} TEXT
          );"""

        private val insertPriority = "INSERT INTO ${DataBaseConstant.PRIORITY.TABLE_NAME} VALUES (1,'Baixa'),(2,'Média'),(3,'Alta'),(4,'Crítica');"

        private val deleteTableUser = "DROP TABLE IF EXISTS ${DataBaseConstant.USER.TABLE_NAME};"

        private val deleteTablePriority = "DROP TABLE IF EXISTS ${DataBaseConstant.PRIORITY.TABLE_NAME};"

        private val deleteTableTask = "DROP TABLE IF EXISTS ${DataBaseConstant.TASK.TABLE_NAME};"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableUser)
        db.execSQL(createTableTask)
        db.execSQL(createTablePriority)
        db.execSQL(insertPriority)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(deleteTableUser)
        db.execSQL(deleteTablePriority)
        db.execSQL(deleteTableTask)

        onCreate(db)
    }

}