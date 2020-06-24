package br.com.expressaologicatautologia;

import br.com.expressaologicatautologia.model.ExpressaoLogica;
import br.com.expressaologicatautologia.processador.ExpressaoProcessador;

/**
 * Duplo Implica      =
 * Implica            >
 * OU                 |
 * E                  &
 * Negacao            !
 * Falso              F
 * Verdadeiro         V
 */
public class App {
  public static void main(String[] args) {
    ExpressaoLogica expressaoLogica = new ExpressaoLogica("(p>q)&(q>r)>(p>r)F");    // Tautologia
//    ExpressaoLogica expressaoLogica = new ExpressaoLogica("p|!pF");                 // Tautologia
//    ExpressaoLogica expressaoLogica = new ExpressaoLogica("(p&q)>(p=q)F");          // Tautologia
//    ExpressaoLogica expressaoLogica = new ExpressaoLogica("p&!pF");                 // Não é uma tautologia
//    ExpressaoLogica expressaoLogica = new ExpressaoLogica("(p|!q)>(p&q)F");         // Não é uma tautologia
    new ExpressaoProcessador().processaExpressao(expressaoLogica);
  }
}
