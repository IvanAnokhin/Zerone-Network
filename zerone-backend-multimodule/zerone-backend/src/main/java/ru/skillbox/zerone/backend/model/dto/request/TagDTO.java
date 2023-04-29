package ru.skillbox.zerone.backend.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import lombok.Data;
import lombok.experimental.Accessors;

import static java.lang.Long.MAX_VALUE;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {
  @Max(MAX_VALUE)
  private Long id;

  private String tag;
}