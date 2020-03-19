package nl.rls.composer.controller;

import io.swagger.annotations.Api;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.DangerGoodsType;
import nl.rls.composer.repository.DangerGoodsTypeRepository;
import nl.rls.composer.rest.dto.DangerGoodsTypeDto;
import nl.rls.composer.rest.dto.mapper.DangerGoodsTypeDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/dangergoodstypes")
@Api(value = "All DangerGoodsType according to the RID chapter 3.2, table A, column 5, excepting the shunting labels Model 13 and 15 (CODE: OTIF RID-Specification).")
public class DangerGoodsTypeController {
    private final DangerGoodsTypeRepository dangerGoodsTypeRepository;

    public DangerGoodsTypeController(DangerGoodsTypeRepository dangerGoodsTypeRepository) {
        this.dangerGoodsTypeRepository = dangerGoodsTypeRepository;
    }

    @GetMapping(value = "/{dangerGoodsTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<DangerGoodsTypeDto> getById(@PathVariable int dangerGoodsTypeId) {
        DangerGoodsType dangerGoodsType = dangerGoodsTypeRepository.findById(dangerGoodsTypeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find danger goods type with id %d", dangerGoodsTypeId)));
        DangerGoodsTypeDto dangerGoodsTypeDto = DangerGoodsTypeDtoMapper.map(dangerGoodsType);
        return ResponseBuilder.ok()
                .data(dangerGoodsTypeDto)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<DangerGoodsTypeDto>> getAll() {
        List<DangerGoodsType> dangerGoodsTypeList = dangerGoodsTypeRepository.findAll();

        List<DangerGoodsTypeDto> dangerGoodsTypeDtoList = dangerGoodsTypeList.stream()
                .map(DangerGoodsTypeDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(dangerGoodsTypeDtoList)
                .build();
    }
}
