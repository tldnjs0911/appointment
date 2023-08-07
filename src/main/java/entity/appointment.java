package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private shopInformation shop;

    private LocalDateTime appointmentDateTime;

    private boolean confirmed;

    @Enumerated(EnumType.STRING)
    private appointmentStatus status;

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}