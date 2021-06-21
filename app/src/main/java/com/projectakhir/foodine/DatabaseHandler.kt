package com.projectakhir.foodine

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.projectakhir.foodine.AllMethod.Gender
import com.projectakhir.foodine.DataClass.DatabaseModel
import com.projectakhir.foodine.DataClass.MainUsers
import com.projectakhir.foodine.DataClass.UserDetail

class DatabaseHandler(val context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private val DATABASE_NAME = "FoodineDB"
        private val DATABASE_VERSION = 1

        private val USER_TABLE = "UserTable"
        private val COL_ID = "id"
        private val COL_EMAIL ="email"
        private val COL_TOKEN ="api_token"
        private val COL_REMEMBER = "remember"
        private val COL_NAME = "name"
        private val COL_GENDER = "gender"
        private val COL_PHOTO = "photo"
        private val COL_DOB = "dob"
        private val COL_BIO = "bio"
        private val COL_URL = "url"

        //todo : userGoal
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $USER_TABLE(" +
                "$COL_ID INTEGER PRIMARY KEY, " +
                "$COL_EMAIL TEXT, $COL_TOKEN TEXT, " +
                "$COL_REMEMBER INTEGER DEFAULT 0, " +
                "$COL_NAME TEXT, " +
                "$COL_GENDER TEXT, " +
                "$COL_PHOTO TEXT, " +
                "$COL_DOB TEXT, " +
                "$COL_BIO TEXT," +
                "$COL_URL TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $USER_TABLE")
        onCreate(db)
    }

    private fun Boolean.toInt() = if (this) 1 else 0
    private fun Int.toBoolean() = this==1

    fun size():Int{
        val countQuery = "SELECT  * FROM $USER_TABLE"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(countQuery, null)
        val count: Int = cursor.count
        cursor.close()
        return count
    }

    fun getUser() : DatabaseModel {
        //in SplashScreenActivity
        val db = this.writableDatabase
        val output = DatabaseModel(0,false, null)
        val sqlCommand = "SELECT * FROM $USER_TABLE ORDER BY $COL_ID DESC LIMIT 1;"
        val cursor = db.rawQuery(sqlCommand, null)

        if(cursor.moveToFirst()){
            output.remember = cursor.getInt(cursor.getColumnIndex(COL_REMEMBER)).toBoolean()
            output.mainUser = MainUsers(
                userEmail = cursor.getString(cursor.getColumnIndex(COL_EMAIL)),
                userAPItoken = cursor.getString(cursor.getColumnIndex(COL_TOKEN)),
                userDetail = UserDetail(
                    cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    Gender.valueOf(cursor.getString(cursor.getColumnIndex(COL_GENDER))),
                    null,
                    cursor.getString(cursor.getColumnIndex(COL_DOB)),
                    cursor.getString(cursor.getColumnIndex(COL_BIO)),
                    cursor.getString(cursor.getColumnIndex(COL_URL)),
                    null)
            )
        }
        cursor.close()
        return output
    }

    fun modifyUser(input: DatabaseModel) : DatabaseModel{
        //in SignInFragment, SignUpFragment, and todo SettingsAccountActivity
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_REMEMBER, input.remember?.toInt())
        contentValues.put(COL_EMAIL, input.mainUser?.userEmail)
        contentValues.put(COL_TOKEN, input.mainUser?.userAPItoken)
        contentValues.put(COL_NAME, input.mainUser?.userDetail?.userName)
        contentValues.put(COL_GENDER, input.mainUser?.userDetail?.userGender.toString())
        contentValues.put(COL_DOB, input.mainUser?.userDetail?.userDob)
        contentValues.put(COL_BIO, input.mainUser?.userDetail?.userBio)
        contentValues.put(COL_URL, input.mainUser?.userDetail?.userUrl)

        val lastData = getUser()
        if(lastData.mainUser?.userEmail == input.mainUser?.userEmail){
            db.update(USER_TABLE, contentValues, null, null)
        }else{
            db.insert(USER_TABLE, null, contentValues)
        }
        db.close()
        return getUser()
    }

    fun deleteUser(): DatabaseModel{
        //in MainActivity (logout method)
        val db = this.writableDatabase
        val contentValues = ContentValues()

        val lastData = getUser()
        if(lastData.remember == true){
            contentValues.put(COL_TOKEN, " ")
            db.update(USER_TABLE, contentValues, null, null)
        }else{
            db.delete(USER_TABLE, null, null)
        }
        db.close()
        return getUser()
    }
}