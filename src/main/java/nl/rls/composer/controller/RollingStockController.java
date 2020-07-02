package nl.rls.composer.controller;

import nl.rls.ci.url.BaseURL;
import nl.rls.composer.rest.dto.RollingStockDto;
import nl.rls.composer.rest.dto.RollingStockMoveDto;
import nl.rls.composer.rest.dto.RollingStockPostDto;
import nl.rls.composer.service.TrainCompositionService;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasPermission('read:rollingstock')")
    public Response<List<RollingStockDto>> getRollingStockByTrainCompositionId(@PathVariable Integer trainCompositionId) {
        List<RollingStockDto> rollingStocks = trainCompositionService.getRollingStockByTrainCompositionId(trainCompositionId);
        return ResponseBuilder.ok()
                .data(rollingStocks)
                .build();
    }

    @PostMapping(value = "/{trainCompositionId}/stock", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasPermission('write:rollingstock')")
    public Response<RollingStockDto> addRollingStock(@PathVariable int trainCompositionId, @RequestBody RollingStockPostDto rollingStockPostDto) {
        RollingStockDto rollingStockDto = trainCompositionService.addRollingStockToTrainComposition(trainCompositionId, rollingStockPostDto);
        return ResponseBuilder.created()
                .data(rollingStockDto)
                .build();
    }

    @PutMapping(value = "/{trainCompositionId}/stock/{rollingStockId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasPermission('write:rollingstock')")
    public Response<RollingStockDto> updateRollingStock(@PathVariable int trainCompositionId, @PathVariable int rollingStockId, @RequestBody RollingStockPostDto rollingStockPostDto) {
        RollingStockDto rollingStockDto = trainCompositionService.updateRollingStock(trainCompositionId, rollingStockId, rollingStockPostDto);
        return ResponseBuilder.created()
                .data(rollingStockDto)
                .build();
    }

    @DeleteMapping(value = "/{trainCompositionId}/stock/{rollingStockId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasPermission('delete:rollingstock')")
    public Response<?> deleteRollingStock(@PathVariable int trainCompositionId, @PathVariable int rollingStockId) {
        trainCompositionService.deleteRollingStock(trainCompositionId, rollingStockId);
        return ResponseBuilder.ok()
                .build();
    }

    @PutMapping(value = "/{trainCompositionId}/stock/{rollingStockId}/move", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasPermission('write:rollingstock')")
    public Response<?> moveRollingStock(@PathVariable int trainCompositionId, @PathVariable int rollingStockId, @RequestBody RollingStockMoveDto rollingStockMoveDto) {
        trainCompositionService.moveRollingStock(trainCompositionId, rollingStockId, rollingStockMoveDto.getPosition());
        return ResponseBuilder.ok()
                .build();
    }
}
