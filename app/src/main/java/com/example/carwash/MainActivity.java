package com.example.carwash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Declaracao dos botoes
    Button btnLavagem, btnConcluido, btnListagem, btnEditar;

    TextView txtLista, txtConcluidos;

    Intent iLavagem, iConcluido,iListagem, iEditar ;

    Cursor c,c1;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Atribuicao dos valores aos botoes principais
        btnLavagem = findViewById(R.id.btnlavagem_main);
        btnConcluido = findViewById(R.id.btnconcluido_main);
        btnListagem = findViewById(R.id.btnlistagem_main);
        btnEditar = findViewById(R.id.btnalterar_main);

        txtLista =findViewById(R.id.txtlistastatus_main);
        txtConcluidos =findViewById(R.id.txtconcluidostatus_main);

        String lista = "";
        String concluido = "";

        try{
            db = openOrCreateDatabase("data", Context.MODE_PRIVATE, null);
            c = db.rawQuery("SELECT count(*) test FROM veiculo WHERE status = ?", new String[]{"1"});
            c1 = db.rawQuery("SELECT count(*) test FROM veiculo WHERE status = ?", new String[]{"2"});

            c.moveToFirst();
            c1.moveToFirst();

            lista = c.getString(c.getColumnIndex("test"));
            concluido = c1.getString(c1.getColumnIndex("test"));

            txtLista.setText(lista);
            txtConcluidos.setText(concluido);

        }catch (Exception e){

        }

        // Implementacao dos botoes principais
        // Botao Lavagem
        btnLavagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iLavagem = new Intent(MainActivity.this, LavagemActivity.class);
                startActivity(iLavagem);
            }
        });

        // Botao Concluidos
        btnConcluido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iConcluido = new Intent(MainActivity.this, ConcluidoActivity.class);
                startActivity(iConcluido);
            }
        });

        // Botao Listagem
        btnListagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iListagem = new Intent(MainActivity.this, ListagemActivity.class);
                startActivity(iListagem);
            }
        });

        // Botao Editar
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iEditar = new Intent(MainActivity.this, EditarActivity.class);
                startActivity(iEditar);
            }
        });


    }
}
