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

public class FormNotaActivity extends AppCompatActivity {
    public static final int POSICAO_INVALIDA = -1;
    public static final String CHAVE_POSICAO = "posicao";
    private int posicaoRecebida = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_nota);

        inicializaCampos();

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            Nota notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);

            posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            // -1 é colocado, pois esse valor é invalido e se for recebido esse valor dará erro

            preencheCampos(notaRecebida);
        }
    }



    private void preencheCampos(Nota notaRecebida) {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formNota_EditTxt_Titulo);
        descricao = findViewById(R.id.formNota_EditTxt_Descricao);
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
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecebida);
        setResult(CODIGO_RESULTADO_NOTA_CIRADA, resultadoInsercao);
    }

    @NonNull
    private Nota criaNota() {
        return new Nota(pegaTexto(titulo), pegaTexto(descricao));
    }

    private boolean validacaoIdBotaoSalvamento(@NonNull MenuItem item) {

        return item.getItemId() == R.id.menu_form_nota_ic_salva;
    }

    public String pegaTexto(@NonNull TextView valor) {
        return valor.getText().toString();
    }
}