package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String reviewContent;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private shopInformation shop;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private appointment appointment;
}
