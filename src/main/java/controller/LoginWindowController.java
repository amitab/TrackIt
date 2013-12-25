package controller;

import trackIt.TrackIt;
import view.LoginWindow;
import dao.HibernateUtil;
import dao.StaffDao;

public class LoginWindowController {
	
	LoginWindow window;
	
	public LoginWindowController() {
		try {
			window = new LoginWindow();
			window.setController(this);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loginButtonClick() {
		String email = window.getLoginView().getEmail();
		String password = window.getLoginView().getPassword();
		
		try {
			StaffDao staffdao = new StaffDao();
			
			HibernateUtil.openSession();
			TrackIt.staff = staffdao.Authenticate(email, password);
			HibernateUtil.closeSession();
			
			if(TrackIt.staff == null) {
				window.displayError("Error", "Y U NO MAKE ACCOUNT?");
			} else {
				window.displayInfo("Success", "Aww yeah! Welcome back, " + TrackIt.staff.getFirstName() + "!");
				window.close();
				MainWindowController mw = new MainWindowController();
			}
			
		} catch (Exception e) {
			window.displayError("Error", e.getMessage());
		}
		
	}
	
}
