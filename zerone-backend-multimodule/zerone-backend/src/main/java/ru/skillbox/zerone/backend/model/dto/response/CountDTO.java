package ru.skillbox.zerone.backend.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Модель данных для возврата числового значения")
public class CountDTO {
  private long count;
}
