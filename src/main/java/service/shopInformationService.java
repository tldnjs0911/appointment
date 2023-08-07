package service;

import DTO.appointmentRequest;
import DTO.reviewRequest;
import DTO.shopSearchCriteria;
import entity.appointment;
import entity.review;
import entity.shopInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.shopInformationRepository;
import repository.appointmentRepository;
import repository.reviewRepository;
import entity.appointmentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class shopInformationService {
    private final shopInformationRepository shopInformationRepository;

    @Autowired
    public shopInformationService(shopInformationRepository shopInformationRepository) {
        this.shopInformationRepository = shopInformationRepository;
    }

    @Autowired
    private appointmentRepository appointmentRepository;

    @Autowired
    private reviewRepository reviewRepository;

    public shopInformation saveShopInformation(shopInformation shopInformation) {
        return shopInformationRepository.save(shopInformation);
    }

    public shopInformation getShopInformationById(Long id) {
        return shopInformationRepository.findById(id).orElse(null);
    }

    public List<shopInformation> searchShops(shopSearchCriteria criteria) {
        return shopInformationRepository.findShopsByCriteria(criteria);
    }


    public boolean makeAppointment(Long shopId, appointmentRequest appointmentRequest) {
        Optional<shopInformation> optionalShop = shopInformationRepository.findById(shopId);
        if (optionalShop.isPresent()) {
            shopInformation shop = optionalShop.get();

            if (isShopAvailable(shop, appointmentRequest.getAppointmentDateTime())) {
                appointment appointment = new appointment();
                appointment.setShop(shop);
                appointment.setAppointmentDateTime(appointmentRequest.getAppointmentDateTime());
                appointment.setStatus(appointmentStatus.Pending);

                appointmentRepository.save(appointment);
                return true;
            }
        }
        return false;
    }

    private boolean isShopAvailable(shopInformation shop, LocalDateTime appointmentDateTime) {
        List<appointment> appointments = appointmentRepository.findByAppointmentDateTime(appointmentDateTime);

        return true;
    }

    public boolean confirmAppointment(Long appointmentId) {
        Optional<appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            appointment appointment = optionalAppointment.get();

            LocalDateTime appointmentTime = appointment.getAppointmentDateTime();
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime tenMinutesBeforeAppointment = appointmentTime.minusMinutes(10);

            if (currentDateTime.isAfter(tenMinutesBeforeAppointment) && currentDateTime.isBefore(appointmentTime)) {
                appointment.setConfirmed(true);
                appointmentRepository.save(appointment);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean submitReview(reviewRequest reviewRequest) {
        review review = new review();
        review.setUserName(reviewRequest.getUserName());
        review.setReviewContent(reviewRequest.getReviewContent());

        Optional<shopInformation> optionalShop = shopInformationRepository.findById(reviewRequest.getShopId());
        Optional<appointment> optionalAppointment = appointmentRepository.findById(reviewRequest.getAppointmentId());

        if (optionalShop.isPresent() && optionalAppointment.isPresent()) {
            review.setShop(optionalShop.get());
            review.setAppointment(optionalAppointment.get());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    public boolean acceptAppointment(Long appointmentId) {
        Optional<appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            appointment appointment = optionalAppointment.get();
            appointment.setStatus(appointmentStatus.Accepted);
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }

    public boolean declineAppointment(Long appointmentId) {
        Optional<appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            appointment appointment = optionalAppointment.get();
            appointment.setStatus(appointmentStatus.Declined);
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }

}
