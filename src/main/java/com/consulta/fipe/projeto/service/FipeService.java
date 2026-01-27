package com.consulta.fipe.projeto.service;

import com.consulta.fipe.projeto.model.Marca;
import com.consulta.fipe.projeto.model.Modelo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class FipeService {

    private static final Gson gson = new GsonBuilder()
//            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create();
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String url = "https://parallelum.com.br/fipe/api/v1/";


    public List<Marca> obterMarcasVeiculo(String tipoVeiculo) {

        var address = url + tipoVeiculo + "/marcas";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            TypeToken<List<Marca>> collectionType = new TypeToken<List<Marca>>(){};

            List<Marca> marcas = gson.fromJson(response.body(), collectionType);

            return marcas;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public Boolean isCodigoValido(String opCodMarca, List<Marca> marcas) {
        return marcas
                .stream()
                .anyMatch(m -> {
                    System.out.println("Comparando: [" + m.codigo() + "] com [" + opCodMarca + "]");
                    return m.codigo().trim().equalsIgnoreCase(opCodMarca.trim());
                });
    }

    public Modelo obterModelos(String tipoVeiculo, String codigoModelo) {
        var address = url + tipoVeiculo + "/marcas/" + codigoModelo + "/modelos";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();

        System.out.println("endereço: " + address);
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

//            TypeToken<List<Marca>> collectionType = new TypeToken<List<Marca>>(){};

            Modelo modelos = gson.fromJson(response.body(), Modelo.class);

            return modelos;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
