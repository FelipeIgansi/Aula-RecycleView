package com.example.ceep.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.example.ceep.R;

import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.adapter.ListaNotasAdapter;

import java.util.List;

public class ListaNotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        ListView listaNotas = findViewById(R.id.listaNotas_listView);

        NotaDAO notaDao = new NotaDAO();
        notaDao.insere(new Nota("Primeira nota", "Primeira descrição"));
        List<Nota> todasNotas = notaDao.todos();

        listaNotas.setAdapter(new ListaNotasAdapter(this, todasNotas));

    }
}