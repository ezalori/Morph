package org.ezalori.morph.web.model;

import java.util.Date;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {
  @Id
  private int id;
  private String username;
  private String password;
  private Date createdAt;
  private Date updatedAt;
}
