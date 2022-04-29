package br.com.cadastrodeusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText login = findViewById(R.id.editLogin);
        EditText senha = findViewById(R.id.editSenha);
        Button acessar = findViewById(R.id.btnLogin);

        DatabaseActivity db = new DatabaseActivity(this);

        acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = login.getText().toString();
                byte[] pass = senha.getText().toString().getBytes();

                BigInteger md5 = null;

                if(login.getText().toString() == "" || senha.getText().toString() == ""){
                    Toast.makeText(LoginActivity.this,"Informe login e senha", Toast.LENGTH_SHORT);
                } else {
                    try{
                        md5 = new BigInteger(1, EncriptaMD5.encriptaSenha(pass));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    String senhaEncript = md5.toString(16);

                    Boolean checaLogin = db.validaLogin(user, senhaEncript);
                    if(checaLogin == true){
                        Toast.makeText(LoginActivity.this,"Login feito com sucesso!", Toast.LENGTH_SHORT);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this,"Login inválido!", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }
}