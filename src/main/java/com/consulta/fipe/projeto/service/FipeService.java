package com.consulta.fipe.projeto.service;

import com.consulta.fipe.projeto.model.Modelo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FipeService {

    private final Gson gson = new GsonBuilder()
//            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create();
    private final HttpClient client = HttpClient.newHttpClient();

    public List<Modelo> consultaVeiculos(String tipoVeiculo) {

        var address = "https://parallelum.com.br/fipe/api/v1/" + tipoVeiculo + "/marcas";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            TypeToken<List<Modelo>> collectionType = new TypeToken<List<Modelo>>(){};

            List<Modelo> modelos = gson.fromJson(response.body(), collectionType);


            return modelos;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


}
