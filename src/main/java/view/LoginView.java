package view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class LoginView extends Composite {
	private Text text;
	private Text text_1;
	private LoginWindow loginWindow;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	public String getEmail() {
		return text.getText();
	}
	
	public String getPassword() {
		return text_1.getText();
	}
	
	public LoginView(Composite parent, int style, LoginWindow window) {
		super(parent, style);
		this.loginWindow = window;
		setLayout(new FormLayout());
		
		text = new Text(this, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(100, -66);
		text.setLayoutData(fd_text);
		
		text_1 = new Text(this, SWT.BORDER | SWT.PASSWORD);
		fd_text.bottom = new FormAttachment(text_1, -18);
		FormData fd_text_1 = new FormData();
		fd_text_1.left = new FormAttachment(text, 0, SWT.LEFT);
		fd_text_1.right = new FormAttachment(100, -66);
		fd_text_1.top = new FormAttachment(0, 141);
		text_1.setLayoutData(fd_text_1);
		
		Button btnLogin = new Button(this, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loginWindow.getController().loginButtonClick();
			}
		});
		FormData fd_btnLogin = new FormData();
		fd_btnLogin.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_btnLogin.left = new FormAttachment(100, -132);
		btnLogin.setLayoutData(fd_btnLogin);
		btnLogin.setText("Login");
		
		Button btnForgotPassword = new Button(this, SWT.NONE);
		fd_btnLogin.top = new FormAttachment(btnForgotPassword, 0, SWT.TOP);
		btnForgotPassword.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loginWindow.getLoginView().setVisible(false);
				loginWindow.getForgotPasswordView().setVisible(true);
			}
		});
		FormData fd_btnForgotPassword = new FormData();
		fd_btnForgotPassword.bottom = new FormAttachment(100, -33);
		fd_btnForgotPassword.left = new FormAttachment(0, 41);
		fd_btnForgotPassword.right = new FormAttachment(0, 229);
		btnForgotPassword.setLayoutData(fd_btnForgotPassword);
		btnForgotPassword.setText("Oh Noes! I forgot my password!");
		
		Label lblLoginToRepullet = new Label(this, SWT.NONE);
		lblLoginToRepullet.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		FormData fd_lblLoginToRepullet = new FormData();
		fd_lblLoginToRepullet.left = new FormAttachment(0, 41);
		fd_lblLoginToRepullet.top = new FormAttachment(0, 29);
		lblLoginToRepullet.setLayoutData(fd_lblLoginToRepullet);
		lblLoginToRepullet.setText("Login to Repullet");
		
		Label lblEmail = new Label(this, SWT.NONE);
		fd_text.left = new FormAttachment(lblEmail, 20);
		FormData fd_lblEmail = new FormData();
		fd_lblEmail.left = new FormAttachment(0, 64);
		lblEmail.setLayoutData(fd_lblEmail);
		lblEmail.setText("Email :");
		
		Label lblPassword = new Label(this, SWT.NONE);
		fd_lblEmail.bottom = new FormAttachment(lblPassword, -24);
		FormData fd_lblPassword = new FormData();
		fd_lblPassword.top = new FormAttachment(0, 144);
		fd_lblPassword.right = new FormAttachment(lblEmail, 0, SWT.RIGHT);
		lblPassword.setLayoutData(fd_lblPassword);
		lblPassword.setText("Password :");

	}
}
