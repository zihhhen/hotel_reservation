package com.example.hotel_reservation.repository;

import com.example.hotel_reservation.dto.RoomResponse;
import com.example.hotel_reservation.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {


    @Query("SELECT r FROM Room r " +
            "LEFT JOIN Reservation res ON r.ID = res.room.ID " +
            "AND res.startDate < :endDate " +
            "AND res.endDate > :startDate " +
            "WHERE res.room.ID IS NULL " +
            "AND r.maxOccupancy >= :numberOfGuests")
    List<Room> findAvailableRooms(@Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate,
                                  @Param("numberOfGuests") Integer numberOfGuests);

    @Query("SELECT r FROM Room r WHERE r.maxOccupancy >= :capacity")
    List<Room> findByMaxOccupancy(@Param("capacity") Integer capacity);

    Optional<Room> findByRoomType(String roomType);
}


