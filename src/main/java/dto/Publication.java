package dto;

import helper.AbstractModelObject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import annotation.AssociationType;

/**
 * @author Amitab
 *
 */
@Entity
@Table(name="publication")
@DynamicUpdate
public class Publication extends AbstractModelObject {
	@Id @GeneratedValue
	@Column(name="publication_id")
	private int publicationId;

	@Column(name = "date", columnDefinition="DATE")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(name="publication_name", nullable = false)
	private String publicationName = null;
	
	@AssociationType(type="Staff")
	@ManyToOne
	@JoinColumn(name="staff_id")
	private Staff staff;
    
	

	public int getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(int publicationId) {
		int oldValue = this.publicationId;
		this.publicationId = publicationId;
		firePropertyChange("publicationId", oldValue, publicationId);
	}

	public String getPublicationName() {
		return publicationName;
	}

	public void setPublicationName(String publicationName) {
		String oldValue = this.publicationName;
		this.publicationName = publicationName;
		firePropertyChange("publicationName", oldValue, publicationName);
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		Date oldValue = this.date;
		this.date = date;
		firePropertyChange("date", oldValue, date);
	}
	
}
