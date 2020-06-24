package br.com.expressaologicatautologia.processador;

import br.com.expressaologicatautologia.model.ExpressaoLogica;
import br.com.expressaologicatautologia.model.OperadoresEnum;
import br.com.expressaologicatautologia.model.Resultado;

import java.util.ArrayList;
import java.util.List;

public class ExpressaoProcessador {
  List<Resultado> resultados;
  List<String> variaveis;
  Boolean naoEhTautologia;

  public void processaExpressao(ExpressaoLogica expressaoLogica) {
    this.naoEhTautologia = true;
    this.variaveis = new ArrayList<>();
    this.resultados = new ArrayList<>();
    this.resultados.add(new Resultado(expressaoLogica.getExpressao()));
    processador(this.resultados);
    provaTautologia(this.resultados);
    if (this.naoEhTautologia) {
      System.out.println(expressaoLogica.getExpressao() + " - Não é uma tautologia!!!");
    } else {
      System.out.println(expressaoLogica.getExpressao() + " - É uma tautologia!!!");
    }
  }

  private void processador(List<Resultado> resultados) {
    String expressao = "";
    Resultado resultado = null;
    for (int i = 0; i < resultados.size(); i++) {
      resultado = resultados.get(i);
      if (!resultado.getCheck()) {
        expressao = resultado.getExpressao();
        resultado.setCheck(true);
        System.out.println(expressao);
        break;
      }
    }

    if (expressao.length() > 2) {
      List<String> lista = processaString(expressao);
      lista = processaLista(lista);
      verificaOrdemPrecedencia(lista, resultados);
    }

    if (resultado.equals(getUltimoResultado(resultados))) {
      if (!resultado.getBifurcacaoEsquerda().isEmpty() && !resultado.getBifurcacaoEsquerda().isEmpty()) {
        processador(resultado.getBifurcacaoEsquerda());
        processador(resultado.getBifurcacaoDireita());
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
      if (expr.contains("(")) {
        if (expr.charAt(0) == '(' && expr.charAt(expr.length() - 1) == ')' && expr.length() <= 7) {
          expr = expr.replace("(", "");
          expr = expr.replace(")", "");
          aux.add(expr);
        } else {
          aux.add(expr);
        }
      } else {
        aux.add(expr);
      }
    }
    return aux;
  }

  private void verificaOrdemPrecedencia(List<String> lista, List<Resultado> resultados) {
    if (lista.get(1).equals(OperadoresEnum.DUPLOIMPLICA.getValor())) {
      processaDuploImplica(lista, resultados);
    } else if (lista.get(1).equals(OperadoresEnum.IMPLICA.getValor())) {
      processaImplica(lista, resultados);
    } else if (lista.get(1).equals(OperadoresEnum.OU.getValor())) {
      processaOU(lista, resultados);
    } else if (lista.get(1).equals(OperadoresEnum.E.getValor())) {
      processaE(lista, resultados);
    } else if (lista.get(1).equals(OperadoresEnum.NEGACAO.getValor())) {
      processaNegacao(lista, resultados);
    }
  }

  private void processaDuploImplica(List<String> lista, List<Resultado> resultados) {
    if (lista.get(3).equals("V")) {
      Resultado bifurcacaoEsquerda = new Resultado(lista.get(0) + "V");
      Resultado bifurcacaoDireita = new Resultado(lista.get(0) + "F");
      adicionaBifurcacao(getUltimoResultado(resultados), bifurcacaoEsquerda, bifurcacaoDireita);
      adicionaProlongamento(getUltimoResultado(resultados).getBifurcacaoEsquerda(), new Resultado(lista.get(2) + "V"));
      adicionaProlongamento(getUltimoResultado(resultados).getBifurcacaoDireita(), new Resultado(lista.get(2) + "F"));
    } else {
      Resultado bifurcacaoEsquerda = new Resultado(lista.get(0) + "V");
      Resultado bifurcacaoDireita = new Resultado(lista.get(0) + "F");
      adicionaBifurcacao(getUltimoResultado(resultados), bifurcacaoEsquerda, bifurcacaoDireita);
      adicionaProlongamento(getUltimoResultado(resultados).getBifurcacaoEsquerda(), new Resultado(lista.get(2) + "F"));
      adicionaProlongamento(getUltimoResultado(resultados).getBifurcacaoDireita(), new Resultado(lista.get(2) + "V"));
    }
  }

  private void processaImplica(List<String> lista, List<Resultado> resultados) {
    if (lista.get(3).equals("V")) {
      Resultado bifurcacaoEsquerda = new Resultado(lista.get(0) + "F");
      Resultado bifurcacaoDireita = new Resultado(lista.get(2) + "V");
      adicionaBifurcacao(getUltimoResultado(resultados), bifurcacaoEsquerda, bifurcacaoDireita);
    } else {
      adicionaProlongamento(resultados, new Resultado(lista.get(0) + "V"));
      adicionaProlongamento(resultados, new Resultado(lista.get(2) + "F"));
    }
  }

  private void processaOU(List<String> lista, List<Resultado> resultados) {
    if (lista.get(3).equals("V")) {
      Resultado bifurcacaoEsquerda = new Resultado(lista.get(0) + "V");
      Resultado bifurcacaoDireita = new Resultado(lista.get(2) + "V");
      adicionaBifurcacao(getUltimoResultado(resultados), bifurcacaoEsquerda, bifurcacaoDireita);
    } else {
      adicionaProlongamento(resultados, new Resultado(lista.get(0) + "F"));
      adicionaProlongamento(resultados, new Resultado(lista.get(2) + "F"));
    }
  }

  private void processaE(List<String> lista, List<Resultado> resultados) {
    if (lista.get(3).equals("V")) {
      adicionaProlongamento(resultados, new Resultado(lista.get(0) + "V"));
      adicionaProlongamento(resultados, new Resultado(lista.get(2) + "V"));
    } else {
      Resultado bifurcacaoEsquerda = new Resultado(lista.get(0) + "F");
      Resultado bifurcacaoDireita = new Resultado(lista.get(2) + "F");
      adicionaBifurcacao(getUltimoResultado(resultados), bifurcacaoEsquerda, bifurcacaoDireita);
    }
  }

  private void processaNegacao(List<String> lista, List<Resultado> resultados) {
    if (lista.get(3).equals("V")) {
      adicionaProlongamento(resultados, new Resultado(lista.get(2) + "F"));
    } else {
      adicionaProlongamento(resultados, new Resultado(lista.get(2) + "V"));
    }
  }

  private void adicionaProlongamento(List<Resultado> resultados, Resultado resultado) {
    resultados.add(resultado);
  }

  private void adicionaBifurcacao(Resultado ultimoResultado, Resultado bifurcacaoEsquerda, Resultado
      bifurcacaoDireita) {
    if (ultimoResultado.getBifurcacaoEsquerda().isEmpty() && ultimoResultado.getBifurcacaoEsquerda().isEmpty()) {
      ultimoResultado.getBifurcacaoEsquerda().add(bifurcacaoEsquerda);
      ultimoResultado.getBifurcacaoDireita().add(bifurcacaoDireita);
    } else {
      bifurcacaoEsquerda = new Resultado(bifurcacaoEsquerda.getExpressao());
      bifurcacaoDireita = new Resultado(bifurcacaoDireita.getExpressao());
      adicionaBifurcacao(getUltimoResultado(ultimoResultado.getBifurcacaoEsquerda()), bifurcacaoEsquerda, bifurcacaoDireita);
      bifurcacaoEsquerda = new Resultado(bifurcacaoEsquerda.getExpressao());
      bifurcacaoDireita = new Resultado(bifurcacaoDireita.getExpressao());
      adicionaBifurcacao(getUltimoResultado(ultimoResultado.getBifurcacaoDireita()), bifurcacaoEsquerda, bifurcacaoDireita);
    }
  }

  private Resultado getUltimoResultado(List<Resultado> aux) {
    return aux.get(aux.size() - 1);
  }

  private void provaTautologia(List<Resultado> resultados) {
    for (Resultado resultado : resultados) {
      this.naoEhTautologia = true;
      String expressao = resultado.getExpressao();
      if (expressao.length() == 2) {
        String variavel = expressao.charAt(0) + "";
        char valor = expressao.charAt(1);
        if (valor == 'V') {
          valor = 'F';
        } else {
          valor = 'V';
        }
        variavel = variavel + valor;
        if (!variaveis.contains(variavel)) {
          variaveis.add(expressao);
        } else {
          this.naoEhTautologia = false;
          break;
        }
      }
      if (resultado.equals(getUltimoResultado(resultados))) {
        if (!resultado.getBifurcacaoEsquerda().isEmpty() && !resultado.getBifurcacaoEsquerda().isEmpty()) {
          provaTautologia(resultado.getBifurcacaoEsquerda());
          if (this.naoEhTautologia) {
            return;
          }
          provaTautologia(resultado.getBifurcacaoDireita());
          if (this.naoEhTautologia) {
            return;
          }
        }
      }
    }
  }
}
