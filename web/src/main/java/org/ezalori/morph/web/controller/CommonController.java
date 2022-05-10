package org.ezalori.morph.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hey on 26/04/2018.
 */
@Controller
public class CommonController {
  @GetMapping(value = "/ping", produces = "text/plain")
  @ResponseBody
  public String ping() {
    return "pong";
  }
}
