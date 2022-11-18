package com.example.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ceep.R;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    // Declaração das variaveis globais
    // Context é o contexto da classe é a referencia para ela
    private final Context context;
    private final List<Nota> notas;
    private OnItemClickListener onItemClickListener;


    public ListaNotasAdapter(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = criaView(parent);
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

    public void altera(int id, Nota nota) {
        notas.set(id, nota) ;
        notifyItemChanged(id, nota);
    }

    public void remove(int id) {
        notas.remove(id);
        notifyItemRemoved(id);
    }

    public void troca(int idInicial, int idFinal) {
        Collections.swap(notas, idInicial, idFinal);
        notifyItemMoved(idInicial, idFinal);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private Nota nota;


        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.itemNota_titulo);
            descricao = itemView.findViewById(R.id.itemNota_descricao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //noinspection deprecation
                    onItemClickListener.onItemClick(nota, getAdapterPosition());
                }
            });
        }

        public void Vincula(Nota nota) {
            this.nota = nota;
            preencheCampos(nota);
        }

        private void preencheCampos(Nota nota) {
            titulo.setText((nota.getTitulo()));
            descricao.setText((nota.getDescricao()));
        }
    }

    public void adiciona(Nota nota) {
        notas.add(nota);
        notifyDataSetChanged();
    }
}

