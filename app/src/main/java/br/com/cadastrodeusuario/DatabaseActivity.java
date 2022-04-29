package br.com.cadastrodeusuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseActivity extends SQLiteOpenHelper {
    public static final String db = "login.db";

    //método construtor do banco de dados
    public DatabaseActivity(@Nullable Context context) {
        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table usuario(login TEXT primary key, senha TEXT, nome TEXT, endereco TEXT, sexo TEXT)");
    }

    //método de atualização do banco de dados
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists usuario");
    }

    //método para inserir dados no banco de dados
    public Boolean inserirDados(String nome, String login, String senha, String endereco){
        SQLiteDatabase banco = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", nome);
        contentValues.put("login", login);
        contentValues.put("senha", senha);
        contentValues.put("endereco", endereco);
        long result = banco.insert("usuario", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean validaUsuario(String login){
        SQLiteDatabase banco = this.getWritableDatabase();
        Cursor cursor = banco.rawQuery("select * from usuario where login = ?", new String[] {login});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean validaLogin(String login, String senha) {
        SQLiteDatabase banco = this.getWritableDatabase();
        Cursor cursor = banco.rawQuery("select * from usuario where login = ? and senha = ?", new String[]{login, senha});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
