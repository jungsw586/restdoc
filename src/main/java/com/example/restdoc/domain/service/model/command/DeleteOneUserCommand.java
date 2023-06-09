package com.example.restdoc.domain.service.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteOneUserCommand {
  private String id;
}
