package com.example.ceep.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ceep.R;
import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

import java.util.List;

public class ListaNotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        List<Nota> todasNotas = configuraNotas();
        configuraRecyclerView(todasNotas);

    }

    private List<Nota> configuraNotas() {
        NotaDAO notaDao = new NotaDAO();
//        for (int i = 0; i < 10000; i++) {
//            notaDao.insere(new Nota("Titulo " + i ,
//                                "Descrição "+i));
//        }
        notaDao.insere(new Nota("Primeira Nota", "Descrição pequena "),
                new Nota("Segunda Nota", "Segunda descrição é bem maior que a da primeira nota"));
        List<Nota> todasNotas = notaDao.todos();
        return todasNotas;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.listaNotas_recyclerView);
        configuraAdapter(todasNotas, listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        listaNotas.setAdapter(new ListaNotasAdapter(this, todasNotas));
    }
}