package dataproducer.service;

import dataproducer.dto.request.DataPointRequestDTO;
import dataproducer.dto.response.DataPointResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataPointService {

    public DataPointResponseDTO create(DataPointRequestDTO dataPointRequestDTO) {
        return DataPointResponseDTO.builder().message("Data point created successfully").build();
    }
}