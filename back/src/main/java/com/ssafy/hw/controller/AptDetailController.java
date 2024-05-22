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
            Integer office = service.getOffice(lat, lng);
            return new ResponseEntity<Integer>(office, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/school")
    public ResponseEntity<?> getSchool(@RequestParam double lat, @RequestParam double lng) {
        try {
            Integer school = service.getSchool(lat, lng);
            return new ResponseEntity<Integer>(school, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/park")
    public ResponseEntity<?> getPark(@RequestParam double lat, @RequestParam double lng) {
        try {
            Integer park = service.getPark(lat, lng);
            return new ResponseEntity<Integer>(park, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/cctv")
    public ResponseEntity<?> getCCTV(@RequestParam String aptCode) {
        try {
            Integer cctv = service.getCCTV(aptCode);
            return new ResponseEntity<Integer>(cctv, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/restaurant")
    public ResponseEntity<?> getRestaurant(@RequestParam String dongCode) {
        try {
            Integer restaurant = service.getRestaurant(dongCode);
            return new ResponseEntity<Integer>(restaurant, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/population")
    public ResponseEntity<?> getPopulation(@RequestParam String bjdCode, @RequestParam(required = false) String type) {
        try {
            if (type==null) {
                PopulationDto population = service.getPopulation(bjdCode);
                return new ResponseEntity<PopulationDto>(population, HttpStatus.OK);
            } else if (type.equals("male")) {
                Integer[] malePopulation = service.getMalePopulation(bjdCode);
                return new ResponseEntity<Integer[]>(malePopulation, HttpStatus.OK);
            } else if (type.equals("female")) {
                Integer[] femalePopulation = service.getFemalePopulation(bjdCode);
                return new ResponseEntity<Integer[]>(femalePopulation, HttpStatus.OK);
            } else {
                Double[] popRatio = service.getPopRatio(bjdCode);
                return new ResponseEntity<Double[]>(popRatio, HttpStatus.OK);
            }
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
