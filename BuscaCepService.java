package com.example.atividade5_ac2.api;

import com.example.atividade5_ac2.model.Endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BuscaCepService {

        @GET("{cep}/json/")
        Call<Endereco> buscarEndereco(@Path("cep") String cep);
    }


