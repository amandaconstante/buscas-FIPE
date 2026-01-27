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
import java.util.List;

public class FipeService {

    private static final Gson gson = new GsonBuilder()
            .create();
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String url = "https://parallelum.com.br/fipe/api/v1/";


    public List<Dados> obterMarcasVeiculo(String tipoVeiculo) {
        var address = url + tipoVeiculo + "/marcas";
        String response = consomeAPI(address);
        TypeToken<List<Dados>> collectionType = new TypeToken<>() {
        };

        return gson.fromJson(response, collectionType);
    }


    public Boolean isCodigoValido(String codigo, List<Dados> dados) {
        return dados
                .stream()
                .anyMatch(m ->  m.codigo().trim().equalsIgnoreCase(codigo.trim()));
    }

    public Modelo obterModelos(String tipoVeiculo, String codMarca) {
        var address = url + tipoVeiculo + "/marcas/" + codMarca + "/modelos";
        String response = consomeAPI(address);
        return gson.fromJson(response, Modelo.class);
    }

    public List<Veiculo> obterPrecos(String tipoVeiculo, String codMarca, String codModelo) {
        var address = url + tipoVeiculo + "/marcas/" + codMarca + "/modelos/" + codModelo + "/anos";
        String response = consomeAPI(address);
        TypeToken<List<Dados>> collectionType = new TypeToken<>(){};
        List<Dados> dadosAnos = gson.fromJson(response, collectionType);

        return dadosAnos.stream()
                .map(a -> obterValorFinal(address, a.codigo()))
                .toList();
    }

    private Veiculo obterValorFinal(String address, String ano) {
        address += "/" + ano;
        String response = consomeAPI(address);
        return gson.fromJson(response, Veiculo.class);
    }

    private String consomeAPI(String address) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
