package com.consulta.fipe.projeto.view;

import com.consulta.fipe.projeto.model.Marca;
import com.consulta.fipe.projeto.model.Modelo;
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
        String opcaoVeiculo = scanner.nextLine();

        try {
            List<Marca> marcas = fipeService.obterMarcasVeiculo(opcaoVeiculo);
            for(Marca marca : marcas) {
                System.out.println("Cód.: " + marca.codigo() + "\t\t\tDescr.: " + marca.nome());
            }
            obterModeloVeiculo(opcaoVeiculo, marcas);
            System.out.println("voltou nesse ponto qui.");
//            reduzirListaModelos(modelos);
        } catch (JsonSyntaxException e) {
            System.out.println("Tipo inválido! Digite uma opção válida. [entrada]");
        }
    }

    private void reduzirListaModelos(Modelo modelos) {
        System.out.println("Digite um trecho do nome do veículo para consulta: ");
        String trechoDecr = scanner.nextLine();
        var modeloFiltrado = modelos.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(trechoDecr.toLowerCase()))
                .toList();
        if (modeloFiltrado.isEmpty()) {
            System.out.println("Nenhum modelo encontrado com esse trecho!");
        } else {
            modeloFiltrado.forEach(
                    m -> System.out.println("Cód.: " + m.codigo() + "\t\t\tDescr.: " + m.nome())
            );
        }
    }

    private void obterModeloVeiculo(String opcaoVeiculo, List<Marca> marcas) {
        String opcaoModelo = getInputModelo(marcas);
        try {
            Modelo modelos = fipeService.obterModelos(opcaoVeiculo, opcaoModelo);
            System.out.println("aqui em obterModeloVeiculo...");
            modelos.modelos().forEach(m -> System.out.println("Cód.: " + m.codigo() + "\t\t\tDescr.: " + m.nome()));
            reduzirListaModelos(modelos);
        } catch (Exception e) {
            throw new RuntimeException("Erro de requisição com o servidor! " + e.getMessage());
        }
    }

    private String getInputModelo(List<Marca> marcas) {
        while(true) {
            System.out.println("Digite o código do marca para consultar valores: ");
            String opModelo = scanner.nextLine();

            if (fipeService.isCodigoValido(opModelo, marcas)) {
                System.out.println("vai sair daqui...");
                return opModelo;
            }
            System.out.println("Código inválido! Digite uma opção válida.[obterModeloVeiculo]");
        }
    }

}
