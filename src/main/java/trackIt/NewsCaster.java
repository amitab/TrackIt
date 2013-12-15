package trackIt;

import java.util.List;

import org.hibernate.Query;

import dao.HibernateUtil;
import dto.Staff;
import querycomposer.Attribute;
import querycomposer.QueryComposerException;
import querycomposer.Root;

public class NewsCaster {
	
	public static void queryTest() throws QueryComposerException {
		HibernateUtil.openSession();
		Root r = new Root("Staff");
		r.addAttributes(
			Attribute.newInstance("dateOfBirth", "2005-01-03, 2000-02-13, 2009-12-12", Attribute.OP_IN)
		);
		
		Query query = r.prepareQuery();
		List<Staff> list = query.list();
		
		for(Staff s : list) {
			System.out.println(s.getStaffId() + ". " + s.getFirstName());
		}
	}
	
	public static void main(String[] args) {
		try {

			HibernateUtil.openSession();
			Root r = new Root("Staff");
			r.addAttributes(
				Attribute.newInstance("dateOfBirth", "05-2005")
			);
			
			Query query = r.prepareQuery();
			List<Staff> list = query.list();
			
			for(Staff s : list) {
				System.out.println(s.getStaffId() + ". " + s.getFirstName());
			}
			
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
			e.printStackTrace();
		}
	}

}
