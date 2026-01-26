package com.consulta.fipe.projeto;

import com.consulta.fipe.projeto.service.FipeService;
import com.consulta.fipe.projeto.view.FipeView;

public class Main {
    public static void main(String[] args) {

        FipeService fipeService = new FipeService();
        FipeView fipeView = new FipeView(fipeService);

        fipeView.entrada();
    }
}
