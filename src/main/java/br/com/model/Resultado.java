package br.com.model;

import java.util.List;

public class Resultado {
  private String expressao;
  private Boolean check;
  private Boolean fechado;
  private List<Resultado> resultados;

  public Resultado(String expressao) {
    this.expressao = expressao;
    this.check = false;
  }

  public Resultado(List<Resultado> resultados) {
    this.resultados = resultados;
  }

  public String getExpressao() {
    return expressao;
  }

  public void setExpressao(String expressao) {
    this.expressao = expressao;
  }

  public Boolean getCheck() {
    return check;
  }

  public void setCheck(Boolean check) {
    this.check = check;
  }

  public List<Resultado> getResultados() {
    return resultados;
  }

  public void setResultados(List<Resultado> resultados) {
    this.resultados = resultados;
  }

  public Boolean getFechado() {
    return fechado;
  }

  public void setFechado(Boolean fechado) {
    this.fechado = fechado;
  }
}
