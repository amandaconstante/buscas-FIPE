package com.consulta.fipe.projeto;

import com.consulta.fipe.projeto.service.ConsumoApi;
import com.consulta.fipe.projeto.service.ConverteDados;
import com.consulta.fipe.projeto.service.FipeService;
import com.consulta.fipe.projeto.view.FipeView;

public class Main {
    public static void main(String[] args) {
        ConverteDados converteDados = new ConverteDados();
        ConsumoApi consumoApi = new ConsumoApi();
        FipeService fipeService = new FipeService(consumoApi, converteDados);
        FipeView fipeView = new FipeView(fipeService);

        fipeView.entrada();
    }
}
