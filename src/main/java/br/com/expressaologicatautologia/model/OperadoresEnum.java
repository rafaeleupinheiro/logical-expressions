package br.com.expressaologicatautologia.model;

import java.util.List;

public enum OperadoresEnum {
  DUPLOIMPLICA("="),
  IMPLICA(">"),
  OU("|"),
  E("&"),
  NEGACAO("!");

  private String valor;

  OperadoresEnum(String valor) {
    this.valor = valor;
  }

  public String getValor() {
    return valor;
  }

  public static String getOperadorMaiorPrioridade(List<String> lista) {
    for(OperadoresEnum operadoresEnum : OperadoresEnum.values()) {
      for (String operador : lista) {
        if (operadoresEnum.getValor().equals(operador)) {
          return operador;
        }
      }
    }
    return null;
  }
}
