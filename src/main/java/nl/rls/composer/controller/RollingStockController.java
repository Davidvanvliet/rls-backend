package nl.rls.composer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.rest.dto.RollingStockDTO;
import nl.rls.composer.rest.dto.mapper.RollingStockDtoMapper;
import nl.rls.composer.service.TrainCompositionService;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseURL.BASE_PATH + TrainCompositionController.PATH)
public class RollingStockController {

    private final TrainCompositionService trainCompositionService;

    public RollingStockController(TrainCompositionService trainCompositionService) {
        this.trainCompositionService = trainCompositionService;
    }

    @GetMapping(value = "/{trainCompositionId}/stock", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<RollingStockDTO>> getRollingStockByTrainCompositionId(@PathVariable Integer trainCompositionId) {
        return ResponseBuilder.ok()
                .data(trainCompositionService.getRollingStockByTrainCompositionId(trainCompositionId))
                .build();
    }

    @PostMapping(value = "/{trainCompositionId}/stock", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<RollingStockDTO> addRollingStock(@PathVariable Integer trainCompositionId, @RequestBody RollingStockDTO rollingStockDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writer().writeValueAsString(rollingStockDTO));
        System.out.println(rollingStockDTO.getStockType());
        System.out.println(objectMapper.writer().writeValueAsString(RollingStockDtoMapper.map(rollingStockDTO)));
        return ResponseBuilder.created()
                .data(null)
                .build();
    }
}
