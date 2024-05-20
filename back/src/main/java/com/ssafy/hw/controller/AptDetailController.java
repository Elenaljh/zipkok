package com.ssafy.hw.controller;

import com.ssafy.hw.model.dto.*;
import com.ssafy.hw.model.service.AptDetailService;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apt")
@CrossOrigin(originPatterns = {"*"})
public class AptDetailController {
    private final AptDetailService service;
    public AptDetailController(AptDetailService service) {
        this.service = service;
    }

    @GetMapping("/details")
    public ResponseEntity<?> getAptDetails(@RequestParam String aptCode) {
        try {
            AptDetail detail = service.searchDetailByCode(aptCode);
            return new ResponseEntity<AptDetail>(detail, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/transaction")
    public ResponseEntity<?> getTransactionList(@RequestParam Map<String, String> params) {
        try {
            List<AptTransaction> list = service.searchTransactionByCode(params.get("aptCode"),
                    params.get("type"));
            return new ResponseEntity<List<AptTransaction>>(list, HttpStatus.OK);

        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/average")
    public ResponseEntity<?> getAveragePrice(@RequestParam String aptCode, @RequestParam String type) {
        try {
            PriceAvgDto average = service.getAverage(aptCode, type);
            return new ResponseEntity<PriceAvgDto>(average, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/office")
    public ResponseEntity<?> getOffice(@RequestParam double lat, @RequestParam double lng) {
        try {
            List<OfficeDto> office = service.getOffice(lat, lng);
            return new ResponseEntity<List<OfficeDto>>(office, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/school")
    public ResponseEntity<?> getSchool(@RequestParam double lat, @RequestParam double lng) {
        try {
            List<SchoolDto> school = service.getSchool(lat, lng);
            return new ResponseEntity<List<SchoolDto>>(school, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/population")
    public ResponseEntity<?> getPopulation(@RequestParam String bjdCode) {
        try {
            PopulationDto population = service.getPopulation(bjdCode);
            return new ResponseEntity<PopulationDto>(population, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/dong")
    public ResponseEntity<?> getDong(@RequestParam String bjdCode) {
        try {
            String dong = service.getDongbyCode(bjdCode);
            return new ResponseEntity<String>(dong, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
