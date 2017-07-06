package dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by erick on 06/07/2017.
 */

public class DatabaseProvider extends SQLiteOpenHelper {
    private static final String LOG = DatabaseProvider.class.getName();

    //versi database
    private static final int DATABASE_VERSION = 1;

    //database name
    private static  final String DATABASE_NAME = "BackendLocalDb";

    private static final String TableKategori = "Kategori";
    private static final String TableBarang = "Barang";

    //table Kategori
    private static final String KategoriID = "KategoriID";
    private static final String NamaKategori = "NamaKategori";

    //table Barang
    private static final String BarangID = "BarangID";
    private static final String NamaBarang = "NamaBarang";
    private static final String Deskripsi = "Deskripsi";
    private static final String Stok = "Stok";
    private static final String HargaBeli = "HargaBeli";
    private static final String HargaJual = "HargaJual";
    private static final String Gambar = "Gambar";
    private static final String isSync = "isSync";

    //create table
    private static final String Create_Table_Kategori = "create table "+TableKategori+
            "("+KategoriID+" integer primary key,"+NamaKategori+" text);";

    private static final String Create_Table_Barang = "create table "+TableBarang+
            "("+BarangID+" text primary key,"+KategoriID+" integer,"+NamaBarang+" text,"
            +Deskripsi+" text,"+Stok+" integer,"+HargaBeli+" real,"
            +HargaJual+" real,"+Gambar+" text, "+isSync+" integer);";

    public DatabaseProvider(Context context, String name,
                            SQLiteDatabase.CursorFactory factory,
                            int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
