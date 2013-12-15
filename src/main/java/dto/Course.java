package dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Amitab
 *
 */
@Entity
@Table(name="course")
public class Course {
	@Id
	@Column(name="course_code", nullable = false)
	private String courseCode;

	@Column(name="course_name", nullable = false)
	private String courseName;
	
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
}
