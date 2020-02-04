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

public class ListagemActivity extends AppCompatActivity {

    // Botoes
    TextView txtCarro, txtPlaca,txtLavagem,txtCliente,txtStatus;

    ImageView imgPrimeiro, imgAnterior, imgProximo, imgUltimo, imgCheck, imgDeletar, btnVoltar;

    SQLiteDatabase db;

    int id;

    int indice;

    Cursor c;

    Intent iMain;

    DialogInterface.OnClickListener diAlterar;

    DialogInterface.OnClickListener diDeletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        // Atribuicao dos valores
        txtCarro = findViewById(R.id.txtcarro_listagem);
        txtPlaca = findViewById(R.id.txtplaca_listagem);
        txtLavagem = findViewById(R.id.txtlavagem_listagem);
        txtCliente = findViewById(R.id.txtcliente_listagem);

        txtStatus = findViewById(R.id.txtstatus_listagem);

        imgPrimeiro = findViewById(R.id.imgprimeiro_listagem);
        imgAnterior = findViewById(R.id.imganterior_listagem);
        imgProximo = findViewById(R.id.imgproximo_listagem);
        imgUltimo = findViewById(R.id.imgultimo_listagem);

        imgCheck = findViewById(R.id.imgcheck_listagem);
        imgDeletar = findViewById(R.id.imgdeletar_listagem);

        btnVoltar = findViewById(R.id.btnvoltar_listagem);

        try {
            // Banco
            db = openOrCreateDatabase("data", Context.MODE_PRIVATE, null);

            //SQL
            c = db.rawQuery("SELECT * FROM veiculo WHERE status = ?", new String[]{"1"});

            if(c.getCount() > 0){

                c.moveToFirst();

                indice = 1;

                // define a ordem
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

        //Alterar dialog
        diAlterar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String carro = txtCarro.getText().toString();
                String placa = txtPlaca.getText().toString();
                String lavagem = txtLavagem.getText().toString();
                String cliente = txtCliente.getText().toString();
                String status = "2";

                try{

                    // Update do banco
                    db.execSQL("update veiculo set carro = '" + carro + "', "
                            + "placa = '" + placa + "', lavagem = '" + lavagem +
                            "', cliente = '" + cliente + "', status = '" + status +
                            "' where id = " + id);

                    //MostrarMensagem("Concluido!");
                    Toast.makeText(getApplicationContext(), "Concluido!", Toast.LENGTH_SHORT).show();
                    ListagemActivity.this.finish();

                }catch(Exception e){
                    MostrarMensagem("Erro: " + e.toString());
                }

                iMain = new Intent(ListagemActivity.this, MainActivity.class);
                startActivity(iMain);
            }
        };

        imgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(ListagemActivity.this);
                dialogo.setTitle("Confirmar");
                dialogo.setMessage("Concluir Lavagem?");
                dialogo.setNegativeButton("Nao", null);
                dialogo.setPositiveButton("Sim", diAlterar);
                dialogo.show();
            }
        });


        // Deletar dialog
        diDeletar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                db.execSQL("delete from veiculo where id = " + id);

                //MostrarMensagem("Deletado!");
                Toast.makeText(getApplicationContext(), "Deletado!", Toast.LENGTH_SHORT).show();
                ListagemActivity.this.finish();

                iMain = new Intent(ListagemActivity.this, MainActivity.class);
                startActivity(iMain);
            }
        };

        // deletar
        imgDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(c.getCount() > 0 ){

                AlertDialog.Builder dialogo = new AlertDialog.Builder(ListagemActivity.this);
                dialogo.setTitle("Confirmar");
                dialogo.setMessage("Deseja Excluir?");
                dialogo.setNegativeButton("Nao", null);
                dialogo.setPositiveButton("Sim", diDeletar);
                dialogo.show();

            }else{
                MostrarMensagem("Nao ha dados!");
            }
            }
        });

        // Implementacao do botao cancelar
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListagemActivity.this.finish();
            }
        });
    }

    public void MostrarMensagem(String str){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(ListagemActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();

    }

}











