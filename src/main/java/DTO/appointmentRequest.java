package DTO;

import entity.appointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class appointmentRequest {
    private Long shopId;
    private LocalDateTime appointmentDateTime;
    private appointmentStatus status;

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

}
