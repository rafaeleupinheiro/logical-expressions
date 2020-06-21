package br.com.model;

public class Resultado {
  private String expressao;
  private Boolean check;

  public Resultado(String expressao, Boolean check) {
    this.expressao = expressao;
    this.check = check;
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
}
