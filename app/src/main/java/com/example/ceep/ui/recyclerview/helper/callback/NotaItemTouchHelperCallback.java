package com.example.ceep.ui.recyclerview.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.dao.NotaDAO;
import com.example.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListaNotasAdapter adapter;

    public NotaItemTouchHelperCallback(ListaNotasAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int marcacoesDeArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP |
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int idInicial = viewHolder.getAdapterPosition();
        int idFinal = target.getAdapterPosition();
        new NotaDAO().troca(idInicial, idFinal);
        adapter.troca(idInicial, idFinal);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        @SuppressWarnings("deprecation") int id = viewHolder.getAdapterPosition();
        new NotaDAO().remove(id);
        adapter.remove(id);
    }
}
