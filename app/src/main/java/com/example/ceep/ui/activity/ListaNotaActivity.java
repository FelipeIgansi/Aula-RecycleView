package com.example.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ceep.R;
import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

import java.util.List;

public class ListaNotaActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;
    private List<Nota> todasNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        todasNotas = configuraNotas();
        configuraRecyclerView(todasNotas);

        TextView insereNota = findViewById(R.id.listaNotas_InsereNotas);
        insereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iniciaFormNota = new Intent(ListaNotaActivity.this, FormNotaActivity.class);
                startActivity(iniciaFormNota);
            }
        });

    }

    @Override
    protected void onResume() {
        NotaDAO notaDao = new NotaDAO();
        todasNotas.clear();
        todasNotas.addAll(notaDao.todos());
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    private List<Nota> configuraNotas() {
        NotaDAO notaDao = new NotaDAO();

        notaDao.insere(new Nota("Primeira Nota", "Descrição pequena "),
                new Nota("Segunda Nota", "Segunda descrição é bem maior que a da primeira nota"));
        return notaDao.todos();
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.listaNotas_recyclerView);
        configuraAdapter(todasNotas, listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
    }
}