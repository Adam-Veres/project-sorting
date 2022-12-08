package com.epam.service;

import com.epam.model.Coordinate;
import com.epam.repository.CoordinateRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoordinateService {

  private final CoordinateRepository coordinateRepository;

  /**
   * @param coordinate
   * @return give back an existing coordinate from database or create a new one if it is not exist
   */
  @Transactional
  public Coordinate getExistingCoordinateOrCreateNew(final Optional<Coordinate> coordinate) {
    final Coordinate coord =
        coordinate.orElseThrow(
            () -> new IllegalArgumentException("Coordinate is not a valid format!"));
    Coordinate resultCoordinate;
    if (coordinateRepository.existsById(coord.getId())) {
      resultCoordinate = coordinateRepository.findById(coord.getId()).get();
    } else {
      resultCoordinate = createNewCoordinate(coordinate);
    }
    return resultCoordinate;
  }

  /**
   * @param coordinate
   * @return created coordinate
   */
  @Transactional
  public Coordinate createNewCoordinate(final Optional<Coordinate> coordinate) {
    final Coordinate coord =
        coordinate.orElseThrow(
            () -> new IllegalArgumentException("Given Object is not a valid format!"));
    return coordinateRepository.save(coord);
  }
}
