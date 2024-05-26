package com.example.atividade5_ac2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atividade5_ac2.R;
import com.example.atividade5_ac2.api.AlunoService;
import com.example.atividade5_ac2.api.ApiClient;
import com.example.atividade5_ac2.api.BuscaCepService;
import com.example.atividade5_ac2.model.Aluno;
import com.example.atividade5_ac2.model.Endereco;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlunoActivity extends AppCompatActivity {

    Button btnsalvar;

    TextView txtra, txtnome, txtcep, txtlogradouro, txtcidade, txtcomplemento, txtbairro, txtuf;
    AlunoService apiService;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        btnsalvar = (Button) findViewById(R.id.btnsalvar);
        apiService = ApiClient.getAlunoService();
        txtra = (TextView) findViewById(R.id.txtra);
        txtnome = (TextView) findViewById(R.id.txtnome);
        txtlogradouro = (TextView) findViewById(R.id.txtlogradouro);
        txtcep = (TextView) findViewById(R.id.txtcep);
        txtcidade = (TextView) findViewById(R.id.txtcidade);
        txtcomplemento = (TextView) findViewById(R.id.txtcomplemento);
        txtuf = (TextView) findViewById(R.id.txtuf);
        txtbairro = (TextView) findViewById(R.id.txtbairro);



        txtcep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 8) {
                    buscarEndereco(s.toString());
                }
            }
        });




        id = getIntent().getIntExtra("id", 0);
        if (id > 0) {
            apiService.getAlunoPorId(id).enqueue(new Callback<Aluno>() {
                @Override
                public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                    if (response.isSuccessful()) {
                        txtra.setText(String.valueOf(response.body().getRa()));
                        txtnome.setText(response.body().getNome());
                        txtcep.setText(response.body().getCep());
                        txtcidade.setText(response.body().getCidade());
                        txtbairro.setText(response.body().getBairro());
                        txtcomplemento.setText(response.body().getComplemento());
                        txtuf.setText(response.body().getUf());
                        txtlogradouro.setText(response.body().getLogradouro());

                    }
                }

                @Override
                public void onFailure(Call<Aluno> call, Throwable t) {
                    Log.e("Obter aluno", "Erro ao obter aluno");
                }

            });
        }

        btnsalvar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                int ra = Integer.parseInt(txtra.getText().toString());
                Aluno aluno = new Aluno();
                aluno.setRa(ra);
                aluno.setNome(txtnome.getText().toString());
                aluno.setCep(txtcep.getText().toString());
                aluno.setLogradouro(txtlogradouro.getText().toString());
                aluno.setComplemento(txtcomplemento.getText().toString());
                aluno.setCidade(txtcidade.getText().toString());
                aluno.setBairro(txtbairro.getText().toString());
                aluno.setUf(txtuf.getText().toString());

                if (id == 0)
                    inserirAluno(aluno);
                else {
                    aluno.setId(id);
                    editarAluno(aluno);
                }
            }
        });
    }
    private void buscarEndereco (String cep){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BuscaCepService service = retrofit.create(BuscaCepService.class);
        Call<Endereco> call = service.buscarEndereco(cep);

        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    Endereco endereco = response.body();
                    if (endereco != null) {
                        txtlogradouro.setText(endereco.getLogradouro());
                        txtcomplemento.setText(endereco.getComplemento());
                        txtbairro.setText(endereco.getBairro());
                        txtcidade.setText(endereco.getLocalidade());
                        txtuf.setText(endereco.getUf());
                    } else {
                        Toast.makeText(AlunoActivity.this, "Endereço não encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AlunoActivity.this, "Erro na resposta da API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(AlunoActivity.this, "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void inserirAluno(Aluno aluno) {
        Call<Aluno> call = apiService.postAluno(aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {

                    Aluno createdPost = response.body();
                    Toast.makeText(AlunoActivity.this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    Log.e("Inserir", "Erro ao criar: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Log.e("Inserir", "Erro ao criar: " + t.getMessage());
            }
        });
    }
    private void editarAluno(Aluno aluno) {
        Call<Aluno> call = apiService.putAluno(id,aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    // A solicitação foi bem-sucedida
                    Aluno createdPost = response.body();
                    Toast.makeText(AlunoActivity.this, "Editado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // A solicitação falhou
                    Log.e("Editar", "Erro ao editar: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                // Ocorreu um erro ao fazer a solicitação
                Log.e("Editar", "Erro ao editar: " + t.getMessage());
            }
        });
    }

}
