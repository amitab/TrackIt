package controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import dao.HibernateUtil;
import dto.Publication;
import trackIt.TrackIt;
import view.MainWindow;

public class MainWindowController {
	
	MainWindow window;
	
	public MainWindowController() {
		try {
			window = new MainWindow();
			window.setController(this);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Publication> getAllYourPublications() {
		List<Publication> publications = new ArrayList<Publication>();
		HibernateUtil.openSession();
		
		Criteria criteria = HibernateUtil.getSession().createCriteria(Publication.class, "publication")
				.createCriteria("publication.staff", "staff");
		criteria.add(Restrictions.eq("staff.staffId", TrackIt.staff.getStaffId()));
		publications = criteria.list();
		HibernateUtil.closeSession();
		
		return publications;
	}
	
}
