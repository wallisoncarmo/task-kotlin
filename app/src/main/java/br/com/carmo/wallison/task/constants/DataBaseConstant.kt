package br.com.carmo.wallison.task.constants

class DataBaseConstant {

    object USER{
        val TABLE_NAME = "user"
        object COLUMNS{
            val ID ="id"
            val NAME ="name"
            val EMAIL ="email"
            val PASSWORD ="password"
        }
    }

    object PRIORITY{
        val TABLE_NAME = "priority"
        object COLUMNS{
            val ID ="id"
            val DESCRIPTION ="description"
        }
    }

    object TASK{
        val TABLE_NAME = "task"
        object COLUMNS{
            val ID ="id"
            val USER_ID ="user_id"
            val PRIORITY_ID ="priority_id"
            val DESCRIPTION ="description"
            val COMPLETE ="complete"
            val DUE_DATE ="due_date"
        }
    }
}