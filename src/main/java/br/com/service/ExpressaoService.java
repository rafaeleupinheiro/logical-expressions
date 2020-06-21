package br.com.service;

import br.com.model.ExpressaoLogica;
import br.com.model.OperadoresEnum;
import br.com.model.Resultado;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpressaoService {
  List<Resultado> resultados = new ArrayList<>();

  public void processaExpressao(ExpressaoLogica expressaoLogica) {
    resultados.add(new Resultado(expressaoLogica.getExpressao(), false));
    processador();
  }

  private void processador() {
    String expressao = "";
    for (Resultado resultado : resultados) {
      if (!resultado.getCheck()) {
        expressao = resultado.getExpressao();
        break;
      }
    }
    List<String> lista = processaString(expressao);
    lista = processaLista(lista);
    verificaOrdemPrecedencia(lista, expressao);
    processador();
  }

  private List<String> processaString(String expressao) {
    String aux = "";
    Boolean parenteses = false;
    List<String> lista = new ArrayList<>();
    for (int i = 0; i < expressao.length(); i++) {
      Character c = expressao.charAt(i);
      if (c == ')') {
        parenteses = false;
        aux = aux + c;
        lista.add(aux);
        aux = "";
      } else if (c == '(' || parenteses) {
        parenteses = true;
        aux = aux + c;
      } else {
        lista.add(c.toString());
      }
    }
    return lista;
  }


  private List<String> processaStringAux(String expressao) {
    String aux = "";
    Boolean parenteses = false;
    List<String> lista = new ArrayList<>();
    for (int i = 0; i < expressao.length(); i++) {
      Character c = expressao.charAt(i);
      if (parenteses || c == 'V' || c == 'F') {
        lista.add(c.toString());
        parenteses = false;
      } else {
        aux = aux + c;
      }
      if (c == ')') {
        lista.add(aux);
        aux = "";
        parenteses = true;
      }
    }
    return lista;
  }

  private List<String> processaLista(List<String> expressoes) {
    List<String> lista = new ArrayList<>();
    Boolean flag = false;
    String expressao = "";
    String operadorMaiorPrioridade = OperadoresEnum.getOperadorMaiorPrioridade(expressoes);
    for (int i = 0; i < expressoes.size(); i++) {
      String expr = expressoes.get(i);
      if (!flag && expr.equals(operadorMaiorPrioridade)) {
        flag = true;
        lista.add(expressao);
        lista.add(expr);
        expressao = "";
      } else if (expr.equals("V") || expr.equals("F")) {
        lista.add(expressao);
        lista.add(expr);
      } else {
        expressao = expressao + expr;
      }
    }

    List<String> aux = new ArrayList<>();
    for (String expr : lista) {
      if (expr.contains("(") && expr.length() == 5) {
        expr = expr.replace("(", "");
        expr = expr.replace(")", "");
        aux.add(expr);
      } else {
        aux.add(expr);
      }
    }

    return aux;
  }

  private void verificaOrdemPrecedencia(List<String> lista, String expressao) {
    if (lista.get(1).equals(OperadoresEnum.DUPLOIMPLICA.getValor())) {

    } else if (lista.get(1).equals(OperadoresEnum.IMPLICA.getValor())) {
      processaImplica(lista, expressao);
    } else if (lista.get(1).equals(OperadoresEnum.NEGACAO.getValor())) {

    } else if (lista.get(1).equals(OperadoresEnum.OU.getValor())) {

    } else if (lista.get(1).equals(OperadoresEnum.E.getValor())) {
      processaE(lista, expressao);
    }
  }

  private void processaImplica(List<String> lista, String expressao) {
    if (lista.get(3).equals("V")) {

    } else {
      this.resultados.add(new Resultado(lista.get(0) + "V", false));
      this.resultados.add(new Resultado(lista.get(2) + "F", false));
    }

    for (Resultado resultado : resultados) {
      if (resultado.getExpressao().equals(expressao)) {
        resultado.setCheck(true);
      }
    }
  }

  private void processaE(List<String> lista, String expressao) {
    if (lista.get(3).equals("V")) {
      this.resultados.add(new Resultado(lista.get(0) + "V", false));
      this.resultados.add(new Resultado(lista.get(2) + "V", false));
    } else {

    }

    for (Resultado resultado : resultados) {
      if (resultado.getExpressao().equals(expressao)) {
        resultado.setCheck(true);
      }
    }
  }
}
