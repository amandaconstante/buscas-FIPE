package com.consulta.fipe.projeto.model;

import com.google.gson.annotations.SerializedName;

public record Veiculo(//@SerializedName("TipoVeiculo") int tipoVeiculo,
                      @SerializedName("Valor") String valor,
                      @SerializedName("Marca") String marca,
                      @SerializedName("Modelo") String modelo,
                      @SerializedName("AnoModelo") Long anoModelo,
                      @SerializedName("Combustivel") String combustivel,
                      @SerializedName("CodigoFipe") String codigoFipe,
                      @SerializedName("MesReferencia") String mesReferencia) {
}
