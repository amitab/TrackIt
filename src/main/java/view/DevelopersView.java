package view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;

public class DevelopersView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DevelopersView(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Label lblEntirelyDevelopedBy = new Label(this, SWT.NONE);
		FormData fd_lblEntirelyDevelopedBy = new FormData();
		fd_lblEntirelyDevelopedBy.top = new FormAttachment(0, 177);
		fd_lblEntirelyDevelopedBy.left = new FormAttachment(0, 60);
		lblEntirelyDevelopedBy.setLayoutData(fd_lblEntirelyDevelopedBy);
		lblEntirelyDevelopedBy.setText("Entirely developed by Amitabh Das. No thanks to his team... or the teachers.");
		
		Label lblDevelopers = new Label(this, SWT.NONE);
		lblDevelopers.setFont(SWTResourceManager.getFont("Segoe UI", 20, SWT.NORMAL));
		FormData fd_lblDevelopers = new FormData();
		fd_lblDevelopers.bottom = new FormAttachment(lblEntirelyDevelopedBy, -55);
		fd_lblDevelopers.left = new FormAttachment(lblEntirelyDevelopedBy, 0, SWT.LEFT);
		lblDevelopers.setLayoutData(fd_lblDevelopers);
		lblDevelopers.setText("Developers");
		
		Label lblAbhilashAndAashrit = new Label(this, SWT.NONE);
		FormData fd_lblAbhilashAndAashrit = new FormData();
		fd_lblAbhilashAndAashrit.top = new FormAttachment(lblEntirelyDevelopedBy, 6);
		fd_lblAbhilashAndAashrit.left = new FormAttachment(lblEntirelyDevelopedBy, 0, SWT.LEFT);
		lblAbhilashAndAashrit.setLayoutData(fd_lblAbhilashAndAashrit);
		lblAbhilashAndAashrit.setText("Abhilash and Aashrit are douchebags. Idiots.");

	}
}
