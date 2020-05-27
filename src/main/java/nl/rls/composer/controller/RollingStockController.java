package nl.rls.composer.controller;

import nl.rls.ci.url.BaseURL;
import nl.rls.composer.rest.dto.RollingStockDto;
import nl.rls.composer.rest.dto.RollingStockPostDto;
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
    public Response<List<RollingStockDto>> getRollingStockByTrainCompositionId(@PathVariable Integer trainCompositionId) {
        List<RollingStockDto> rollingStocks = trainCompositionService.getRollingStockByTrainCompositionId(trainCompositionId);
        return ResponseBuilder.ok()
                .data(rollingStocks)
                .build();
    }

    @PostMapping(value = "/{trainCompositionId}/stock", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<RollingStockDto> addRollingStock(@PathVariable int trainCompositionId, @RequestBody RollingStockPostDto rollingStockPostDto) {
        RollingStockDto rollingStockDto = trainCompositionService.addRollingStockToTrainComposition(trainCompositionId, rollingStockPostDto);
        return ResponseBuilder.created()
                .data(rollingStockDto)
                .build();
    }

    @PutMapping(value = "/{trainCompositionId}/stock/{rollingStockId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<RollingStockDto> updateRollingStock(@PathVariable int trainCompositionId, @PathVariable int rollingStockId, @RequestBody RollingStockPostDto rollingStockPostDto) {
        RollingStockDto rollingStockDto = trainCompositionService.updateRollingStock(trainCompositionId, rollingStockId, rollingStockPostDto);
        return ResponseBuilder.created()
                .data(rollingStockDto)
                .build();
    }
}
