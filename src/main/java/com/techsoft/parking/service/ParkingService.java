package com.techsoft.parking.service;

import com.techsoft.parking.common.error.HttpResponseException;
import com.techsoft.parking.domain.Parking;
import com.techsoft.parking.repository.ParkingRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Parking> findAll() {
        log.info("Find all parking");
        List<Parking> parkingList = parkingRepository.findAll();

        if(parkingList.isEmpty()) {
            log.info("Not possible find parking list!");

            throw new HttpResponseException(NOT_FOUND, "Not possible find parking list!");
        }

        return parkingList;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Parking findById(UUID id) {
        log.info("Find parking of id {}", id);

        Optional<Parking> optionalParking = parkingRepository.findById(id);

        if(optionalParking.isEmpty()) {
            log.info("Not possible find parking of id {}", id);

            throw new HttpResponseException(NOT_FOUND, String.format("Parking of id (%s) not found!", id));
        }

        return optionalParking.get();
    }

    @Transactional
    public Parking create(Parking parkingCreate) {
        try {
            log.info("Create parking in database!");

            parkingCreate.setEntryDate(LocalDateTime.now());
            return parkingRepository.save(parkingCreate);
        } catch (Exception ignored) {
            log.info("Error to create parking!");
            throw new HttpResponseException(BAD_REQUEST, "Error to create parking!");
        }
    }

    @Transactional
    public Parking update(UUID id, Parking parkingUpdate) {
        try {
            log.info("Update parking of id {}!", id);

            Parking parking = findById(id);

            parking.setColor(parkingUpdate.getColor());
            parking.setState(parkingUpdate.getState());
            parking.setModel(parkingUpdate.getModel());
            parking.setLicense(parkingUpdate.getLicense());

            return parkingRepository.save(parking);
        }catch (Exception ignored) {
            log.info("Error to update parking of id {}!", id);
            throw new HttpResponseException(BAD_REQUEST, String.format("Error to update parking of id %s!", id));
        }
    }

    @Transactional
    public void delete(UUID id) {
       try {
           log.info("Delete parking of id {}!", id);

           Parking parking = findById(id);
           parkingRepository.delete(parking);
       } catch (Exception e) {
           log.info("Error to delete parking of id {}!", id);
           throw new HttpResponseException(BAD_REQUEST, String.format("Error to delete parking of id %s!", id));
       }
    }

    @Transactional
    public Parking checkout(UUID id) {
      try {
          log.info("Perform checkout parking of id {}!", id);

          Parking parking = findById(id);
          parking.setExitDate(LocalDateTime.now());
          parking.setBill(ParkingCheckOut.getBill(parking));

          return parkingRepository.save(parking);
      }catch (Exception ignored) {
          log.info("Error to perform checkout parking of id {}!", id);
          throw new HttpResponseException(BAD_REQUEST, String.format("Error to perform checkout parking of id %s!", id));
      }
    }
}
