package com.consulta.fipe.projeto.view;

import com.consulta.fipe.projeto.model.Dados;
import com.consulta.fipe.projeto.model.Modelos;
import com.consulta.fipe.projeto.model.Veiculo;
import com.consulta.fipe.projeto.service.FipeService;

import java.util.Comparator;
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

        List<Dados> marcas = exibirMarcas(opcaoVeiculo);
        String codMarca = getCodigoMarca(marcas);
        List<Dados> modelosFiltrados = exibirEobterModelosFiltrado(opcaoVeiculo, codMarca);
        consultarValores(modelosFiltrados, opcaoVeiculo, codMarca);
    }

    private List<Dados> exibirEobterModelosFiltrado(String opcaoVeiculo, String codMarca) {
        Modelos modelos = fipeService.obterModelos(opcaoVeiculo, codMarca);
        modelos.modelos()
                .stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(m -> System.out.println("Cód.: " + m.codigo() + "\t\t\tDescr.: " + m.nome()));
        return reduzirListaModelos(modelos);
    }

    private List<Dados> exibirMarcas(String opcaoVeiculo) {
        List<Dados> marcas = fipeService.obterMarcasVeiculoByTipoVeiculo(opcaoVeiculo);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(m -> System.out.println("Cód.: " + m.codigo() + "\t\t\tDescr.: " + m.nome()));
        return marcas;
    }

    private void consultarValores(List<Dados> filtrado, String tipoVeiculo, String codMarca) {
        System.out.println("Digite o código do modelo para consultar valores: ");
        var codModelo = scanner.nextLine();

        if (fipeService.isCodigoValido(codModelo, filtrado)) {
            List<Veiculo> veiculos = fipeService.obterPrecos(tipoVeiculo, codMarca, codModelo);
            System.out.println("TODOS OS VEÍCULOS COM VALORES POR ANO: ");
            veiculos.forEach(System.out::println);
        } else {
            System.out.println("Código inválido!");
        }
    }

    private List<Dados> reduzirListaModelos(Modelos modelos) {
        System.out.println("Digite um trecho do nome do veículo para consulta: ");
        String trechoDecr = scanner.nextLine();
        var modeloFiltrado = modelos.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(trechoDecr.toLowerCase()))
                .toList();
        if (modeloFiltrado.isEmpty()) {
            System.out.println("Nenhum modelo encontrado com esse trecho.");
        } else {
            modeloFiltrado
                    .stream()
                    .sorted(Comparator.comparing(Dados::codigo))
                    .forEach(
                    m -> System.out.println("Cód.: " + m.codigo() + "\t\t\tDescr.: " + m.nome())
            );
        }
        return modeloFiltrado;
    }

    private String getCodigoMarca(List<Dados> marcas) {
        while(true) {
            System.out.println("Digite o código da marca: ");
            String codMarca = scanner.nextLine();

            if (fipeService.isCodigoValido(codMarca, marcas)) return codMarca;

            System.out.println("Código inválido! Digite uma opção válida.[obterModeloVeiculo]");
        }
    }

}
