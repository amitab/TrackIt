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
@Table(name="workshop")
public class Workshop {
	@Id @GeneratedValue
	@Column(name="workshop_id")
	private int workshopId;
	
	@Column(name="workshop_name")
	private String workshopName;
	
	@AssociationType(type="Staff")
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "workshopList")
    @NotFound(action=NotFoundAction.IGNORE)
	private Collection<Staff> staffList = new ArrayList<Staff>();

	public int getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(int workshopId) {
		this.workshopId = workshopId;
	}

	public String getWorkshopName() {
		return workshopName;
	}

	public void setWorkshopName(String workshopName) {
		this.workshopName = workshopName;
	}

	public Collection<Staff> getStaffList() {
		return staffList;
	}

	public void setStaffList(Collection<Staff> staffList) {
		this.staffList = staffList;
	}
}
