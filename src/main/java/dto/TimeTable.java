package dto;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import annotation.AssociationType;

/**
 * @author Amitab
 *
 */
@Entity
@Table(
		name="timetable",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"day_name", "grade_id"})}
)
public class TimeTable implements Comparable<TimeTable> {
	@Id @GeneratedValue
	@Column(name="timetable_id")
	private int timetableId;
	
	@Column(name="day_name", nullable = false)
	private String dayName = null;

	@AssociationType(type="ClassSession")
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="timetable")
    @NotFound(action=NotFoundAction.IGNORE)
	@Sort(type=SortType.NATURAL)
	private SortedSet<ClassSession> classSessions = new TreeSet<ClassSession>();

	@AssociationType(type="Grade")
	@ManyToOne
	@JoinColumn(name="grade_id")
	private Grade grade;

	public int getTimetableId() {
		return timetableId;
	}

	public void setTimetableId(int timetableId) {
		this.timetableId = timetableId;
	}

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public SortedSet<ClassSession> getClassSessions() {
		return classSessions;
	}

	public void setClassSessions(SortedSet<ClassSession> classSessions) {
		this.classSessions = classSessions;
	}
	
	public ClassSession getClassSessionByHour(Integer hour) {
		ClassSession classSession = null;
		for(ClassSession cs : classSessions) {
			if(cs.getHour() == hour.intValue()) {
				classSession = cs;
				break;
			}
		}
		return classSession;
	}
	
	private int getDayValue(String dayName) {
		int value = 0;
		
		switch(dayName.toLowerCase()) {
			case "monday":
				value = 1;
			break;
			case "tuesday":
				value = 2;
			break;
			case "wednesday":
				value = 3;
			break;
			case "thursday":
				value = 4;
			break;
			case "friday":
				value = 5;
			break;
			case "saturday":
				value = 6;
			break;
		}
		
		return value;
	}

	@Override
	public int compareTo(TimeTable timeTable) {
		return getDayValue(this.getDayName().toLowerCase()) - getDayValue(timeTable.getDayName().toLowerCase());
	}
	
}
