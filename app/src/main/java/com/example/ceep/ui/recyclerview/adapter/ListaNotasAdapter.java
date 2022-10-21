package com.example.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ceep.R;
import com.example.ceep.model.Nota;
import java.util.List;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private final Context context;
    private final List<Nota> notas;
    private int qtnViewHolder = 0;


    public ListaNotasAdapter(Context context, List<Nota> notas ) {
        this.context = context;
        this.notas = notas;
    }

    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        qtnViewHolder++;
        View viewCriada = criaView(parent);
        Log.i("recyclerView Adapter", "quantidade view holder: "+qtnViewHolder);
        return new NotaViewHolder(viewCriada);
    }

    private View criaView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(context)
                .inflate(R.layout.activity_item_nota, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaNotasAdapter.NotaViewHolder holder, int position) {
        Nota nota = notas.get(position);
        holder.Vincula(nota);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    class NotaViewHolder extends  RecyclerView.ViewHolder{

        private final TextView titulo;
        private final TextView descricao;
        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.itemNota_titulo);

            descricao = itemView.findViewById(R.id.itemNota_descricao);
        }

        public void Vincula(Nota nota){
            preencheCampos(nota);
        }

        private void preencheCampos(Nota nota) {
            titulo.setText((nota.getTitulo()));
            descricao.setText((nota.getDescricao()));
        }
    }
    public void adiciona(Nota nota){
        notas.add(nota);
        notifyDataSetChanged();
    }
}

