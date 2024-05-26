package com.example.atividade5_ac2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.atividade5_ac2.R;
import com.example.atividade5_ac2.activity.AlunoActivity;
import com.example.atividade5_ac2.api.AlunoService;
import com.example.atividade5_ac2.api.ApiClient;
import com.example.atividade5_ac2.model.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoHolder> {
    private final List<Aluno> alunos;
    Context context;

    public AlunoAdapter(List<Aluno> alunos, Context context) {
        this.alunos = alunos;
        this.context = context;
    }

    @Override
    public AlunoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlunoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_alunos, parent, false));
    }

    @Override
    public void onBindViewHolder(AlunoHolder holder, int position) {
        holder.nome.setText(alunos.get(position).getNome());
        holder.ra.setText(alunos.get(position).getRa());
        holder.cep.setText(alunos.get(position).getCep());
        holder.logradouro.setText(alunos.get(position).getLogradouro());
        holder.complemento.setText(alunos.get(position).getComplemento());
        holder.bairro.setText(alunos.get(position).getBairro());
        holder.cidade.setText(alunos.get(position).getCidade());
        holder.uf.setText(alunos.get(position).getUf());



        holder.btnexcluir.setOnClickListener(view -> removerItem(position));
        holder.btneditar.setOnClickListener(view -> editarItem(position));
    }

    @Override
    public int getItemCount() {
        return alunos != null ? alunos.size() : 0;
    }
    private void removerItem(int position) {

        new AlertDialog.Builder(context)
                .setTitle("Deletar Item")
                .setMessage("Você tem certeza que deseja deletar este item?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Código para deletar o item
                        deleteItem(position);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteItem(int position) {
        int id = alunos.get(position).getId();
        AlunoService apiService = ApiClient.getAlunoService();
        Call<Void> call = apiService.deleteAluno(id);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    alunos.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, alunos.size());
                    Toast.makeText(context, "Excluido com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Exclusao", "Erro ao excluir");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Log.e("Exclusao", "Erro ao excluir");
            }
        });

    }
    private void editarItem(int position) {
        int id = alunos.get(position).getId();
        Intent i = new Intent(context, AlunoActivity.class);
        i.putExtra("id",id);
        context.startActivity(i);
    }


    public class AlunoHolder extends RecyclerView.ViewHolder {
        public TextView nome;
        public TextView ra;
        public TextView cep;
        public TextView logradouro;
        public TextView complemento;
        public TextView bairro;
        public TextView cidade;
        public TextView uf;


        public ImageView btnexcluir;
        public ImageView btneditar;

        public AlunoHolder(View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.textViewnome);
            ra = (TextView) itemView.findViewById(R.id.textViewra);
            cep = (TextView) itemView.findViewById(R.id.textViewcep);
            logradouro = (TextView) itemView.findViewById(R.id.textViewlogradouro);
            complemento = (TextView) itemView.findViewById(R.id.textViewcomplemento);
            bairro = (TextView) itemView.findViewById(R.id.textViewbairro);
            cidade = (TextView) itemView.findViewById(R.id.textViewcidade);
            uf = (TextView) itemView.findViewById(R.id.textViewuf);
            btnexcluir = (ImageView) itemView.findViewById(R.id.btnexcluir);
            btneditar = (ImageView) itemView.findViewById(R.id.btneditar);
        }
    }
}
