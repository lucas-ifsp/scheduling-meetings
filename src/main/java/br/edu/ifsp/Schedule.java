package br.edu.ifsp;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.util.FormatProcessor.FMT;

public class Schedule {
    public static final int MAX_MEETINGS_PER_DAY = 20;
    private final LocalDate day;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Meeting[] meetings;
    private int numberOfMeetings;

    public Schedule(LocalDate day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetings = new Meeting[MAX_MEETINGS_PER_DAY];
    }

    public void addMeeting(Meeting meeting) {
        if (meeting == null) return;
        if (isOutOfWorkingDay(meeting)) return;
        if (isConflicting(meeting)) return;
        meetings[numberOfMeetings++] = meeting;
    }

    private boolean isOutOfWorkingDay(Meeting meeting) {
        return meeting.getStartTime().isBefore(startTime) || meeting.getEndTime().isAfter(endTime);
    }

    //Auxiliary method to check if a new Meeting is not in conflict with the previous scheduled ones
    private boolean isConflicting(Meeting meeting) {
        for (int i = 0; i < numberOfMeetings; i++) {
            Meeting existingMeeting = meetings[i];
            if (meeting.conflicts(existingMeeting)) return true;
        }
        return false;
    }

    //It looks for any object in the meetings array that is equal by value (same)
    public void removeMeeting(Meeting meeting) {
        for (int i = 0; i < numberOfMeetings; i++) {
            Meeting currentMeeting = meetings[i];
            if (currentMeeting.same(meeting)) {
                meetings[i] = meetings[numberOfMeetings - 1];
                meetings[numberOfMeetings - 1] = null;
                numberOfMeetings--;
            }
        }
    }

    public double percentageSpentInMeetings() {
        double timeSpentInMeetings = 0;
        double workingDayInMinutes = Duration.between(startTime, endTime).toMinutes();
        for (int i = 0; i < numberOfMeetings; i++)
            timeSpentInMeetings += meetings[i].durationInMinutes();
        return timeSpentInMeetings / workingDayInMinutes;
    }

    public String scheduleAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return FMT."""
                __________________________________________________________________________________
                Day: \{formatter.format(day)}   Start time: \{startTime}   End time: \{endTime}
                __________________________________________________________________________________
                \{buildScheduleMeetingsTextInOrder()}
                __________________________________________________________________________________
                Percentage spent in meetings: %.2f\{percentageSpentInMeetings() * 100}%%
                __________________________________________________________________________________
                """;
    }

    // Silly method to print the meetings in chronological order.
    // We will see a better way to do it soon...
    private String buildScheduleMeetingsTextInOrder() {
        StringBuilder builder = new StringBuilder("Meetings: \n");
        final Meeting[] meetingsCopy = meetings.clone(); // creates a copy of the meetings array
        int numberOfMeetingsInCopy = numberOfMeetings;

        while (numberOfMeetingsInCopy > 0) {
            Meeting first = meetingsCopy[0];
            int firstIndex = 0;
            for (int i = 0; i < numberOfMeetingsInCopy; i++) {
                if (meetingsCopy[i].isBefore(first)) {
                    first = meetingsCopy[i];
                    firstIndex = i;
                }
            }
            builder.append(STR."[\{first.getStartTime()} - \{first.getEndTime()}]: \{first.getDescription()}\n");
            meetingsCopy[firstIndex] = meetingsCopy[--numberOfMeetingsInCopy];
        }
        return builder.toString();
    }


    public LocalDate getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
