package com.example.restdoc.domain.service.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserCommand {
  private String id;
  private String name;
}
