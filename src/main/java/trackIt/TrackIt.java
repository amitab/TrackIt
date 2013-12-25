package trackIt;

import java.util.List;

import querycomposer.CriteriaComposer;
import controller.LoginWindowController;
import dao.HibernateUtil;
import dto.Staff;

public class TrackIt {
	public static Staff staff;
	
	public static void main(String[] args) {
		try {
			
			HibernateUtil.getSessionFactory();
			LoginWindowController lwc = new LoginWindowController();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}

}
