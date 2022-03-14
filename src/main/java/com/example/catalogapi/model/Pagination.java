package com.example.catalogapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pagination {
  long totalElements;
  int totalPages;
  int pageSize;
  int page;
}
