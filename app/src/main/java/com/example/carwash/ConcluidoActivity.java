package com.example.carwash;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ConcluidoActivity extends AppCompatActivity {

    // Declaracao dos Botoes
    TextView txtCarro, txtPlaca,txtLavagem,txtCliente,txtStatus;

    ImageView imgPrimeiro, imgAnterior, imgProximo, imgUltimo, imgDeletar, btnVoltar;

    SQLiteDatabase db;

    int id;

    int indice;

    Cursor c;

    DialogInterface.OnClickListener diDeletar;

    Intent iMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concluido);


        // Atribuicao dos valores
        txtCarro = findViewById(R.id.txtcarro_concluido);
        txtPlaca = findViewById(R.id.txtplaca_concluido);
        txtLavagem = findViewById(R.id.txtlavagem_concluido);
        txtCliente = findViewById(R.id.txtcliente_concluido);
        txtStatus = findViewById(R.id.txtstatus_concluido);

        imgPrimeiro = findViewById(R.id.imgprimeiro_concluido);
        imgAnterior = findViewById(R.id.imganterior_concluido);
        imgProximo = findViewById(R.id.imgproximo_concluido);
        imgUltimo = findViewById(R.id.img_ultimo_concluido);

        imgDeletar = findViewById(R.id.imgdeletar_concluido);

        btnVoltar = findViewById(R.id.btnvoltar_concluido);

        try {
            // Banco
            db = openOrCreateDatabase("data", Context.MODE_PRIVATE, null);

            // SQL
            c = db.rawQuery("SELECT * FROM veiculo WHERE status = ?", new String[]{"2"});

            // Primeira listagem
            if(c.getCount() > 0){

                c.moveToFirst();

                indice = 1;

                id = c.getInt(0);
                txtCarro.setText(c.getString(1));
                txtPlaca.setText(c.getString(2));
                txtLavagem.setText(c.getString(3));
                txtCliente.setText(c.getString(4));
                txtStatus.setText(indice + "/" + c.getCount());

            }else{
                txtStatus.setText("Nenhum Registro!");
            }

        }catch(Exception e){
            txtStatus.setText("Erro:" + e.toString());
        }

        //Implementacao dos botoes

        // Botao imagem primeiro
        imgPrimeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(c.getCount() > 0 ){

                    c.moveToFirst();

                    indice = 1;

                    id = c.getInt(0);
                    txtCarro.setText(c.getString(1));
                    txtPlaca.setText(c.getString(2));
                    txtLavagem.setText(c.getString(3));
                    txtCliente.setText(c.getString(4));
                    txtStatus.setText(indice + "/" + c.getCount());

                }
            }
        });

        // Botao imagem anterior
        imgAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(c.getCount() > 0 ){

                    if(indice > 1) {


                        indice--;
                        c.moveToPrevious();

                        id = c.getInt(0);
                        txtCarro.setText(c.getString(1));
                        txtPlaca.setText(c.getString(2));
                        txtLavagem.setText(c.getString(3));
                        txtCliente.setText(c.getString(4));
                        txtStatus.setText(indice + "/" + c.getCount());
                    }
                }
            }
        });

        // Botao imagem proximo
        imgProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(c.getCount() > 0 ){

                    if(indice != c.getCount()) {


                        indice++;
                        c.moveToNext();

                        id = c.getInt(0);
                        txtCarro.setText(c.getString(1));
                        txtPlaca.setText(c.getString(2));
                        txtLavagem.setText(c.getString(3));
                        txtCliente.setText(c.getString(4));
                        txtStatus.setText(indice + "/" + c.getCount());
                    }
                }
            }
        });

        // Botao imagem ultimo
        imgUltimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(c.getCount() > 0 ){

                    if(indice != c.getCount()) {

                        c.moveToLast();
                        indice = c.getCount();

                        id = c.getInt(0);
                        txtCarro.setText(c.getString(1));
                        txtPlaca.setText(c.getString(2));
                        txtLavagem.setText(c.getString(3));
                        txtCliente.setText(c.getString(4));
                        txtStatus.setText(indice + "/" + c.getCount());
                    }
                }
            }
        });

        // Interface dialog deletar
        diDeletar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                db.execSQL("delete from veiculo where id = " + id);

                // Toast Deletado
                Toast.makeText(getApplicationContext(), "Deletado!", Toast.LENGTH_SHORT).show();
                ConcluidoActivity.this.finish();

                iMain = new Intent(ConcluidoActivity.this, MainActivity.class);
                startActivity(iMain);
            }
        };

        // Botao imagem deletar
        imgDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(c.getCount() > 0 ){

                    // Alert dilalogo mensagem
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(ConcluidoActivity.this);
                    dialogo.setTitle("Confirmar");
                    dialogo.setMessage("Deseja Excluir?");
                    dialogo.setNegativeButton("Nao", null);
                    dialogo.setPositiveButton("Sim", diDeletar);
                    dialogo.show();

                }else{
                    MostrarMensagem("Não ha Dados!");
                }
            }
        });


        // Implementacao do botao cancelar
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConcluidoActivity.this.finish();
            }
        });
    }

    // Mostrar Mensagem
    public void MostrarMensagem(String str){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(ConcluidoActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();

    }
}
