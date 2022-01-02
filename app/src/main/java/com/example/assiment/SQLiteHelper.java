package com.example.assiment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.net.PortUnreachableException;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TourismDB.sqlite" ;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Hotel(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB , idFB VARCHAR)");
//        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Resturant(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB , idFB VARCHAR)");
//        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Tourism(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB , idFB VARCHAR)");
//        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS  Shop(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB , idFB VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS account(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, email VARCHAR, password VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void Insertaccount(String name , String email , String password){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO account VALUES (NULL , ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1 , name);
        statement.bindString(2, email);
        statement.bindString(3 , password);

        statement.executeInsert();
    }

    public void UpdateAccount( String name ,String email , String password, int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE account SET name = ?,email = ? ,password = ? WHERE id = ?";

        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2,email);
        statement.bindString(3 , password);
        statement.bindDouble(4 , id);

        statement.execute();
        database.close();

    }

    public void insertResturant(String name, String price, byte[] image, String idFB , String cor1 , String cor2){

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Resturant VALUES (null, ?, ?, ?, ? ,? ,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);
        statement.bindString(4 , idFB);
        statement.bindString(5 , cor1);
        statement.bindString(6 ,cor2);

        statement.executeInsert();
    }
    public void insertTourism(String name, String price, byte[] image, String idFB , String cor1 , String cor2){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Tourism VALUES (NULL, ?, ?, ?, ? ,? ,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);
        statement.bindString(4 , idFB);
        statement.bindString(5 , cor1);
        statement.bindString(6 ,cor2);

        statement.executeInsert();
    }

    public void insertShop(String name, String price, byte[] image, String idFB , String cor1 , String cor2){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Shop VALUES (NULL, ?, ?, ?, ? ,? ,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);
        statement.bindString(4 , idFB);
        statement.bindString(5 , cor1);
        statement.bindString(6 ,cor2);

        statement.executeInsert();
    }

    public void insertHotel(String name, String address, byte[] image , String idFB  , String cor1 , String cor2){
       // insertHotel();
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Hotel VALUES (NULL, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, address);
        statement.bindBlob(3, image);
        statement.bindString(4 , idFB);
        statement.bindString(5 , cor1);
        statement.bindString(6 ,cor2);

        statement.executeInsert();
    }


        public void updateHotel(String name, String price, byte[] image, int id ) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE Hotel SET name = ?, price = ?, image = ?  WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);
        statement.bindDouble(4, id);

        statement.execute();
        database.close();
    }

    public  void deleteHotel(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM Hotel WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }
    public void updateResturant(String name, String price, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE Resturant SET name = ?, price = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);
        statement.bindDouble(4, (double)id);

        statement.execute();
        database.close();
    }

    public  void deleteResturant(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM Resturant WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }
    public void updateTourism(String name, String price, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE Tourism SET name = ?, price = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);
        statement.bindDouble(4, (double)id);

        statement.execute();
        database.close();
    }

    public  void deleteTourism(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM Tourism WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }
    public void updateShop(String name, String price, byte[] image, int id ) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE Shop SET name = ?, price = ?, image = ?  WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindBlob(3, image);
        statement.bindDouble(4, id);

        statement.execute();
        database.close();
    }


    public  void deleteShop(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM Shop WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public static byte[] imageViewToByte(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
