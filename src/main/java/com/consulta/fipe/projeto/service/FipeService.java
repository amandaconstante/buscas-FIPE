package com.consulta.fipe.projeto.service;

import com.consulta.fipe.projeto.model.Dados;
import com.consulta.fipe.projeto.model.Modelos;
import com.consulta.fipe.projeto.model.Veiculo;

import java.util.List;

public class FipeService {
    private final ConsumoApi consomeAPI;
    private final ConverteDados converteDados;

    private static final String url = "https://parallelum.com.br/fipe/api/v1/";

    public FipeService(ConsumoApi consomeApi, ConverteDados converteDados) {
        this.consomeAPI = consomeApi;
        this.converteDados = converteDados;
    }

    public List<Dados> obterMarcasVeiculoByTipoVeiculo(String tipoVeiculo) {
        var address = url + tipoVeiculo + "/marcas";
        String response = consomeAPI.obterDados(address);

        return converteDados.obterLista(response, Dados.class);
    }

    public Boolean isCodigoValido(String codigo, List<Dados> dados) {
        return dados
                .stream()
                .anyMatch(m ->  m.codigo().trim().equalsIgnoreCase(codigo.trim()));
    }

    public Modelos obterModelos(String tipoVeiculo, String codMarca) {
        var address = url + tipoVeiculo + "/marcas/" + codMarca + "/modelos";
        String response = consomeAPI.obterDados(address);

        return converteDados.obterDados(response, Modelos.class);
    }

    public List<Veiculo> obterPrecos(String tipoVeiculo, String codMarca, String codModelo) {
        var address = url + tipoVeiculo + "/marcas/" + codMarca + "/modelos/" + codModelo + "/anos";
        String response = consomeAPI.obterDados(address);

        List<Dados> anos = converteDados.obterLista(response, Dados.class);

        return anos.stream()
                .map(a -> obterValorFinal(address, a.codigo()))
                .toList();
    }

    private Veiculo obterValorFinal(String address, String ano) {
        address += "/" + ano;
        String response = consomeAPI.obterDados(address);

        return converteDados.obterDados(response, Veiculo.class);
    }
}
