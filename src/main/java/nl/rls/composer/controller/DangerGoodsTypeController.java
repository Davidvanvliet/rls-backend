package nl.rls.composer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.DangerGoodsType;
import nl.rls.composer.repository.DangerGoodsTypeRepository;
import nl.rls.composer.rest.dto.DangerGoodsTypeDto;
import nl.rls.composer.rest.dto.mapper.DangerGoodsTypeDtoMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH+"dangergoodstypes")
@Api(description = "All DangerGoodsType according to the RID chapter 3.2, table A, column 5, excepting the shunting labels Model 13 and 15 (CODE: OTIF RID-Specification).")
public class DangerGoodsTypeController {
	@Autowired
	private DangerGoodsTypeRepository dangerGoodsTypeRepository;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DangerGoodsTypeDto> getById(@PathVariable Integer id) {
		Optional<DangerGoodsType> optional = dangerGoodsTypeRepository.findById(id);
		if (optional.isPresent()) {
			DangerGoodsTypeDto dangerGoodsTypeDto = DangerGoodsTypeDtoMapper.map(optional.get());
			return ResponseEntity.ok(dangerGoodsTypeDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DangerGoodsTypeDto>> getAll() {
		Iterable<DangerGoodsType> entityList = dangerGoodsTypeRepository.findAll();

		List<DangerGoodsTypeDto> dtoList = new ArrayList<>();
		for (DangerGoodsType entity : entityList) {
			dtoList.add(DangerGoodsTypeDtoMapper.map(entity));
		}
		return ResponseEntity.ok(dtoList);
	}
}
