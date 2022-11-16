package com.example.ceep.ui.activity;

import static com.example.ceep.ui.activity.ConstantesCompartilhadas.CHAVE_NOTA;
import static com.example.ceep.ui.activity.ConstantesCompartilhadas.CODIGO_RESULTADO_NOTA_CIRADA;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ceep.R;
import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;

import java.io.Serializable;

public class FormNotaActivity extends AppCompatActivity {


    private int posicaoRecebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_nota);

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA) && dadosRecebidos.hasExtra("posicao")) {
            Nota notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);

            posicaoRecebida = dadosRecebidos.getIntExtra("posicao", -1);
            // -1 é colocado, pois esse valor é invalido e se for recebido esse valor dará erro

            TextView titulo = findViewById(R.id.formNota_EditTxt_Titulo);
            titulo.setText(notaRecebida.getTitulo());
            TextView descricao = findViewById(R.id.formNota_EditTxt_Descricao);
            descricao.setText(notaRecebida.getDescricao());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (validacaoIdBotaoSalvamento(item)) {
            Nota notaCriada = criaNota();
            retornaNota(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota nota) {
        new NotaDAO().insere(nota);
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra("posicao", posicaoRecebida);
        setResult(CODIGO_RESULTADO_NOTA_CIRADA, resultadoInsercao);
    }

    @NonNull
    private Nota criaNota() {
        EditText titulo = findViewById(R.id.formNota_EditTxt_Titulo);
        EditText descricao = findViewById(R.id.formNota_EditTxt_Descricao);
        Nota notaCriada = new Nota(pegaTexto(titulo), pegaTexto(descricao));
        return notaCriada;
    }

    private boolean validacaoIdBotaoSalvamento(@NonNull MenuItem item) {

        return item.getItemId() == R.id.menu_form_nota_ic_salva;
    }

    public String pegaTexto(@NonNull TextView valor) {
        return valor.getText().toString();
    }
}