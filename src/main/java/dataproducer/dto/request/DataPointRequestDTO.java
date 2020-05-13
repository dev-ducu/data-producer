package dataproducer.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataPointRequestDTO {

    @NotBlank
    @ApiModelProperty(position = 0)
    private String publisher;

    @NotBlank
    @ApiModelProperty(position = 1)
    private List<Integer> readings;
}
