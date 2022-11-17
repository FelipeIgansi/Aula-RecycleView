package com.example.ceep.ui.activity;

import static com.example.ceep.ui.activity.ConstantesCompartilhadas.CHAVE_NOTA;
import static com.example.ceep.ui.activity.ConstantesCompartilhadas.CHAVE_POSICAO;
import static com.example.ceep.ui.activity.ConstantesCompartilhadas.CODIGO_REQUISICAO_ALTERA_NOTA;
import static com.example.ceep.ui.activity.ConstantesCompartilhadas.CODIGO_REQUISICAO_INSERE_NOTA;
import static com.example.ceep.ui.activity.ConstantesCompartilhadas.CODIGO_RESULTADO_NOTA_CIRADA;
import static com.example.ceep.ui.activity.ConstantesCompartilhadas.POSICAO_INVALIDA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ceep.R;
import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import com.example.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;
import java.util.List;

public class ListaNotaActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        List<Nota> todasNotas = pegaTodasNotas();
        configuraRecyclerView(todasNotas);

        configuraInputInsereNotas();

    }

    private void configuraInputInsereNotas() {
        TextView insereNota = findViewById(R.id.listaNotas_InsereNotas);
        insereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioNotaActivity_Insere();
            }
        });
    }

    private void vaiParaFormularioNotaActivity_Insere() {
        Intent iniciaFormNota = new Intent(ListaNotaActivity.this, FormNotaActivity.class);
        //noinspection deprecation
        startActivityForResult(iniciaFormNota, CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {
        NotaDAO notaDao = new NotaDAO();
        for (int i = 0; i < 10; i++) {
            notaDao.insere(new Nota("Titulo " +(i+1), "Descriçao " +(i+1)));
        }
        return notaDao.todos();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ehResultadoComNota(requestCode, resultCode, data)) {
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            adicionaNota(notaRecebida);

        }
        if (ehResultadoAlteraNota(requestCode, resultCode, data)) {
            Nota nota = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            int id = data.getIntExtra(CHAVE_POSICAO,POSICAO_INVALIDA);
            if (ehPosicaoValida(id)){
                alteraNota(nota, id);
            }
            else {
                Toast.makeText(this, "Ocorreu um problema na alteração da nota", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void alteraNota(Nota nota, int id) {
        new NotaDAO().altera(id, nota);
        adapter.altera(id, nota);
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, int resultCode, @Nullable Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) &&
                ehCodigoResultadoNotaCriada(resultCode) &&
                temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private void adicionaNota(Nota nota) {
        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private boolean ehResultadoComNota(int requestCode, int resultCode, @Nullable Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                ehCodigoResultadoNotaCriada(resultCode) &&
                temNota(data);
    }

    private boolean temNota(@Nullable Intent data) {
        return data.hasExtra(CHAVE_NOTA);
    }

    private boolean ehCodigoResultadoNotaCriada(int resultCode) {
        return resultCode == CODIGO_RESULTADO_NOTA_CIRADA;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.listaNotas_recyclerView);
        configuraAdapter(todasNotas, listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int posicao) {
                vaiParaFormularioNotaActivity_Altera(nota, posicao);
            }
        });
    }

    private void vaiParaFormularioNotaActivity_Altera(Nota nota, int posicao) {
        Intent abreFormularioEditaNota =
                new Intent(ListaNotaActivity.this,
                        FormNotaActivity.class);
        abreFormularioEditaNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioEditaNota.putExtra(CHAVE_POSICAO, posicao);
        //noinspection deprecation
        startActivityForResult(abreFormularioEditaNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }
}