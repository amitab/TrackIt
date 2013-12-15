package dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import annotation.AssociationType;

/**
 * @author Amitab
 *
 */
@Entity
@Table(
   name = "class_session", 
   uniqueConstraints = {@UniqueConstraint(columnNames = {"timetable_id", "hour"})}
)
public class ClassSession implements Comparable<ClassSession> {

	@Id @GeneratedValue
	@Column(name="class_session_id")
	private int classSessionId;

	@AssociationType(type="Staff")
	@ManyToOne
	@JoinColumn(name="staff_id")
	private Staff staffIncharge;

	@Column(name="hour")
	private int hour;

	@AssociationType(type="Course")
	@OneToOne
	@JoinColumn(name="course_id")
	private Course course;

	@AssociationType(type="TimeTable")
	@ManyToOne 
	@JoinColumn(name="timetable_id")
	private TimeTable timetable;
	
	public int getClassSessionId() {
		return classSessionId;
	}
	public void setClassSessionId(int classSessionId) {
		this.classSessionId = classSessionId;
	}
	public Staff getStaffIncharge() {
		return staffIncharge;
	}
	public void setStaffIncharge(Staff staffIncharge) {
		this.staffIncharge = staffIncharge;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public TimeTable getTimetable() {
		return timetable;
	}
	public void setTimetable(TimeTable timetable) {
		this.timetable = timetable;
	}
	@Override
	public int compareTo(ClassSession classSession) {
		return this.getHour() - classSession.getHour();
	}
	
}
