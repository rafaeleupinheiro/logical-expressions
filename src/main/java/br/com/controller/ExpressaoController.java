package br.com.controller;

import br.com.model.ExpressaoLogica;
import br.com.service.ExpressaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.Response;

@Controller
@RequestMapping(value = "/api")
public class ExpressaoController {
  @Autowired private ExpressaoService expressaoService;

  @ResponseBody
  @RequestMapping(
      value = "/",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response testaTautologia(@RequestBody ExpressaoLogica expressao) {
    expressaoService.processaExpressao(expressao);
    return Response.ok().build();
  }
}
