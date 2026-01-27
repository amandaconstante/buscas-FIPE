package com.consulta.fipe.projeto.view;

import com.consulta.fipe.projeto.model.Dados;
import com.consulta.fipe.projeto.model.Modelo;
import com.consulta.fipe.projeto.model.Veiculo;
import com.consulta.fipe.projeto.service.FipeService;
import com.google.gson.JsonSyntaxException;

import java.util.List;
import java.util.Scanner;

public class FipeView {
    private final FipeService fipeService;
    private static final Scanner scanner = new Scanner(System.in);

    public FipeView(FipeService fipeService) {
        this.fipeService = fipeService;
    }

    public void entrada() {
        System.out.println("-".repeat(30));
        System.out.println("CONSULTA TABELA FIPE");
        System.out.println("*** OPÇÕES ***");
        System.out.println("Carros\nMotos\nCaminhoes");
        System.out.println("Digite uma das opções para consultar valores: ");
        String opcaoVeiculo = scanner.nextLine().toLowerCase();

        try {
            List<Dados> marcas = exibirMarcas(opcaoVeiculo);
            String codMarca = getInputModelo(marcas);
            List<Dados> modelosFiltrados = exibirEfiltrarListaModelo(opcaoVeiculo, codMarca);
            consultarValores(modelosFiltrados, opcaoVeiculo, codMarca);
        } catch (JsonSyntaxException e) {
            System.out.println("Tipo inválido! Digite uma opção válida. [entrada]");
        }
    }

    private List<Dados> exibirMarcas(String opcaoVeiculo) {
        List<Dados> marcas = fipeService.obterMarcasVeiculo(opcaoVeiculo);
        marcas.forEach(m -> System.out.println("Cód.: " + m.codigo() + "\t\t\tDescr.: " + m.nome()));
        return marcas;
    }

    private void consultarValores(List<Dados> filtrado, String tipoVeiculo, String codMarca) {
        System.out.println("Digite o código do modelo para consultar valores: ");
        var codModelo = scanner.nextLine();

        if (fipeService.isCodigoValido(codModelo, filtrado)) {
            try {
                List<Veiculo> veiculos = fipeService.obterPrecos(tipoVeiculo, codMarca, codModelo);
                System.out.println("TODOS OS VEÍCULOS COM VALORES POR ANO: ");
                veiculos.forEach(System.out::println);
            } catch (JsonSyntaxException e) {
                throw new RuntimeException("Erro de leitura do servidor. ERRO: " + e.getMessage());
            }
        } else {
            System.out.println("Código inválido!");
        }
    }

    private List<Dados> reduzirListaModelos(Modelo modelos) {
        System.out.println("Digite um trecho do nome do veículo para consulta: ");
        String trechoDecr = scanner.nextLine();
        var modeloFiltrado = modelos.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(trechoDecr.toLowerCase()))
                .toList();
        if (modeloFiltrado.isEmpty()) {
            System.out.println("Nenhum modelo encontrado com esse trecho.");
        } else {
            modeloFiltrado.forEach(
                    m -> System.out.println("Cód.: " + m.codigo() + "\t\t\tDescr.: " + m.nome())
            );
        }
        return modeloFiltrado;
    }

    private List<Dados> exibirEfiltrarListaModelo(String opcaoVeiculo, String codMarca) {
        try {
            Modelo modelos = fipeService.obterModelos(opcaoVeiculo, codMarca);
            modelos.modelos().forEach(m -> System.out.println("Cód.: " + m.codigo() + "\t\t\tDescr.: " + m.nome()));
            return reduzirListaModelos(modelos);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Erro de requisição com o servidor. " + e.getMessage());
        }
    }

    private String getInputModelo(List<Dados> marcas) {
        while(true) {
            System.out.println("Digite o código do dados para consultar valores: ");
            String opModelo = scanner.nextLine();

            if (fipeService.isCodigoValido(opModelo, marcas)) {
                return opModelo;
            }
            System.out.println("Código inválido! Digite uma opção válida.[obterModeloVeiculo]");
        }
    }

}
