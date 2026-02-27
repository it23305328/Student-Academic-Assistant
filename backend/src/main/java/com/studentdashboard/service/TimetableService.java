package com.studentdashboard.service;

import com.studentdashboard.dto.TimetableDTO;
import com.studentdashboard.model.Timetable;
import com.studentdashboard.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    public Timetable addEntry(Timetable timetable) {
        if (timetable.getStartTime() != null && timetable.getEndTime() != null &&
                !timetable.getStartTime().isBefore(timetable.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        return timetableRepository.save(timetable);
    }

    public Timetable updateEntry(Long id, Long studentId, Timetable updatedTimetable) {
        if (updatedTimetable.getStartTime() != null && updatedTimetable.getEndTime() != null &&
                !updatedTimetable.getStartTime().isBefore(updatedTimetable.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        Optional<Timetable> existingOpt = timetableRepository.findById(id);
        if (existingOpt.isPresent()) {
            Timetable existing = existingOpt.get();
            // Ownership check
            if (!existing.getStudentId().equals(studentId)) {
                throw new SecurityException("Unauthorized access to timetable entry");
            }
            existing.setSubjectName(updatedTimetable.getSubjectName());
            existing.setDate(updatedTimetable.getDate());
            existing.setStartTime(updatedTimetable.getStartTime());
            existing.setEndTime(updatedTimetable.getEndTime());
            existing.setVenue(updatedTimetable.getVenue());
            return timetableRepository.save(existing);
        }
        return null;
    }

    public boolean deleteEntry(Long id, Long studentId) {
        Optional<Timetable> existingOpt = timetableRepository.findById(id);
        if (existingOpt.isPresent()) {
            Timetable existing = existingOpt.get();
            // Ownership check
            if (!existing.getStudentId().equals(studentId)) {
                throw new SecurityException("Unauthorized access to timetable entry");
            }
            timetableRepository.delete(existing);
            return true;
        }
        return false;
    }

    public List<TimetableDTO> getTimetableByStudent(Long studentId) {
        return timetableRepository.findByStudentIdOrderByDateAscStartTimeAsc(studentId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<TimetableDTO> getTimetableByDate(Long studentId, java.time.LocalDate date) {
        return timetableRepository.findByStudentIdAndDateOrderByStartTimeAsc(studentId, date)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private TimetableDTO convertToDTO(Timetable timetable) {
        return new TimetableDTO(
                timetable.getId(),
                timetable.getSubjectName(),
                timetable.getDate(),
                timetable.getStartTime(),
                timetable.getEndTime(),
                timetable.getVenue());
    }

    public List<String> findFreeSlots(Long studentId, java.time.LocalDate date) {
        List<Timetable> lectures = timetableRepository.findByStudentIdAndDateOrderByStartTimeAsc(studentId, date);
        List<String> freeSlots = new java.util.ArrayList<>();

        for (int i = 0; i < lectures.size() - 1; i++) {
            Timetable current = lectures.get(i);
            Timetable next = lectures.get(i + 1);

            long minutesBetween = java.time.Duration.between(current.getEndTime(), next.getStartTime()).toMinutes();

            if (minutesBetween > 30) {
                long hours = minutesBetween / 60;
                long mins = minutesBetween % 60;
                String timeStr = "";
                if (hours > 0) {
                    timeStr += hours + "-hour ";
                }
                if (mins > 0 && hours == 0) {
                    timeStr += mins + "-minute ";
                }
                timeStr = timeStr.trim();

                freeSlots.add("You have a " + timeStr + " gap between " + current.getSubjectName() + " and "
                        + next.getSubjectName() + ". Time for a break! ☕");
            }
        }
        return freeSlots;
    }
}
