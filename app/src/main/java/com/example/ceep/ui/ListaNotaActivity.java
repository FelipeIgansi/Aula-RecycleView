package com.example.ceep.ui;

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

        RecyclerView listaNotas = findViewById(R.id.listaNotas_recyclerView);

        NotaDAO notaDao = new NotaDAO();


        for (int i = 0; i < 10000; i++) {
            notaDao.insere(new Nota("Titulo " + i ,
                                "Descrição "+i));
        }
        List<Nota> todasNotas = notaDao.todos();
        listaNotas.setAdapter(new ListaNotasAdapter(this, todasNotas));
        LinearLayoutManager  layoutMananger = new LinearLayoutManager(this);
        listaNotas.setLayoutManager(layoutMananger);

    }
}