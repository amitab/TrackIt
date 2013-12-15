package dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import annotation.AssociationType;

/**
 * @author Amitab
 *
 */
@Entity
@Table(name="staff")
final public class Staff {
	
	@Id @GeneratedValue
	@Column(name="staff_id")
	private int staffId;
	
	@Column(name="first_name", nullable = false)
	private String firstName = null;
	
	@Column(name="last_name", nullable = false)
	private String lastName = null;
	
	@Column(name="email", unique = true, nullable = false)
	private String Email = null;
	
	@Column(name="salary", precision=10, scale=2)
	private Double salary;
	
	@Column(name = "date_of_birth", columnDefinition="DATE")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth = null;

	@AssociationType(type="Authentication")
    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Authentication auth = null;

	@AssociationType(type="Group")
    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Group group = null;
    
    @AssociationType(type="Presentation")
    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinTable(name="staff_presentation", 
    joinColumns={@JoinColumn(name="staff_id")}, 
    inverseJoinColumns={@JoinColumn(name="presentation_id")})
	private Collection<Presentation> presentationList = new ArrayList<Presentation>();

    @AssociationType(type="Workshop")
    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinTable(name="staff_workshop", 
    joinColumns={@JoinColumn(name="staff_id")}, 
    inverseJoinColumns={@JoinColumn(name="workshop_id")})
	private Collection<Workshop> workshopList = new ArrayList<Workshop>();
    
	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Authentication getAuth() {
		if(auth == null) auth = new Authentication();
		return auth;
	}

	public void setAuth(Authentication auth) {
		this.auth = auth;
	}

	public Group getGroup() {
		if(group == null) group = new Group();
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Collection<Presentation> getPresentationList() {
		return presentationList;
	}

	public void setPresentationList(Collection<Presentation> presentationList) {
		this.presentationList = presentationList;
	}

	public Collection<Workshop> getWorkshopList() {
		return workshopList;
	}

	public void setWorkshopList(Collection<Workshop> workshopList) {
		this.workshopList = workshopList;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
}
