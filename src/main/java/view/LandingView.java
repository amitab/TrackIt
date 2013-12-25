package view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;

import trackIt.TrackIt;

public class LandingView extends Composite {
	private Text name;
	private Text dob;
	private Text doj;
	private Text email;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LandingView(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Label lblDashboard = new Label(this, SWT.NONE);
		lblDashboard.setFont(SWTResourceManager.getFont("Segoe UI", 20, SWT.NORMAL));
		FormData fd_lblDashboard = new FormData();
		fd_lblDashboard.top = new FormAttachment(0, 70);
		fd_lblDashboard.left = new FormAttachment(0, 60);
		lblDashboard.setLayoutData(fd_lblDashboard);
		lblDashboard.setText("DashBoard");
		
		Label lblName = new Label(this, SWT.NONE);
		FormData fd_lblName = new FormData();
		fd_lblName.top = new FormAttachment(lblDashboard, 36);
		fd_lblName.left = new FormAttachment(0, 113);
		lblName.setLayoutData(fd_lblName);
		lblName.setText("Name :");
		
		name = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		FormData fd_name = new FormData();
		fd_name.right = new FormAttachment(lblName, 283, SWT.RIGHT);
		fd_name.left = new FormAttachment(lblName, 24);
		name.setLayoutData(fd_name);
		
		Label lblDateOfBirth = new Label(this, SWT.NONE);
		FormData fd_lblDateOfBirth = new FormData();
		fd_lblDateOfBirth.top = new FormAttachment(lblName, 28);
		fd_lblDateOfBirth.right = new FormAttachment(lblName, 0, SWT.RIGHT);
		lblDateOfBirth.setLayoutData(fd_lblDateOfBirth);
		lblDateOfBirth.setText("Date of Birth :");
		
		Label lblDateOfJoining = new Label(this, SWT.NONE);
		FormData fd_lblDateOfJoining = new FormData();
		fd_lblDateOfJoining.top = new FormAttachment(lblDateOfBirth, 27);
		fd_lblDateOfJoining.right = new FormAttachment(lblName, 0, SWT.RIGHT);
		lblDateOfJoining.setLayoutData(fd_lblDateOfJoining);
		lblDateOfJoining.setText("Date of Joining :");
		
		Label lblEmail = new Label(this, SWT.NONE);
		FormData fd_lblEmail = new FormData();
		fd_lblEmail.top = new FormAttachment(lblDateOfJoining, 27);
		fd_lblEmail.right = new FormAttachment(lblName, 0, SWT.RIGHT);
		lblEmail.setLayoutData(fd_lblEmail);
		lblEmail.setText("Email :");
		
		dob = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		fd_name.bottom = new FormAttachment(100, -358);
		FormData fd_dob = new FormData();
		fd_dob.right = new FormAttachment(name, 0, SWT.RIGHT);
		fd_dob.top = new FormAttachment(name, 19);
		fd_dob.left = new FormAttachment(lblDateOfBirth, 24);
		dob.setLayoutData(fd_dob);
		
		doj = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		FormData fd_doj = new FormData();
		fd_doj.right = new FormAttachment(name, 0, SWT.RIGHT);
		fd_doj.top = new FormAttachment(dob, 21);
		fd_doj.left = new FormAttachment(lblDateOfJoining, 24);
		doj.setLayoutData(fd_doj);
		
		email = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		FormData fd_email = new FormData();
		fd_email.right = new FormAttachment(name, 0, SWT.RIGHT);
		fd_email.top = new FormAttachment(doj, 21);
		fd_email.left = new FormAttachment(lblEmail, 24);
		email.setLayoutData(fd_email);
		
		Label lblYouAre = new Label(this, SWT.NONE);
		FormData fd_lblYouAre = new FormData();
		fd_lblYouAre.left = new FormAttachment(lblDashboard, 0, SWT.LEFT);
		lblYouAre.setLayoutData(fd_lblYouAre);
		
		Label lblYou = new Label(this, SWT.NONE);
		fd_lblYouAre.bottom = new FormAttachment(lblYou, -6);
		FormData fd_lblYou = new FormData();
		fd_lblYou.left = new FormAttachment(lblDashboard, 0, SWT.LEFT);
		lblYou.setLayoutData(fd_lblYou);
		
		Label lblYouHaveQuerying = new Label(this, SWT.NONE);
		fd_lblYou.bottom = new FormAttachment(lblYouHaveQuerying, -6);
		FormData fd_lblYouHaveQuerying = new FormData();
		fd_lblYouHaveQuerying.top = new FormAttachment(0, 462);
		fd_lblYouHaveQuerying.left = new FormAttachment(lblDashboard, 0, SWT.LEFT);
		lblYouHaveQuerying.setLayoutData(fd_lblYouHaveQuerying);
		lblYouHaveQuerying.setText("You have querying rights.");
		
		if(TrackIt.staff.getGroup().getGroupName().equalsIgnoreCase("admin")) {
			lblYouAre.setText("You are the admin.");
			lblYou.setText("You have editing/deleting rights.");
		}
		else {
			lblYouAre.setText("You are not the admin.");
			lblYou.setText("You do not have editing/deleting rights.");
		}
		
		name.setText(TrackIt.staff.getFirstName() + " " + TrackIt.staff.getLastName());
		dob.setText(TrackIt.staff.getDateOfBirth().toString());
		doj.setText(TrackIt.staff.getDateOfJoin().toString());
		email.setText(TrackIt.staff.getEmail());
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
