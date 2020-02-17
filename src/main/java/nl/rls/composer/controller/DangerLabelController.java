package nl.rls.composer.controller;

import io.swagger.annotations.Api;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.code.DangerLabel;
import nl.rls.composer.repository.DangerLabelRepository;
import nl.rls.composer.rest.dto.DangerLabelDto;
import nl.rls.composer.rest.dto.mapper.DangerLabelDtoMapper;
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
@RequestMapping(BaseURL.BASE_PATH + "dangerlabels/")
@Api(description = "All Danger Labels of dangerous good according to the RID chapter 3.2, table A, column 5, excepting the shunting labels Model 13 and 15 (CODE: OTIF RID-Specification).")
public class DangerLabelController {
    @Autowired
    private DangerLabelRepository dangerLabelRepository;

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DangerLabelDto> getById(@PathVariable Integer id) {
        Optional<DangerLabel> optional = dangerLabelRepository.findById(id);
        if (optional.isPresent()) {
            DangerLabelDto dangerLabelDto = DangerLabelDtoMapper.map(optional.get());
            return ResponseEntity.ok(dangerLabelDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DangerLabelDto>> getAll() {
        Iterable<DangerLabel> entityList = dangerLabelRepository.findAll();

        List<DangerLabelDto> dtoList = new ArrayList<>();
        for (DangerLabel entity : entityList) {
            dtoList.add(DangerLabelDtoMapper.map(entity));
        }

//		Link link = linkTo(methodOn(LocationIdentController.class).getAllQuery("", "")).withSelfRel();
//		Resources<DangerLabelDto> dtos = new Resources<DangerLabelDto>(dtoList, link);
        return ResponseEntity.ok(dtoList);
    }
}
