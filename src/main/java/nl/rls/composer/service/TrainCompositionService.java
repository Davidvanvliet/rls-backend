package nl.rls.composer.service;

import nl.rls.auth.config.SecurityContext;
import nl.rls.composer.domain.RollingStock;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.exceptions.NewRollingStockPositionOutOfBoundsException;
import nl.rls.composer.exceptions.RollingStockAlreadyInCompositionException;
import nl.rls.composer.exceptions.RollingStockNotInCompositionException;
import nl.rls.composer.repository.RollingStockRepository;
import nl.rls.composer.repository.TrainCompositionRepository;
import nl.rls.composer.rest.dto.RollingStockDto;
import nl.rls.composer.rest.dto.RollingStockPostDto;
import nl.rls.composer.rest.dto.mapper.RollingStockDtoMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TrainCompositionService {

    private final TrainCompositionRepository trainCompositionRepository;

    private final RollingStockRepository rollingStockRepository;

    private final SecurityContext securityContext;

    private final RollingStockFactory rollingStockFactory;

    public TrainCompositionService(TrainCompositionRepository trainCompositionRepository, RollingStockRepository rollingStockRepository, SecurityContext securityContext, RollingStockFactory rollingStockFactory) {
        this.trainCompositionRepository = trainCompositionRepository;
        this.rollingStockRepository = rollingStockRepository;
        this.securityContext = securityContext;
        this.rollingStockFactory = rollingStockFactory;
    }

    public List<RollingStockDto> getRollingStockByTrainCompositionId(int trainCompositionId) {
        int ownerId = securityContext.getOwnerId();
        List<RollingStock> rollingStock = rollingStockRepository.findAllByTrainCompositionIdAndTrainCompositionOwnerId(trainCompositionId, ownerId);
        return rollingStock.stream()
                .map(RollingStockDtoMapper::map)
                .collect(Collectors.toList());
    }

    public RollingStockDto addRollingStockToTrainComposition(int trainCompositionId, RollingStockPostDto rollingStockDTO) {
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        if (trainComposition.hasRollingStock(rollingStockDTO.getStockIdentifier())) {
            throw new RollingStockAlreadyInCompositionException(rollingStockDTO.getStockIdentifier());
        }
        RollingStock rollingStock = rollingStockFactory.createRollingStock(rollingStockDTO);
        rollingStock.setTrainComposition(trainComposition);
        trainComposition.addRollingStock(rollingStock);
        rollingStock = rollingStockRepository.save(rollingStock);
        trainCompositionRepository.save(trainComposition);
        return RollingStockDtoMapper.map(rollingStockRepository.findById(rollingStock.getId()).orElse(null));
    }

    public RollingStockDto updateRollingStock(int trainCompositionId, int rollingStockId, RollingStockPostDto rollingStockDto) {
        int ownerId = securityContext.getOwnerId();
        RollingStock rollingStock = rollingStockRepository.findByIdAndTrainCompositionIdAndTrainCompositionOwnerId(rollingStockId, trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find rolling stock with id %d", rollingStockId)));
        if (!Objects.equals(rollingStock.getStockIdentifier(), rollingStockDto.getStockIdentifier())) {
            if (rollingStock.getTrainComposition().hasRollingStock(rollingStockDto.getStockIdentifier())) {
                throw new RollingStockAlreadyInCompositionException(rollingStockDto.getStockIdentifier());
            }
        }
        RollingStock newRollingStock = rollingStockFactory.createRollingStock(rollingStockDto);
        newRollingStock.setPosition(rollingStock.getPosition());
        newRollingStock.setTrainComposition(rollingStock.getTrainComposition());
        newRollingStock.setId(rollingStock.getId());
        rollingStock = rollingStockRepository.save(newRollingStock);
        return RollingStockDtoMapper.map(rollingStock);
    }

    public void deleteRollingStock(int trainCompositionId, int rollingStockId) {
        int ownerId = securityContext.getOwnerId();
        RollingStock rollingStock = rollingStockRepository.findByIdAndTrainCompositionIdAndTrainCompositionOwnerId(rollingStockId, trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find rolling stock with id %d", rollingStockId)));
        TrainComposition trainComposition = rollingStock.getTrainComposition();
        trainComposition.removeRollingStock(rollingStockId);
        trainCompositionRepository.save(trainComposition);
    }

    public void moveRollingStock(int trainCompositionId, int rollingStockId, int newPosition) {
        if (newPosition < 0) {
            throw new NewRollingStockPositionOutOfBoundsException();
        }
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        if (!trainComposition.hasRollingStock(rollingStockId)) {
            throw new RollingStockNotInCompositionException(rollingStockId);
        }
        if (newPosition >= trainComposition.getNumberOfVehicles()) {
            throw new NewRollingStockPositionOutOfBoundsException();
        }
        RollingStock rollingStock = trainComposition.getRollingStock(rollingStockId);
        if (rollingStock.getPosition() <= newPosition) {
            Collections.rotate(trainComposition.getRollingStock().subList(rollingStock.getPosition(), newPosition + 1), -1);
        } else {
            Collections.rotate(trainComposition.getRollingStock().subList(newPosition, rollingStock.getPosition() + 1), 1);
        }
        trainCompositionRepository.save(trainComposition);
    }
}