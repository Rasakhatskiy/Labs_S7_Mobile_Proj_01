package com.example.s07_mobile_proj_1.db;

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 5
        private const val DATABASE_NAME = "Interbrand.db"

        private const val TABLE_USERS = "user"
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_USER_NAME = "user_name"
        private const val COLUMN_USER_PASSWORD = "user_password"

        private const val TABLE_BRANDS = "brands"
        private const val COLUMN_BRAND_ID = "id"
        private const val COLUMN_BRAND_NAME = "name"
        private const val COLUMN_PLACE = "place"
        private const val COLUMN_VALUE = "value"
        private const val COLUMN_DIFF = "difference"
    }

    private var _db: SQLiteDatabase? = null
    var db: SQLiteDatabase
        get() {
            return this._db ?: this.writableDatabase
        }
        set(value) {
            this._db = value
        }

    override fun onCreate(db: SQLiteDatabase) {
        this.db = db

        val CREATE_USER_TABLE_SQL_COMMAND = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USER_NAME VARCHAR(255), " +
                "$COLUMN_USER_PASSWORD VARCHAR(255))"

        db.execSQL(CREATE_USER_TABLE_SQL_COMMAND)

        val CREATE_BRANDS_TABLE_SQL_COMMAND = "CREATE TABLE $TABLE_BRANDS (" +
                "$COLUMN_BRAND_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_BRAND_NAME VARCHAR(255) NOT NULL," +
                "$COLUMN_PLACE INTEGER NOT NULL," +
                "$COLUMN_VALUE INTEGER NOT NULL," +
                "$COLUMN_DIFF INTEGER NOT NULL)"

        db.execSQL(CREATE_BRANDS_TABLE_SQL_COMMAND)

        this.addStudent(Brand(name = "Microsoft", place = 2, value = 278288, diff = 32))
        this.addStudent(Brand(name = "Mercedes-Benz", place = 8, value = 56103, diff = 10))
        this.addStudent(Brand(name = "Google", place = 4, value = 251751, diff = 28))
        this.addStudent(Brand(name = "Nike", place = 10, value = 50289, diff = 18))
        this.addStudent(Brand(name = "Apple", place = 1, value = 482215, diff = 18))
        this.addStudent(Brand(name = "Coca-Cola", place = 7, value = 57535, diff = 0))
        this.addStudent(Brand(name = "Lois Vuitton", place = 14, value = 44508, diff = 21))
        this.addStudent(Brand(name = "Instagram", place = 16, value = 36516, diff = 14))
        this.addStudent(Brand(name = "Intel", place = 19, value = 32916, diff = -8))
        this.addStudent(Brand(name = "Adobe", place = 21, value = 30660, diff = 23))
        this.addStudent(Brand(name = "Canon", place = 97, value = 5828, diff = -15))
        this.addStudent(Brand(name = "Gilette", place = 71, value = 10211, diff = -4))

        this.addUser(User(name = "admin", password = "admin"))
    }

    fun addStudent(brand: Brand) {
        val values = ContentValues()
        values.put(COLUMN_BRAND_NAME, brand.name)
        values.put(COLUMN_PLACE, brand.place)
        values.put(COLUMN_VALUE, brand.value)
        values.put(COLUMN_DIFF, brand.diff)

        db.insert(TABLE_BRANDS, null, values)
    }

    fun addUser(user: User) {
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_PASSWORD, user.password)

        db.insert(TABLE_USERS, null, values)
    }

    fun authorize(user: User): Boolean {
        val columns = arrayOf(
            COLUMN_USER_NAME,
            COLUMN_USER_PASSWORD
        )

        val cur = db.query(TABLE_USERS, columns, null, null, null, null, null)

        if (!cur.moveToFirst())
            return false
        else {
            val password = cur.getString(cur.getColumnIndex(COLUMN_USER_PASSWORD))
            if (password != user.password) {
                return false
            }
        }

        return true
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BRANDS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun findAllBrands(): List<Brand> {
        val columns = arrayOf(
            COLUMN_BRAND_ID,
            COLUMN_BRAND_NAME,
            COLUMN_PLACE,
            COLUMN_VALUE,
            COLUMN_DIFF
        )
        val studentsList = arrayListOf<Brand>()

        val cursor = db.query(
            TABLE_BRANDS,
            columns,
            null,
            null,
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                studentsList.add(
                    Brand(
                        id = cursor.getInt(cursor.getColumnIndex(COLUMN_BRAND_ID)),
                        name = cursor.getString(cursor.getColumnIndex(COLUMN_BRAND_NAME)),
                        place = cursor.getInt(cursor.getColumnIndex(COLUMN_PLACE)),
                        value = cursor.getInt(cursor.getColumnIndex(COLUMN_VALUE)),
                        diff = cursor.getInt(cursor.getColumnIndex(COLUMN_DIFF)),
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return studentsList
    }
}