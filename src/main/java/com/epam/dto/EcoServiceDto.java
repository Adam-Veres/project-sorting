package com.epam.dto;

import com.epam.model.DeliveryOption;
import com.epam.model.PaymentCondition;
import com.epam.model.WasteType;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EcoServiceDto {

  private long id;
  @NotBlank private String serviceName;
  private Set<WasteType> typeOfWastes;
  private Set<PaymentCondition> paymentConditions;
  private Set<DeliveryOption> deliveryOptions;
  @NotNull private CoordinateDto coordinate;
  private String description;
  private Float rating;
  private List<CommentMessageDto> comments;
}
