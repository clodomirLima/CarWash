package com.example.carwash;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class LavagemActivity extends AppCompatActivity {

    // Declaracao dos botoes
    Button btnCadastrar;

    ImageView imgCancelar;

    RadioButton rbSimples, rbAmericana, rbCompleta;

    EditText edtCarro, edtPlaca, edtPlaca1, edtCliente;

    SQLiteDatabase db;

    Intent iMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lavagem);

        // Atribuicao dos valores
        btnCadastrar = findViewById(R.id.btnlavar_lavagem);

        edtCarro = findViewById(R.id.edtcarro_lavagem);
        edtPlaca = findViewById(R.id.edtplaca_lavagem);
        edtPlaca1 = findViewById(R.id.edtplaca1_lavagem);
        edtCliente = findViewById(R.id.edtcliente_lavagem);

        imgCancelar = findViewById(R.id.imgcancelar_lavagem);

        rbSimples = findViewById(R.id.rbsimples_lavagem);
        rbAmericana = findViewById(R.id.rbamericana_lavagem);
        rbCompleta = findViewById(R.id.rbcompleta_lavagem);

        // Focus
        final EditText editText1 = edtPlaca;
        final EditText editText2 = edtPlaca1;

        editText1.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(editText1.getText().length() == 3)
                    editText2.requestFocus();
                return false;
            }
        });


        // Criacao do banco e da tabela
        try{
            db = openOrCreateDatabase("data",
                    Context.MODE_PRIVATE, null);

            db.execSQL("create table if not exists " +
                    "veiculo(id integer primary key autoincrement, " +
                    "carro text not null, " +
                    "placa text not null, "+
                    "lavagem text not null, " +
                    "cliente text not null, " +
                    "status text not null default '1')" );

        }catch (Exception e){
            // Mensagem("Error:"+ e.toString());
        }

        // Implementacao do botao cadastrar
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtCarro.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "O campo Carro e Obrigatorio!", Toast.LENGTH_SHORT).show();

                } else if(edtPlaca.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "O campo Placa e Obrigatorio!", Toast.LENGTH_SHORT).show();

                }else if(edtPlaca1.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "O campo Placa e Obrigatorio!", Toast.LENGTH_SHORT).show();

                }else if(edtCliente.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "O campo Cliente e Obrigatorio!", Toast.LENGTH_SHORT).show();

                }else{

                String rb = " ";

                if (rbSimples.isChecked()) rb = "Simples R$: 10,00";
                if (rbAmericana.isChecked()) rb = "Americana R$: 20,00";
                if (rbCompleta.isChecked()) rb = "Completa R$: 30,00";

                String p = edtPlaca.getText().toString().toUpperCase();
                String p1 = edtPlaca1.getText().toString();
                String carro = edtCarro.getText().toString();
                String placa = p + "-" + p1;
                String cliente = edtCliente.getText().toString();


                try {
                    db.execSQL("insert into veiculo(carro, placa, lavagem, cliente) " +
                            "values('" + carro + "', '" + placa + "', '" +
                            rb + "', '" + cliente + "')");
                    //    MostrarMensagem("Dados Cadastrados!");

                    Toast.makeText(getApplicationContext(), "Cadastrado!", Toast.LENGTH_SHORT).show();
                    LavagemActivity.this.finish();


                } catch (Exception e) {
                    MostrarMensagem("Error:" + e.toString());
                }

                iMain = new Intent(LavagemActivity.this, MainActivity.class);
                startActivity(iMain);
            }
            }
        });

        // Implementacao do botao cancelar
        imgCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LavagemActivity.this.finish();
            }
        });
    }

    public void MostrarMensagem(String str){
        AlertDialog.Builder dialogo = new
                AlertDialog.Builder(LavagemActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();

    }
}
