package nl.rls.ci.controller;

import io.swagger.annotations.ApiOperation;
import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.domain.UicRequest;
import nl.rls.ci.repository.UicRequestResource;
import nl.rls.ci.rest.dto.UicRequestDto;
import nl.rls.ci.rest.dto.mapper.UicRequestDtoMapper;
import nl.rls.ci.url.BaseURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(BaseURL.BASE_PATH + UicRequestController.PATH)
public class UicRequestController {
    public static final String PATH = "uicrequests";
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private UicRequestResource uicRequestResource;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gets all UicRequest for a client, no filtering")
    public ResponseEntity<List<UicRequestDto>> getAll() {
        int ownerId = securityContext.getOwnerId();
        Iterable<UicRequest> uicRequestList = uicRequestResource.findByOwnerId(ownerId);
        List<UicRequestDto> uicRequestDtoList = new ArrayList<>();

        for (UicRequest uicRequest : uicRequestList) {
            uicRequestDtoList.add(UicRequestDtoMapper.map(uicRequest));
        }
        return ResponseEntity.ok(uicRequestDtoList);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UicRequestDto> getById(@PathVariable Integer id) {
        Optional<UicRequest> uicRequest = uicRequestResource.findById(id);
        if (uicRequest.isPresent()) {
            UicRequestDto uicRequestDto = UicRequestDtoMapper.map(uicRequest.get());
            return ResponseEntity.ok(uicRequestDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
