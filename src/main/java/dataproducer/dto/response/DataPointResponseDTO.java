package dataproducer.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataPointResponseDTO {

    @NotBlank
    @ApiModelProperty(position = 0)
    private String message;
}
