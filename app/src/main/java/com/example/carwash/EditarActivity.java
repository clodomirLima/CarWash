package com.example.carwash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;

public class EditarActivity extends AppCompatActivity {

    // Botoes
    EditText edtCarro, edtCliente, edtPlaca1, edtPlaca2;

    RadioButton rbSimples, rbAmericana, rbCompleta;

    RadioGroup rgLavagem;

    TextView txtStatus, txtSts;

    ImageView imgPrimeiro, imgAnterior, imgProximo, imgUltimo, imgCancelar;

    SQLiteDatabase db;

    int id;

    int indice;

    Cursor c;

    DialogInterface.OnClickListener diAlterar;

    Button btnAlterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        // Atribuicao dos valores aos botoes
        edtCarro = findViewById(R.id.edtcarro_editar);
        edtPlaca1 = findViewById(R.id.edtplaca1_editar);
        edtPlaca2 = findViewById(R.id.edtplaca2_editar);
        edtCliente = findViewById(R.id.edtcliente_editar);

        rbSimples = findViewById(R.id.rbsimples_editar);
        rbAmericana = findViewById(R.id.rbamericana_editar);
        rbCompleta = findViewById(R.id.rbcompleta_editar);

        rgLavagem = findViewById(R.id.rglavagem_editar);

        imgPrimeiro = findViewById(R.id.imgprimeiro_editar);
        imgAnterior = findViewById(R.id.imganterior_editar);
        imgProximo = findViewById(R.id.imgproximo_editar);
        imgUltimo = findViewById(R.id.imgultimo_editar);
        imgCancelar= findViewById(R.id.imgcancelar_editar);

        txtStatus = findViewById(R.id.txtstatus_editar);
        txtSts = findViewById(R.id.txtsts_editar);

        btnAlterar = findViewById(R.id.btnalterar_editar);

        final EditText editText1 = edtPlaca1;
        final EditText editText2 = edtPlaca2;

        editText1.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(editText1.getText().length() == 3)
                    editText2.requestFocus();
                return false;
            }
        });

        try {

            // Banco
            db = openOrCreateDatabase("data", Context.MODE_PRIVATE, null);

            c = db.rawQuery("SELECT * FROM veiculo WHERE status = ?", new String[]{"1"});

            if(c.getCount() > 0){

                c.moveToFirst();

                indice = 1;

                id = c.getInt(0);
                edtCarro.setText(c.getString(1));

                // Separacao da string das placas
                String placa1 = c.getString(2);
                String  plc1 = placa1.substring(0, 3);
                String  plc2 = placa1.substring(4, 8);
                edtPlaca1.setText(plc1);
                edtPlaca2.setText(plc2);

                // Logica de selecao do Radio Button
                String radioBT = c.getString(3);
                if(radioBT.equals("Simples R$: 10,00")) rbSimples.toggle();
                if(radioBT.equals("Americana R$: 20,00")) rbAmericana.toggle();
                if(radioBT.equals("Completa R$: 30,00")) rbCompleta.toggle();

                edtCliente.setText(c.getString(4));
                txtSts.setText(c.getString(5));
                txtStatus.setText(indice + "/" + c.getCount());

            }else{
                txtStatus.setText("Nenhum Registro");
            }

        }catch (Exception e){
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
                    edtCarro.setText(c.getString(1));

                    // Separacao da string das placas
                    String placa1 = c.getString(2);
                    String  plc1 = placa1.substring(0, 3);
                    String  plc2 = placa1.substring(4, 8);
                    edtPlaca1.setText(plc1);
                    edtPlaca2.setText(plc2);

                    // Logica de selecao do Radio Button
                    String radioBT = c.getString(3);
                    if(radioBT.equals("Simples R$: 10,00")) rbSimples.toggle();
                    if(radioBT.equals("Americana R$: 20,00")) rbAmericana.toggle();
                    if(radioBT.equals("Completa R$: 30,00")) rbCompleta.toggle();

                    edtCliente.setText(c.getString(4));
                    txtSts.setText(c.getString(5));
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
                        edtCarro.setText(c.getString(1));

                        // Separacao da String das placas
                        String placa1 = c.getString(2);
                        String  plc1 = placa1.substring(0, 3);
                        String  plc2 = placa1.substring(4, 8);
                        edtPlaca1.setText(plc1);
                        edtPlaca2.setText(plc2);

                        // Logica de selecao do Radio Button
                        String radioBT = c.getString(3);
                        if(radioBT.equals("Simples R$: 10,00")) rbSimples.toggle();
                        if(radioBT.equals("Americana R$: 20,00")) rbAmericana.toggle();
                        if(radioBT.equals("Completa R$: 30,00")) rbCompleta.toggle();

                        edtCliente.setText(c.getString(4));
                        txtSts.setText(c.getString(5));
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
                        edtCarro.setText(c.getString(1));

                        // Separacao da String das placas
                        String placa1 = c.getString(2);
                        String  plc1 = placa1.substring(0, 3);
                        String  plc2 = placa1.substring(4, 8);
                        edtPlaca1.setText(plc1);
                        edtPlaca2.setText(plc2);

                        // Logica de selecao do Radio Button
                        String radioBT = c.getString(3);
                        if(radioBT.equals("Simples R$: 10,00")) rbSimples.toggle();
                        if(radioBT.equals("Americana R$: 20,00")) rbAmericana.toggle();
                        if(radioBT.equals("Completa R$: 30,00")) rbCompleta.toggle();

                        edtCliente.setText(c.getString(4));
                        txtSts.setText(c.getString(5));
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
                        edtCarro.setText(c.getString(1));

                        // Separacao da String das placas
                        String placa1 = c.getString(2);
                        String  plc1 = placa1.substring(0, 3);
                        String  plc2 = placa1.substring(4, 8);
                        edtPlaca1.setText(plc1);
                        edtPlaca2.setText(plc2);

                        // Logica de selecao do Radio Button
                        String radioBT = c.getString(3);
                        if(radioBT.equals("Simples R$: 10,00")) rbSimples.toggle();
                        if(radioBT.equals("Americana R$: 20,00")) rbAmericana.toggle();
                        if(radioBT.equals("Completa R$: 30,00")) rbCompleta.toggle();

                        edtCliente.setText(c.getString(4));
                        txtSts.setText(c.getString(5));
                        txtStatus.setText(indice + "/" + c.getCount());
                    }
                }
            }
        });



        // Implementacao do botao cancelar
        imgCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarActivity.this.finish();
            }
        });

// Interface dialog alterar
        diAlterar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String rb = " ";

                if(rbSimples.isChecked()) rb = "Simples R$: 10,00";
                if(rbAmericana.isChecked()) rb = "Americana R$: 20,00";
                if(rbCompleta.isChecked()) rb = "Completa R$: 30,00";

                String carro = edtCarro.getText().toString();
                String plcs = edtPlaca1.getText().toString().toUpperCase() + "-" + edtPlaca2.getText();
                String cliente = edtCliente.getText().toString();
                String status = txtSts.getText().toString();

                try{

                    // Update do banco
                    db.execSQL("update veiculo set carro = '" + carro + "', "
                            + "placa = '" + plcs + "', lavagem = '" + rb +
                            "', cliente = '" + cliente + "', status = '" + status +
                            "' where id = " + id);

                    Toast.makeText(getApplicationContext(), "Alterado!", Toast.LENGTH_SHORT).show();
                    EditarActivity.this.finish();

                }catch(Exception e){
                    MostrarMensagem("Erro: " + e.toString());
                }

            }
        };

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo = new
                        AlertDialog.Builder(EditarActivity.this);
                dialogo.setTitle("Confirmar");
                dialogo.setMessage("Deseja Alterar?");
                dialogo.setNegativeButton("Nao", null);
                dialogo.setPositiveButton("Sim", diAlterar);
                dialogo.show();

            }
        });
    }
    public void MostrarMensagem(String str){
        AlertDialog.Builder dialogo = new
                AlertDialog.Builder(EditarActivity.this);
        dialogo.setTitle("Aviso:");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();

    }
}
