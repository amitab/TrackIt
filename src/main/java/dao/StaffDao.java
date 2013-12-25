package dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import dto.Staff;

public class StaffDao extends GenericDao {
	
	public StaffDao() throws ClassNotFoundException {
		currentClass = Class.forName("dto.Staff");
	}
	
	public Staff Authenticate(String email, String password) {
		Staff staff = null;
		
		Criteria criteria = HibernateUtil.getSession().createCriteria(currentClass, currentClass.getSimpleName());
		criteria.createAlias(currentClass.getSimpleName() + ".auth", "auth")
		.add(Restrictions.eq(currentClass.getSimpleName() + ".Email", email))
		.add(Restrictions.eq("auth.pwd", password));
		
		staff = (Staff) criteria.uniqueResult();
		
		return staff;
	}
	
}
