package com.example.catalogapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ItemPage<T> {

  List<T> data;
  Pagination pagination;

}

