package dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import annotation.AssociationType;

/**
 * @author Amitab
 *
 */
@Entity
@Table(name="grade")
public class Grade {
	@Id @GeneratedValue
	@Column(name="grade_id")
	private int gradeId;
	
	@Column(name="year", nullable = false)
	private int year;

	@Column(name="section", nullable = false)
	private char section;

    @AssociationType(type="TimeTable")
	@OneToMany(fetch=FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy="grade")
    @Sort(type=SortType.NATURAL)
	private SortedSet<TimeTable> timetableList = new TreeSet<TimeTable>();
	
    @ElementCollection
    @JoinTable(name="grade_staff_incharge", joinColumns=@JoinColumn(name="grade_id"))
    private Collection<GradeStaffIncharge> staffInchargeList = new HashSet<GradeStaffIncharge>();
    
	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public char getSection() {
		return section;
	}

	public void setSection(char section) {
		this.section = section;
	}

	public SortedSet<TimeTable> getTimetableList() {
		return timetableList;
	}

	public void setTimetableList(SortedSet<TimeTable> timetableList) {
		this.timetableList = timetableList;
	}

	public Collection<GradeStaffIncharge> getStaffInchargeList() {
		return staffInchargeList;
	}

	public void setStaffInchargeList(
			Collection<GradeStaffIncharge> staffInchargeList) {
		this.staffInchargeList = staffInchargeList;
	}
	
}
