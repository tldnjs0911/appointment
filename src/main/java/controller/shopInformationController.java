package controller;

import DTO.appointmentConfirmationRequest;
import DTO.appointmentRequest;
import DTO.reviewRequest;
import DTO.shopSearchCriteria;
import entity.appointment;
import entity.shopInformation;
import entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import service.shopInformationService;
import repository.userRepository;
import repository.appointmentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shops")
public class shopInformationController {
    private final shopInformationService shopInformationService;
    private final userRepository userRepository;

    @Autowired
    public shopInformationController(shopInformationService shopInformationService, repository.userRepository userRepository) {
        this.shopInformationService = shopInformationService;
        this.userRepository = userRepository;
    }

    @Autowired
    private appointmentRepository appointmentRepository;


    @GetMapping("/search")
    public List<shopInformation> searchShops(@ModelAttribute shopSearchCriteria criteria) {
        return shopInformationService.searchShops(criteria);
    }

    @PostMapping
    public shopInformation addShopInformation(@RequestBody shopInformation shopInformation, @AuthenticationPrincipal UserDetails userDetails) {
        user currentUser = userRepository.findByUsername(userDetails.getUsername());
        if (currentUser.getRole().getName().equals("Partner")) {
            return shopInformationService.saveShopInformation(shopInformation);
        } else {
            throw new RuntimeException("You don't have permission to add shop information.");
        }
    }

    @PostMapping("/{shopId}/appointments")
    public ResponseEntity<String> makeAppointment(
            @PathVariable Long shopId,
            @RequestBody appointmentRequest appointmentRequest) {

        boolean success = shopInformationService.makeAppointment(shopId, appointmentRequest);
        if (success) {
            return ResponseEntity.ok("Appointment made successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to make the appointment. Please check the shop availability.");
        }
    }

    @PostMapping("/appointments/confirm")
    public ResponseEntity<String> confirmAppointment(@RequestBody appointmentConfirmationRequest confirmationRequest) {
        boolean success = shopInformationService.confirmAppointment(confirmationRequest.getAppointmentId());
        if (success) {
            return ResponseEntity.ok("Appointment confirmed successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to confirm the appointment. Please check the appointment details.");
        }
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> submitReview(@RequestBody reviewRequest reviewRequest) {
        boolean success = shopInformationService.submitReview(reviewRequest);
        if (success) {
            return ResponseEntity.ok("Review submitted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to submit the review. Please check the review details.");
        }
    }

    @PostMapping("/appointments/{appointmentId}/accept")
    public ResponseEntity<String> acceptAppointment(@PathVariable Long appointmentId) {
        boolean success = shopInformationService.acceptAppointment(appointmentId);
        if (success) {
            return ResponseEntity.ok("Appointment accepted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to accept the appointment.");
        }
    }

    @PostMapping("/appointments/{appointmentId}/decline")
    public ResponseEntity<String> declineAppointment(@PathVariable Long appointmentId) {
        boolean success = shopInformationService.declineAppointment(appointmentId);
        if (success) {
            return ResponseEntity.ok("Appointment declined successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to decline the appointment.");
        }
    }

}
