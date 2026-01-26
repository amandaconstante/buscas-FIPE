package com.consulta.fipe.projeto.view;

import com.consulta.fipe.projeto.model.Modelo;
import com.consulta.fipe.projeto.service.FipeService;
import com.google.gson.JsonSyntaxException;

import java.util.List;
import java.util.Scanner;

public class FipeView {
    private FipeService fipeService;
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
            List<Modelo> modelos = fipeService.consultaVeiculos(opcaoVeiculo);
            System.out.println("na view...");
            System.out.println(modelos);
        } catch (JsonSyntaxException e) {
            System.out.println("Tipo inválido! Digite uma opção válida.");
        }
    }

}
