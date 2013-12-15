package dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import annotation.AssociationType;

/**
 * @author Amitab
 *
 */
@Entity
@Table(name="presentation")
public class Presentation {
	@Id @GeneratedValue
	@Column(name="presentation_id")
	private int presentationId;
	
	@Column(name="presentation_name", nullable = false)
	private String presentationName = null;
	
	@AssociationType(type="Staff")
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "presentationList")
    @NotFound(action=NotFoundAction.IGNORE)
	private Collection<Staff> staffList = new ArrayList<Staff>();
    
	public int getPresentationId() {
		return presentationId;
	}

	public void setPresentationId(int presentationId) {
		this.presentationId = presentationId;
	}

	public String getPresentationName() {
		return presentationName;
	}

	public void setPresentationName(String presentationName) {
		this.presentationName = presentationName;
	}

	public Collection<Staff> getStaffList() {
		return staffList;
	}
	
	public void setStaffList(Collection<Staff> staffList) {
		this.staffList = staffList;
	}
	
}
