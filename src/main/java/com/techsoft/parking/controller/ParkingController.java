package com.techsoft.parking.controller;

import com.techsoft.parking.controller.dto.ParkingCreateDTO;
import com.techsoft.parking.controller.dto.ParkingDTO;
import com.techsoft.parking.controller.mapper.ParkingMapper;
import com.techsoft.parking.domain.Parking;
import com.techsoft.parking.service.ParkingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/parking")
@Api(tags = "Parking Controller")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    @ApiOperation("Find all parking")
    public ResponseEntity<List<ParkingDTO>> findAll() {
        log.info("Request for get all parking");

        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @ApiOperation("Find parking by id")
    public ResponseEntity<ParkingDTO> findById(@PathVariable("id") String id) {
        log.info("Request for get parking by id: {}", id);

        Parking parking = parkingService.findById(id);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    @ApiOperation("Create new parking")
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO parkingCreateDTO) {
        log.info("Request for create Parking");

        Parking parkingCreate = parkingMapper.toParking(parkingCreateDTO);
        Parking parking = parkingService.create(parkingCreate);

        ParkingDTO result = parkingMapper.toParkingDTO(parking);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/update/{id}")
    @ApiOperation("Update exists parking")
    public ResponseEntity<ParkingDTO> update(@PathVariable("id") String id, @RequestBody ParkingCreateDTO parkingCreateDTO) {
        log.info("Request for update Parking");

        Parking parkingCreate = parkingMapper.toParking(parkingCreateDTO);
        Parking parking = parkingService.update(id, parkingCreate);

        ParkingDTO result = parkingMapper.toParkingDTO(parking);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete parking by id")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        log.info("Request for get parking by id: {}", id);

        parkingService.delete(id);

        return ResponseEntity.ok().build();
    }
}
