package org.ezalori.morph.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hey on 26/04/2018.
 */
@Controller
public class BasicController {
  @RequestMapping(value = "/ping")
  @ResponseBody
  public Object ping() {
    return "pong";
  }
}
