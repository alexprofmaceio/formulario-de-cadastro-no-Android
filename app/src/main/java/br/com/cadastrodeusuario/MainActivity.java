package br.com.cadastrodeusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nome = findViewById(R.id.editNome);
        EditText email = findViewById(R.id.editEmail);
        EditText senha = findViewById(R.id.editSenha2);
        EditText endereco = findViewById(R.id.editEndereco);

        Button confirmar = findViewById(R.id.btnCadastrar);
        Button cancelar = findViewById(R.id.btnCancelar);

        DatabaseActivity db = new DatabaseActivity(this);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtNome = nome.getText().toString();
                String txtEmail = email.getText().toString();
                String txtSenha = senha.getText().toString();
                String txtEndereco = endereco.getText().toString();

                //converte a senha digitada em um array de bytes
                byte[] senhaNormal = txtSenha.getBytes();
                BigInteger md5 = null;

                if(txtNome.equals("") || txtEmail.equals("") || txtSenha.equals("") || txtEndereco.equals("")){
                    Toast.makeText(MainActivity.this, "Todos os campos precisam estar preenchidos", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean existe = db.validarUsuario(txtEmail);
                    if(existe == false){
                        try{
                            md5 = new BigInteger(1, EncriptaMD5.encriptaSenha(senhaNormal));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        String senhaEncript = md5.toString(16);

                        Boolean insere = db.inserirDados(txtNome, txtEmail, senhaEncript, txtEndereco);
                        if(insere == true){
                            Toast.makeText(MainActivity.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Erro ao cadastrar.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Usuário já existe no cadastro.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}