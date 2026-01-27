package com.consulta.fipe.projeto.service;

import com.consulta.fipe.projeto.model.Dados;
import com.consulta.fipe.projeto.model.Modelo;
import com.consulta.fipe.projeto.model.Veiculo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FipeService {

    private static final Gson gson = new GsonBuilder()
//            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create();
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String url = "https://parallelum.com.br/fipe/api/v1/";


    public List<Dados> obterMarcasVeiculo(String tipoVeiculo) {

        var address = url + tipoVeiculo + "/marcas";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            TypeToken<List<Dados>> collectionType = new TypeToken<List<Dados>>(){};

            List<Dados> marcas = gson.fromJson(response.body(), collectionType);

            return marcas;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public Boolean isCodigoValido(String codigo, List<Dados> dados) {
        return dados
                .stream()
                .anyMatch(m ->  m.codigo().trim().equalsIgnoreCase(codigo.trim()));
    }

    public Modelo obterModelos(String tipoVeiculo, String codMarca) {
        var address = url + tipoVeiculo + "/marcas/" + codMarca + "/modelos";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            Modelo modelos = gson.fromJson(response.body(), Modelo.class);

            return modelos;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Veiculo> obterPrecos(String tipoVeiculo, String codMarca, String codModelo) {
        var address = url + tipoVeiculo + "/marcas/" + codMarca + "/modelos/" + codModelo + "/anos";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            TypeToken<List<Dados>> collectionType = new TypeToken<List<Dados>>(){};

            List<Dados> dadosAnos = gson.fromJson(response.body(), collectionType);

            return dadosAnos.stream()
                    .map(a -> obterValorFinal(address, a.codigo()))
                    .toList();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Veiculo obterValorFinal(String address, String ano) {
        var consulta = address + "/" + ano;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(consulta))
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), Veiculo.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
