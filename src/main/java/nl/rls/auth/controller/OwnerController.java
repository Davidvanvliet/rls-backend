package nl.rls.auth.controller;

import nl.rls.auth.rest.dto.OwnerCreateDto;
import nl.rls.auth.rest.dto.OwnerDto;
import nl.rls.auth.service.OwnerService;
import nl.rls.ci.url.BaseURL;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<List<OwnerDto>> getOwners() {
        List<OwnerDto> owners = ownerService.getOwners();
        return ResponseBuilder.ok()
                .data(owners)
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<OwnerDto> createOwner(@RequestBody OwnerCreateDto ownerCreateDto) {
        OwnerDto ownerDto = ownerService.createOwner(ownerCreateDto.getCompanyCode(), ownerCreateDto.getAuth0Ids());
        return ResponseBuilder.created()
                .data(ownerDto)
                .build();
    }

    @PutMapping("/{ownerId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<OwnerDto> updateOwner(@RequestBody OwnerCreateDto ownerCreateDto, @PathVariable Integer ownerId) {
        OwnerDto ownerDto = ownerService.updateOwner(ownerId, ownerCreateDto.getCompanyCode(), ownerCreateDto.getAuth0Ids());
        return ResponseBuilder.accepted()
                .data(ownerDto)
                .build();
    }
}
