package br.com.expressaologicatautologia.model;

import java.util.LinkedList;
import java.util.List;

public class Node {
  private String valor;
  private List<Node> filhos;

  public Node() {
  }

  public Node(String valor) {
    this.valor = valor;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }

  public List<Node> getFilhos() {
    if (filhos == null) {
      filhos = new LinkedList<>();
    }
    return filhos;
  }

  public void setFilhos(List<Node> filhos) {
    this.filhos = filhos;
  }
}
