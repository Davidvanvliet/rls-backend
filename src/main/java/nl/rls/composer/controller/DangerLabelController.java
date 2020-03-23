package nl.rls.composer.controller;

import io.swagger.annotations.Api;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.code.DangerLabel;
import nl.rls.composer.repository.DangerLabelRepository;
import nl.rls.composer.rest.dto.DangerLabelDto;
import nl.rls.composer.rest.dto.mapper.DangerLabelDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/dangerlabels")
@Api(value = "All Danger Labels of dangerous good according to the RID chapter 3.2, table A, column 5, excepting the shunting labels Model 13 and 15 (CODE: OTIF RID-Specification).")
public class DangerLabelController {
    private final DangerLabelRepository dangerLabelRepository;

    public DangerLabelController(DangerLabelRepository dangerLabelRepository) {
        this.dangerLabelRepository = dangerLabelRepository;
    }

    @GetMapping(value = "/{dangerLabelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<DangerLabelDto> getById(@PathVariable int dangerLabelId) {
        DangerLabel dangerLabel = dangerLabelRepository.findById(dangerLabelId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find danger label with id %d", dangerLabelId)));
        DangerLabelDto dangerLabelDto = DangerLabelDtoMapper.map(dangerLabel);
        return ResponseBuilder.ok()
                .data(dangerLabelDto)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<DangerLabelDto>> getAll() {
        List<DangerLabel> dangerLabelList = dangerLabelRepository.findAll();
        List<DangerLabelDto> dangerLabelDtoList = dangerLabelList.stream()
                .map(DangerLabelDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(dangerLabelDtoList)
                .build();
    }
}
