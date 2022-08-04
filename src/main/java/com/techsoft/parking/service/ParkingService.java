package com.techsoft.parking.service;

import com.techsoft.parking.common.error.HttpResponseException;
import com.techsoft.parking.domain.Parking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ParkingService {

    private static Map<String, Parking> parkingMap = new HashMap<>();

    static {
        String firstId = getUUID();
        String secondId = getUUID();

        Parking firstParking = new Parking(
                firstId, "DMS-1111", "SC", "CELTA", "PRETO", null, null, null
        );
        Parking secondParking = new Parking(
                secondId, "DWA-1541", "SP", "VW GOL", "VERMELHO", null, null, null
        );

        parkingMap.put(firstId, firstParking);
        parkingMap.put(secondId, secondParking);
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public List<Parking> findAll() {
        log.info("Find all parking");
        return new ArrayList<>(parkingMap.values());
    }

    public Parking findById(String id) {
        log.info("Find parking of id {}", id);

        Optional<Parking> optionalParking = parkingMap.values().stream().filter(parking -> parking.getId().equals(id)).findFirst();

        if(optionalParking.isEmpty()) {
            notFound(id);
        }

        return optionalParking.get();
    }

    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());

        parkingMap.put(uuid, parkingCreate);

        return parkingCreate;
    }

    public Parking update(String id, Parking parkingCreate) {
        Parking parking = parkingMap.get(id);

        if(parking == null) {
            notFound(id);
        }

        parking.setState(parkingCreate.getState());
        parking.setModel(parkingCreate.getModel());
        parking.setLicense(parkingCreate.getLicense());

        parkingMap.remove(id);
        parkingMap.put(id, parking);

        return parkingCreate;
    }

    public void delete(String id) {
        Parking parking = parkingMap.get(id);

        if(parking == null) {
            notFound(id);
        }

        parkingMap.remove(id);
    }

    private static void notFound(String id) {
        throw new HttpResponseException(HttpStatus.NOT_FOUND, String.format("Parking of id (%s) not found!", id));
    }
}
