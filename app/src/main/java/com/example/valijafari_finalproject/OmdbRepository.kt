package com.example.valijafari_finalproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class OmdbRepository(context: Context?, name: String?, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    var TABLE_NAME = "OmdbInformation"
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Title TEXT," +
                "ImdbId Text," +
                "Year TEXT," +
                "Poster TEXT," +
                "runtime TEXT," +
                "Genre TEXT," +
                "Writer TEXT," +
                "Actors TEXT," +
                "Language TEXT," +
                "imdbRating TEXT" +
                ")"
        db.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // on upgrade
    }

    fun insertOmdbInformation(Title: String, ImdbId: String, Year: String, Poster: String, runtime: String, Genre: String, Writer: String, Actors: String, Language: String, imdbRating: String) {
        val INSERT_OmdbInformation_QUERY = ("INSERT INTO " + TABLE_NAME + "(Title,ImdbId,Year,Poster,runtime,Genre,Writer,Actors,Language,imdbRating)" +
                "VALUES("
                + "'" + Title + "'" + "," + "'" + ImdbId + "'" + "," + "'" + Year + "'" + "," + "'" + Poster + "'" + ","
                + "'" + runtime + "'" + "," + "'" + Genre + "'" + "," + "'" + Writer + "'" + "," + "'" + Actors + "'" + ","
                + "'" + Language + "'" + "," + "'" + imdbRating + "'" + ")")
        val db = this.writableDatabase
        db.execSQL(INSERT_OmdbInformation_QUERY)
        db.close()
    }

    fun DeleteOmdbInformation(ImdbId: String) {
        val Delete_OmdbInformation_QUERY = "Delete From $TABLE_NAME Where ImdbId='$ImdbId'"
        val db = this.writableDatabase
        db.execSQL(Delete_OmdbInformation_QUERY)
        db.close()
    }

    val allOmdbInformation: List<OmdbDetailClass>
        get() {
            val GET_ALL_OmdbInformation_QUERY = "SELECT Title,ImdbId,Year,Poster,runtime,Genre,Writer,Actors,Language,imdbRating FROM $TABLE_NAME"
            val OmdbDetailClassList = ArrayList<OmdbDetailClass>()
            var omdbDetailClass = OmdbDetailClass()
            val db = this.readableDatabase
            val cursor = db.rawQuery(GET_ALL_OmdbInformation_QUERY, null)
            while (cursor.moveToNext()) {
                omdbDetailClass = OmdbDetailClass()
                omdbDetailClass.title = cursor.getString(0)
                omdbDetailClass.imdbID = cursor.getString(1)
                omdbDetailClass.year = cursor.getString(2)
                omdbDetailClass.poster = cursor.getString(3)
                omdbDetailClass.runtime = cursor.getString(4)
                omdbDetailClass.genre = cursor.getString(5)
                omdbDetailClass.writer = cursor.getString(6)
                omdbDetailClass.actors = cursor.getString(7)
                omdbDetailClass.language = cursor.getString(8)
                omdbDetailClass.imdbRating = cursor.getString(9)
                OmdbDetailClassList.add(omdbDetailClass)
            }
            db.close()
            return OmdbDetailClassList
        }
    val allOmdbInformation_Favorite: OmdbClass
        get() {
            val GET_ALL_OmdbInformation_QUERY = "SELECT Title,ImdbId,Year,Poster FROM $TABLE_NAME"
            val dto_OmdbClass = OmdbClass()
            val lst_dto_Search: MutableList<Search> = ArrayList()
            val db = this.readableDatabase
            val cursor = db.rawQuery(GET_ALL_OmdbInformation_QUERY, null)
            while (cursor.moveToNext()) {
                val dto_Search = Search()
                dto_Search.title = cursor.getString(0)
                dto_Search.imdbID = cursor.getString(1)
                dto_Search.year = cursor.getString(2)
                dto_Search.poster = cursor.getString(3)
                lst_dto_Search.add(dto_Search)
            }
            dto_OmdbClass.search = lst_dto_Search
            db.close()
            return dto_OmdbClass
        }

    fun ExsitsFilm(ImdbId: String): Boolean {
        val GET_OmdbInformation_QUERY = "SELECT ImdbId FROM $TABLE_NAME Where ImdbId='$ImdbId'"
        val omdbDetailClass = OmdbDetailClass()
        val db = this.readableDatabase
        val cursor = db.rawQuery(GET_OmdbInformation_QUERY, null)
        while (cursor.moveToNext()) {
            if (ImdbId == cursor.getString(0)) {
                db.close()
                return true
            }
        }
        db.close()
        return false
    }

    fun GetRowOmdbInformation(ImdbId: String): OmdbDetailClass {
        val GET_OmdbInformation_QUERY = "SELECT Title,ImdbId,Year,Poster,runtime,Genre,Writer,Actors,Language,imdbRating" +
                " FROM " + TABLE_NAME + " Where ImdbId=" + "'" + ImdbId + "'"
        val omdbDetailClass = OmdbDetailClass()
        val db = this.readableDatabase
        val cursor = db.rawQuery(GET_OmdbInformation_QUERY, null)
        while (cursor.moveToNext()) {
            if (ImdbId == cursor.getString(1)) {
                omdbDetailClass.title = cursor.getString(0)
                omdbDetailClass.imdbID = cursor.getString(1)
                omdbDetailClass.year = cursor.getString(2)
                omdbDetailClass.poster = cursor.getString(3)
                omdbDetailClass.runtime = cursor.getString(4)
                omdbDetailClass.genre = cursor.getString(5)
                omdbDetailClass.writer = cursor.getString(6)
                omdbDetailClass.actors = cursor.getString(7)
                omdbDetailClass.language = cursor.getString(8)
                omdbDetailClass.imdbRating = cursor.getString(9)
            }
        }
        db.close()
        return omdbDetailClass
    }
}