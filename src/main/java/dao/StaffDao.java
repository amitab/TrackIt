package dao;

public class StaffDao extends GenericDao {
	
	StaffDao() throws ClassNotFoundException {
		currentClass = Class.forName("dto.Staff");
	}
	
}
