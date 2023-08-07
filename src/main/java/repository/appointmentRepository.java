package repository;

import entity.appointment;
import entity.role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface appointmentRepository extends JpaRepository<appointment, Long> {

    List<appointment> findByAppointmentDateTime(LocalDateTime appointmentDateTime);

}
