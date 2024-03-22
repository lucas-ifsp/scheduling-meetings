package br.edu.ifsp;

import java.time.Duration;
import java.time.LocalTime;

public class Meeting {
    private final String description;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Meeting(String description, LocalTime startTime, LocalTime endTime) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long durationInMinutes() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    public boolean conflicts(Meeting meeting) {
        return overlaps(startTime, meeting.startTime, meeting.endTime)
                || overlaps(endTime, meeting.startTime, meeting.endTime);
    }

    //Auxiliary method to check if a given time overlaps a schedule
    private boolean overlaps(LocalTime time, LocalTime begin, LocalTime end){
        return !time.isBefore(begin) && !time.isAfter(end);
    }

    //Auxiliary method to print the schedule in sorted order
    public boolean isBefore(Meeting meeting) {
        return startTime.isBefore(meeting.startTime);
    }

    //Auxiliary Method to check if two Meeting objects are equal by value, not by reference
    public boolean same(Meeting meeting) {
        return meeting != null
                && meeting.getStartTime().equals(startTime) && meeting.getEndTime().equals(endTime)
                && meeting.description.equals(description);
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
