package kamil.com.monitorowaniezdrowia

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context):SQLiteOpenHelper(context,dbname,factory,version ){
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL( "create table user(id integer primary key autoincrement,"+
        "name varchar(30), email varchar(100), password varchar(20))")
        p0?.execSQL("create table kroki(id integer primary key autoincrement,"+
                "ilosckrokow varchar(30))")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        onCreate(p0)
    }

    fun insertKroki(kroki: String){
        val db:SQLiteDatabase=writableDatabase
        val values = ContentValues()
        values.put("ilosckrokow", kroki)
        db.update("kroki", values, "id=1",null )
        db.close()
    }

    fun readKroki():String {
        val db = writableDatabase
        val query = "select ilosckrokow from kroki"
        val cursor = db.rawQuery(query, null)
        var przeczytajKroki = "Pusty"

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            przeczytajKroki = cursor.getString(0)
            cursor.close()

        }
        db.close()
        return przeczytajKroki

    }

    fun insertUserData(name: String, email: String, password: String){
        val db:SQLiteDatabase=writableDatabase
        val values = ContentValues()
        values.put("name",name)
        values.put("email",email)
        values.put("password",password)

        db.insert("user", null,values)
        db.close()

    }

    fun userpasswordPresent(name: String, password: String):Boolean{
        val db=writableDatabase
        val query="select * from user where name = '$name' and password = '$password'"
        val cursor=db.rawQuery(query,null)
        if (cursor.count<=0){
            cursor.close()
            return false
        }
        cursor.close()
        db.close()
        return true
    }

    fun useremail(name: String):String{
        val db=writableDatabase
        val query="select email from user where name = '$name'"
        val cursor=db.rawQuery(query,null)
        var emailuzytkownika = "Brak e-mail"

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            emailuzytkownika = cursor.getString(0)
            cursor.close()

        }
        db.close()
        return  emailuzytkownika
    }

    fun userPresent(name: String):Boolean{
        val db=writableDatabase
        val query="select * from user where name = '$name'"
        val cursor=db.rawQuery(query,null)
        if (cursor.count<=0){
            cursor.close()
            db.close()
            return false
        }
        cursor.close()
        db.close()
        return true
    }

    companion object {
        internal val dbname="userDB"
        internal val factory = null
        internal val version = 1
    }



}
