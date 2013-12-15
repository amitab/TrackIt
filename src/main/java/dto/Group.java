package dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Amitab
 *
 */
@Entity
@Table(name="groups")
final public class Group {
	
	@Id @GeneratedValue
	@Column(name="group_id")
	private int groupId;
	
	@Column(name="group_name", unique = true, nullable = false)
	private String groupName = null;
	
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}
