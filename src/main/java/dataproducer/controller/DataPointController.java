package dataproducer.controller;

import dataproducer.config.SwaggerConfig;
import dataproducer.dto.request.DataPointRequestDTO;
import dataproducer.dto.response.DataPointResponseDTO;
import dataproducer.service.DataPointService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(DataPointController.PATH_DATA_POINTS)
@Api(tags = SwaggerConfig.TAG_DATA_POINTS)
public class DataPointController {

    public static final String PATH_DATA_POINTS = "/data-points";

    private final DataPointService dataPointService;

    @Autowired
    public DataPointController(DataPointService dataPointService) {
        this.dataPointService = dataPointService;
    }

    @PostMapping(value = "")
    @ApiOperation(value = "${DataPointController.create}", response = DataPointResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Something went wrong")
    })
    public DataPointResponseDTO create(@ApiParam("Data Point Details") @RequestBody DataPointRequestDTO dataPointRequestDTO) {
        log.info("Handling POST request on path => {}", PATH_DATA_POINTS);
        log.info("Request body => {}", dataPointRequestDTO);

        final DataPointResponseDTO userResponseDto = dataPointService.handleCreate(dataPointRequestDTO);

        log.info("Response => {}", userResponseDto);
        return userResponseDto;
    }
}
