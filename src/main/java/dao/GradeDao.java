package dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import dto.ClassSession;
import dto.Grade;
import dto.TimeTable;

public class GradeDao extends GenericDao {
	
	private Grade grade = null;
	
	public GradeDao() throws ClassNotFoundException {
		currentClass = Class.forName("dto.Grade");
	}
	
	public Grade getGradeBySectionYear(Character section, Integer year) throws Exception {
		try {

			HibernateUtil.startTransaction();
			Criteria criteria = HibernateUtil.getSession().createCriteria(currentClass, currentClass.getSimpleName())
					.add(Restrictions.eq(currentClass.getSimpleName() + ".section", section))
					.add(Restrictions.eq(currentClass.getSimpleName() + ".year", year));
			grade = (Grade) criteria.uniqueResult();
			return grade;
			
		} catch (HibernateException e) {
			throw new Exception("Could not retrieve Grade : " + e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findFreeTeachersForHour(Character section, Integer year, Integer hour, String dayName) {
		
		Query query = HibernateUtil.getSession().createSQLQuery(
				"SELECT DISTINCT CONCAT( s.first_name,  ' ', s.last_name ) AS teacher "
				+ "FROM staff s, grade g, grade_staff_incharge gs "
				+ "WHERE g.grade_id = gs.grade_id "
				+ "AND g.section = :section "
				+ "AND g.year = :year "
				+ "AND gs.staff_id = s.staff_id "
				+ "AND s.staff_id NOT "
				+ "IN ( "
				+ "SELECT cs.staff_id "
				+ "FROM timetable t, class_session cs "
				+ "WHERE cs.timetable_id = t.timetable_id "
				+ "AND t.day_name =  :dayName "
				+ "AND cs.hour = :hour "
				+ ")"
		);
		query.setParameter("section", section);
		query.setParameter("year", year);
		query.setParameter("dayName", dayName);
		query.setParameter("hour", hour);
		
		return query.list();
		
	}
	
	public void extractTimeTable() {
		for(TimeTable t : grade.getTimetableList()) {
			System.out.print(t.getDayName() + " : ");
			for(ClassSession cs : t.getClassSessions()) {
				System.out.print(cs.getCourse().getCourseName() + ", ");
			}
			System.out.println();
		}
	}
	
}
