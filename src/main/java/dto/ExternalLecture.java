package dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import annotation.AssociationType;

@Entity
@Table(name="external_lecture")
public class ExternalLecture {
	@Id @GeneratedValue
	@Column(name="external_lecture_id")
	private int externalLectureId;

	@Column(name = "date", columnDefinition="DATE")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(name="external_lecture_name", nullable = false)
	private String externalLectureName = null;
	
	@AssociationType(type="Staff")
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "externalLectures")
    @NotFound(action=NotFoundAction.IGNORE)
	private Collection<Staff> staffList = new ArrayList<Staff>();

	
	public int getExternalLectureId() {
		return externalLectureId;
	}

	public void setExternalLectureId(int externalLectureId) {
		this.externalLectureId = externalLectureId;
	}

	public String getExternalLectureName() {
		return externalLectureName;
	}

	public void setExternalLectureName(String externalLectureName) {
		this.externalLectureName = externalLectureName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Collection<Staff> getStaffList() {
		return staffList;
	}

	public void setStaffList(Collection<Staff> staffList) {
		this.staffList = staffList;
	}
	
}
