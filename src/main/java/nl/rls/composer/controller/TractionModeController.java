package nl.rls.composer.controller;

import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.code.TractionMode;
import nl.rls.composer.repository.TractionModeRepository;
import nl.rls.composer.rest.dto.TractionModeDto;
import nl.rls.composer.rest.dto.mapper.TractionModeDtoMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/tractionmodes")
public class TractionModeController {
    private final TractionModeRepository tractionModeRepository;

    public TractionModeController(TractionModeRepository tractionModeRepository) {
        this.tractionModeRepository = tractionModeRepository;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TractionModeDto> getTractionMode(@PathVariable Integer id) {
        Optional<TractionMode> optional = tractionModeRepository.findById(id);
        if (optional.isPresent()) {
            TractionModeDto tractionModeDto = TractionModeDtoMapper
                    .map(optional.get());
            return ResponseEntity.ok(tractionModeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TractionModeDto>> getAll(@RequestParam(value = "code", required = false) String code) {
        if (code == null) {
            Iterable<TractionMode> tractionModeList = tractionModeRepository.findAll();
            List<TractionModeDto> tractionModeDtoList = new ArrayList<>();

            for (TractionMode tractionMode : tractionModeList) {
                tractionModeDtoList.add(TractionModeDtoMapper.map(tractionMode));
            }
            return ResponseEntity.ok(tractionModeDtoList);
        } else {
            Optional<TractionMode> optional = tractionModeRepository.findByCode(code);
            if (optional.isPresent()) {
                TractionModeDto tractionModeDto = TractionModeDtoMapper
                        .map(optional.get());
                return ResponseEntity.ok(Collections.singletonList(tractionModeDto));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }

}
