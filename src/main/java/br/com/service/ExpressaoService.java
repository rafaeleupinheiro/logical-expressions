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
  List<String> variaveis = new ArrayList<>();

  public void processaExpressao(ExpressaoLogica expressaoLogica) {
    resultados.add(new Resultado(expressaoLogica.getExpressao()));
    processador(resultados);
    provaTautologia(resultados);
  }

  private void processador(List<Resultado> resultados) {
    String expressao = "";
    Resultado resultado = null;
    for (int i = 0; i < resultados.size(); i++) {
      resultado = resultados.get(i);
      if (!resultado.getCheck()) {
        expressao = resultado.getExpressao();
        resultado.setCheck(true);
        break;
      }
    }

    if (expressao.length() != 2) {
      List<String> lista = processaString(expressao);
      lista = processaLista(lista);
      verificaOrdemPrecedencia(lista, expressao);
    }

    if (resultado.equals(resultados.get(resultados.size() - 1))) {
      if (resultado.getResultados() != null) {
        processador(resultado.getResultados());
      }
    } else {
      processador(resultados);
    }
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
      List<Resultado> aux = new ArrayList<>();
      aux.add(new Resultado(lista.get(0) + "F"));
      aux.add(new Resultado(lista.get(2) + "V"));
      adiciona(aux, null);
    } else {
      adiciona(null, new Resultado(lista.get(0) + "V"));
      adiciona(null, new Resultado(lista.get(2) + "F"));
    }
  }

  private void processaE(List<String> lista, String expressao) {
    if (lista.get(3).equals("V")) {
      adiciona(null, new Resultado(lista.get(0) + "V"));
      adiciona(null, new Resultado(lista.get(2) + "V"));
    } else {
      List<Resultado> aux = new ArrayList<>();
      aux.add(new Resultado(lista.get(0) + "F"));
      aux.add(new Resultado(lista.get(2) + "V"));
      adiciona(aux, null);
    }
  }

  /*private void setCheck(String expr, List<Resultado> result) {
    for (Resultado resultado : result) {
      if (resultado.getExpressao().equals(expr) && !resultado.getCheck()) {
        resultado.setCheck(true);
      }
    }
  }*/

//  private void setCheck2(String expr, List<Resultado> result) {
//    for (Resultado resultado : result) {
//      if (resultado.getExpressao() == null && resultado.getResultados() != null) {
//        setCheck(expr, resultado.getResultados());
//      } else if (resultado.getExpressao().equals(expr) && !resultado.getCheck()) {
//        resultado.setCheck(true);
//      }
//    }
//  }

  private void adiciona(List<Resultado> lista, Resultado resultado) {
    if (lista == null) {
      resultados.add(resultado);
    } else {
      resultado = resultados.get(resultados.size() - 1);
      if (resultado != null && resultado.getResultados() == null) {
        resultado.setResultados(lista);
      } else {
        for (Resultado aux : resultado.getResultados()) {
          aux.setResultados(lista);
        }
      }
    }
  }

  private void provaTautologia(List<Resultado> resultados) {
    for (Resultado resultado : resultados) {
      String expressao = resultado.getExpressao();
      if (expressao.length() == 2) {
        variaveis.add(expressao);
      }
      if (resultado.getResultados() != null) {
        provaTautologia(resultado.getResultados());
      } else {
        System.out.println();
      }
    }
  }

  private void provaTautologia2(List<Resultado> resultados) {
    for (Resultado resultado : resultados) {
      String expressao = resultado.getExpressao();
      if (expressao.length() == 2) {
        variaveis.add(expressao);
      }
      if (resultado.equals(resultados.get(resultados.size() - 1))) {
        if (resultado.getResultados() != null) {
          provaTautologia(resultado.getResultados());
        } else {
          System.out.println();
        }
      }
    }
  }
}
