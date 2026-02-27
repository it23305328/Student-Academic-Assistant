package com.studentdashboard.controller;

import com.studentdashboard.dto.TimetableDTO;
import com.studentdashboard.model.Timetable;
import com.studentdashboard.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;

    @PostMapping("/add")
    public ResponseEntity<?> addEntry(@RequestBody Timetable timetable) {
        try {
            Timetable addedEntry = timetableService.addEntry(timetable);
            return new ResponseEntity<>(addedEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding timetable entry: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all/{studentId}")
    public ResponseEntity<List<TimetableDTO>> getAllEntries(@PathVariable("studentId") Long studentId) {
        List<TimetableDTO> entries = timetableService.getTimetableByStudent(studentId);
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/today/{studentId}")
    public ResponseEntity<List<TimetableDTO>> getTodayEntries(@PathVariable("studentId") Long studentId) {
        List<TimetableDTO> entries = timetableService.getTimetableByDate(studentId, java.time.LocalDate.now());
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable("id") Long id, @RequestParam("studentId") Long studentId,
            @RequestBody Timetable timetable) {
        try {
            Timetable updatedEntry = timetableService.updateEntry(id, studentId, timetable);
            if (updatedEntry != null) {
                return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Timetable entry not found with Id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable("id") Long id, @RequestParam("studentId") Long studentId) {
        try {
            boolean deleted = timetableService.deleteEntry(id, studentId);
            if (deleted) {
                return new ResponseEntity<>("Entry deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Timetable entry not found with Id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (SecurityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/free-slots/{studentId}")
    public ResponseEntity<List<String>> getFreeSlots(
            @PathVariable("studentId") Long studentId,
            @RequestParam("date") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate date) {

        List<String> freeSlots = timetableService.findFreeSlots(studentId, date);
        return new ResponseEntity<>(freeSlots, HttpStatus.OK);
    }
}
