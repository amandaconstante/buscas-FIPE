package com.consulta.fipe.projeto.model;

import java.math.BigDecimal;

public record Veiculo(int tipoVeiculo,
                      BigDecimal valor,
                      String marca,
                      Modelo modelo,
                      Long AnoModelo,
                      String combustivel,
                      String codigoFipe, String
                      mesReferencia) {
}
