package com.techsoft.parking.mapper;

import com.techsoft.parking.dto.form.ParkingCreateDTO;
import com.techsoft.parking.dto.ParkingDTO;
import com.techsoft.parking.domain.Parking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkingMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public ParkingDTO toParkingDTO(Parking parking) {
        return MODEL_MAPPER.map(parking, ParkingDTO.class);
    }

    public List<ParkingDTO> toParkingDTOList(List<Parking> parkingList) {
        return parkingList.stream().map(this::toParkingDTO).collect(Collectors.toList());
    }

    public Parking toParking(ParkingDTO parkingDTO) {
        return MODEL_MAPPER.map(parkingDTO, Parking.class);
    }

    public Parking toParking(ParkingCreateDTO parkingDTO) {
        return MODEL_MAPPER.map(parkingDTO, Parking.class);
    }
}
