package dto;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import annotation.AssociationType;

@Embeddable
public class GradeStaffIncharge {
	@AssociationType(type="Staff")
	@OneToOne
	@JoinColumn(name="staff_id")
	private Staff staff;
	
	@AssociationType(type="Course")
	@OneToOne
	@JoinColumn(name="course_id")
	private Course course;
	
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}

}
