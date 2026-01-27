package com.consulta.fipe.projeto.model;

import java.math.BigDecimal;

public record Veiculo(int tipoVeiculo,
                      BigDecimal valor,
//                      String marca,
                      Marca marca,
                      Long AnoModelo,
                      String combustivel,
                      String codigoFipe, String
                      mesReferencia) {
}
