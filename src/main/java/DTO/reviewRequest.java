package DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class reviewRequest {
    private String userName;
    private Long shopId;
    private Long appointmentId;
    private String reviewContent;

}
