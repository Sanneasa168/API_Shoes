package com.example.shoes_ecommerce.feature.Warranties;

import com.example.shoes_ecommerce.feature.Warranties.dto.WarrantiesRequest;
import com.example.shoes_ecommerce.feature.Warranties.dto.WarrantiesResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/warranties")
public class WarrantiesController {

    private final  WarrantiesService warrantiesService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    WarrantiesResponse createWarranties(@Valid @RequestBody WarrantiesRequest warrantiesRequest){
       return  warrantiesService.createWarranties(warrantiesRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/findList")
    List<WarrantiesResponse> findListOfWarranties(){
        return warrantiesService.getAllWarranties();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    void deleteWarrantiesByUuid(@PathVariable("uuid") String uuid){
        warrantiesService.deleteWarrantiesByUuid(uuid);
    }

}
