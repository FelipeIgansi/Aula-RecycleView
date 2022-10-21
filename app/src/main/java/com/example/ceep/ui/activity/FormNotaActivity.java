package com.example.ceep.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ceep.R;
import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;

public class FormNotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_nota);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_form_nota_ic_salva){
            TextView titulo = findViewById(R.id.formNota_EditTxt_Titulo);
            TextView descricao = findViewById(R.id.formNota_EditTxt_Descricao);
            Nota notaCriada = new Nota(pegaTexto(titulo), pegaTexto(descricao));
            new NotaDAO().insere(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public String pegaTexto(@NonNull TextView valor){
        return valor.getText().toString();
    }
}