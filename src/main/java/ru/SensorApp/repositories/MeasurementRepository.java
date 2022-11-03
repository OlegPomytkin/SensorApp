package ru.SensorApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.SensorApp.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    @Query(nativeQuery = true,value = "SELECT DISTINCT COUNT(a) FROM (SELECT DISTINCT CAST(created_at as DATE) date FROM measurements m WHERE m.raining = true) as a")
    long countRaining();
}
