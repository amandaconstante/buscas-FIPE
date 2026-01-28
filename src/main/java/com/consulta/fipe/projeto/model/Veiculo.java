package com.consulta.fipe.projeto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(@JsonProperty("Valor") String valor,
                      @JsonProperty("Marca") String marca,
                      @JsonProperty("Modelo") String modelo,
                      @JsonProperty("AnoModelo") Long ano,
                      @JsonProperty("Combustivel") String combustivel,
                      @JsonProperty("CodigoFipe") String codigoFipe,
                      @JsonProperty("MesReferencia") String mesReferencia) {
}
