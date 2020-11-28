package com.example.valijafari_finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OmdbRepository extends SQLiteOpenHelper {

    String TABLE_NAME = "OmdbInformation";

    public OmdbRepository(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
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
                ")";
        db.execSQL(CREATE_TABLE_QUERY);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade
    }

    public void insertOmdbInformation(String Title, String ImdbId, String Year, String Poster, String runtime, String Genre, String Writer, String Actors, String Language, String imdbRating) {
        String INSERT_OmdbInformation_QUERY = "INSERT INTO " + TABLE_NAME + "(Title,ImdbId,Year,Poster,runtime,Genre,Writer,Actors,Language,imdbRating)" +
                "VALUES("
                + "'" + Title + "'" + "," + "'" + ImdbId + "'" + ","+ "'" + Year + "'" + ","+ "'" + Poster + "'"+ ","
                + "'" + runtime + "'" + "," + "'" + Genre + "'" + ","+ "'" + Writer + "'" + ","+ "'" + Actors + "'"+ ","
                + "'" + Language + "'" + "," + "'" + imdbRating + "'" +  ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT_OmdbInformation_QUERY);
        db.close();
    }
    public void DeleteOmdbInformation( String ImdbId) {
        String Delete_OmdbInformation_QUERY = "Delete From " + TABLE_NAME +" Where ImdbId="+ "'" + ImdbId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(Delete_OmdbInformation_QUERY);
        db.close();
    }

    List<OmdbDetailClass> getAllOmdbInformation() {

        String GET_ALL_OmdbInformation_QUERY = "SELECT Title,ImdbId,Year,Poster,runtime,Genre,Writer,Actors,Language,imdbRating FROM " + TABLE_NAME;
        ArrayList<OmdbDetailClass> OmdbDetailClassList = new ArrayList<>();
        OmdbDetailClass omdbDetailClass = new OmdbDetailClass();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_ALL_OmdbInformation_QUERY, null);

        while (cursor.moveToNext()) {
            omdbDetailClass = new OmdbDetailClass();

            omdbDetailClass.setTitle(cursor.getString(0));
            omdbDetailClass.setImdbID(cursor.getString(1));
            omdbDetailClass.setYear(cursor.getString(2));
            omdbDetailClass.setPoster(cursor.getString(3));
            omdbDetailClass.setRuntime(cursor.getString(4));
            omdbDetailClass.setGenre(cursor.getString(5));
            omdbDetailClass.setWriter(cursor.getString(6));
            omdbDetailClass.setActors(cursor.getString(7));
            omdbDetailClass.setLanguage(cursor.getString(8));
            omdbDetailClass.setImdbRating(cursor.getString(9));


            OmdbDetailClassList.add(omdbDetailClass);
        }
        db.close();
        return OmdbDetailClassList;
    }

    OmdbClass getAllOmdbInformation_Favorite() {

        String GET_ALL_OmdbInformation_QUERY = "SELECT Title,ImdbId,Year,Poster FROM " + TABLE_NAME;
        OmdbClass dto_OmdbClass = new OmdbClass();
        List<Search> lst_dto_Search = new ArrayList<Search>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_ALL_OmdbInformation_QUERY, null);

        while (cursor.moveToNext()) {
            Search dto_Search = new Search();

            dto_Search.setTitle(cursor.getString(0));
            dto_Search.setImdbID(cursor.getString(1));
            dto_Search.setYear(cursor.getString(2));
            dto_Search.setPoster(cursor.getString(3));

            lst_dto_Search.add(dto_Search);
        }
        dto_OmdbClass.setSearch(lst_dto_Search);
        db.close();
        return dto_OmdbClass;
    }

    boolean ExsitsFilm(String ImdbId)
    {

        String GET_OmdbInformation_QUERY = "SELECT ImdbId FROM " + TABLE_NAME+ " Where ImdbId="+ "'" + ImdbId + "'";
        OmdbDetailClass omdbDetailClass = new OmdbDetailClass();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_OmdbInformation_QUERY, null);

        while (cursor.moveToNext()) {
            if (ImdbId.equals(cursor.getString(0)))
            {
                db.close();
                return  true    ;
            }
        }
        db.close();
        return false;
    }

    OmdbDetailClass GetRowOmdbInformation(String ImdbId)
    {

        String GET_OmdbInformation_QUERY = "SELECT Title,ImdbId,Year,Poster,runtime,Genre,Writer,Actors,Language,imdbRating" +
                " FROM " + TABLE_NAME+ " Where ImdbId="+ "'" + ImdbId + "'";
        OmdbDetailClass omdbDetailClass = new OmdbDetailClass();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_OmdbInformation_QUERY, null);

        while (cursor.moveToNext()) {
            if (ImdbId.equals(cursor.getString(1)))
            {

                omdbDetailClass.setTitle(cursor.getString(0));
                omdbDetailClass.setImdbID(cursor.getString(1));
                omdbDetailClass.setYear(cursor.getString(2));
                omdbDetailClass.setPoster(cursor.getString(3));
                omdbDetailClass.setRuntime(cursor.getString(4));
                omdbDetailClass.setGenre(cursor.getString(5));
                omdbDetailClass.setWriter(cursor.getString(6));
                omdbDetailClass.setActors(cursor.getString(7));
                omdbDetailClass.setLanguage(cursor.getString(8));
                omdbDetailClass.setImdbRating(cursor.getString(9));

            }
        }
        db.close();
        return   omdbDetailClass;

    }
}
