import br.edu.ifsp.Meeting;
import br.edu.ifsp.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

void main(){
    Schedule schedule = new Schedule(LocalDate.now(), LocalTime.of(8, 0), LocalTime.of(17,0));

    schedule.addMeeting(new Meeting("Before working time (should not be added)", LocalTime.of(7, 30), LocalTime.of(8, 0)));
    schedule.addMeeting(new Meeting("Meet with students", LocalTime.of(8, 1), LocalTime.of(9, 0)));
    schedule.addMeeting(new Meeting("Meet with pai Jorge", LocalTime.of(15, 1), LocalTime.of(15, 30)));
    schedule.addMeeting(new Meeting("Research meet", LocalTime.of(10, 1), LocalTime.of(11, 30)));
    schedule.addMeeting(new Meeting("After working time (should not be added)", LocalTime.of(16, 30), LocalTime.of(17, 1)));
    schedule.addMeeting(new Meeting("Meet to be deleted", LocalTime.of(16, 0), LocalTime.of(17, 0)));
    schedule.addMeeting(new Meeting("Conflict meet (should not be added)", LocalTime.of(11, 0), LocalTime.of(12, 0)));
    System.out.println(schedule.scheduleAsString());

    schedule.removeMeeting(new Meeting("Meet to be deleted", LocalTime.of(16, 0), LocalTime.of(17, 0)));
    System.out.println("After deleting a meeting");
    System.out.println(schedule.scheduleAsString());

}