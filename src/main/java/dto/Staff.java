package dto;

import helper.AbstractModelObject;
import helper.ConversionUtil;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import annotation.AssociationType;
import annotation.Expose;

/**
 * @author Amitab
 *
 */
@Entity
@Table(name="staff")
@DynamicUpdate
final public class Staff extends AbstractModelObject {
	
	@Expose(show=false)
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

	@Column(name = "date_of_join", columnDefinition="DATE")
	@Temporal(TemporalType.DATE)
	private Date dateOfJoin = null;
	
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
    
	@AssociationType(type="Publication")
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="staff")
    @NotFound(action=NotFoundAction.IGNORE)
	private Collection<Publication> publicationList = new ArrayList<Publication>();

    @AssociationType(type="Workshop")
    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinTable(name="staff_workshop", 
    joinColumns={@JoinColumn(name="staff_id")}, 
    inverseJoinColumns={@JoinColumn(name="workshop_id")})
	private Collection<Workshop> workshopList = new ArrayList<Workshop>();

    @AssociationType(type="ExternalLecture")
    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinTable(name="staff_lecture", 
    joinColumns={@JoinColumn(name="staff_id")}, 
    inverseJoinColumns={@JoinColumn(name="external_lecture_id")})
	private Collection<ExternalLecture> externalLectures = new ArrayList<ExternalLecture>();
    
	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		int oldValue = this.staffId;
		this.staffId = staffId;
		firePropertyChange("staffId", oldValue, staffId);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		String oldValue = this.firstName;
		this.firstName = firstName;
		firePropertyChange("firstName", oldValue, firstName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		String oldValue = this.lastName;
		this.lastName = lastName;
		firePropertyChange("lastName", oldValue, lastName);
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		String oldValue = this.Email;
		Email = email;
		firePropertyChange("Email", oldValue, email);
	}
	
	public double getSalary() {
		return salary;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		Date oldValue = this.dateOfBirth;
		this.dateOfBirth = dateOfBirth;
		firePropertyChange("dateOfBirth", oldValue, dateOfBirth);
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

	public Collection<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(Collection<Publication> publicationList) {
		this.publicationList = publicationList;
	}

	public Collection<Workshop> getWorkshopList() {
		return workshopList;
	}

	public void setWorkshopList(Collection<Workshop> workshopList) {
		this.workshopList = workshopList;
	}

	public void setSalary(Double salary) {
		Double oldValue = this.salary;
		this.salary = salary;
		firePropertyChange("salary", oldValue, salary);
	}

	public Collection<ExternalLecture> getExternalLectures() {
		return externalLectures;
	}

	public void setExternalLectures(Collection<ExternalLecture> externalLectures) {
		this.externalLectures = externalLectures;
	}

	public Date getDateOfJoin() {
		return dateOfJoin;
	}

	public void setDateOfJoin(Date dateOfJoin) {
		Date oldValue = this.dateOfJoin;
		this.dateOfJoin = dateOfJoin;
		firePropertyChange("dateOfJoin", oldValue, dateOfJoin);
	}
	
}
