package br.com.alelo.consumer.consumerpat.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PaginatedResponse<T> {
  private List<T> content;
  private int totalPages;
  private long totalElements;
}