package br.com.model;

import java.util.ArrayList;
import java.util.List;

public class Resultado {
  private String expressao;
  private Boolean check;
  private List<Resultado> bifurcacaoEsquerda;
  private List<Resultado> bifurcacaoDireita;

  public Resultado(String expressao) {
    this.expressao = expressao;
    this.check = false;
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

  public List<Resultado> getBifurcacaoEsquerda() {
    if (bifurcacaoEsquerda == null) {
      bifurcacaoEsquerda = new ArrayList<>();
    }
    return bifurcacaoEsquerda;
  }

  public void setBifurcacaoEsquerda(List<Resultado> bifurcacaoEsquerda) {
    this.bifurcacaoEsquerda = bifurcacaoEsquerda;
  }

  public List<Resultado> getBifurcacaoDireita() {
    if (bifurcacaoDireita == null) {
      bifurcacaoDireita = new ArrayList<>();
    }
    return bifurcacaoDireita;
  }

  public void setBifurcacaoDireita(List<Resultado> bifurcacaoDireita) {
    this.bifurcacaoDireita = bifurcacaoDireita;
  }
}
