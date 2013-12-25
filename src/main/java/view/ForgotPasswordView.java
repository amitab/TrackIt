package view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ForgotPasswordView extends Composite {
	private Text text;
	private Text text_1;
	private LoginWindow loginWindow;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ForgotPasswordView(Composite parent, int style, LoginWindow window) {
		super(parent, style);
		this.loginWindow = window;
		setLayout(new FormLayout());
		
		text = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		FormData fd_text = new FormData();
		text.setLayoutData(fd_text);
		
		text_1 = new Text(this, SWT.BORDER);
		fd_text.bottom = new FormAttachment(text_1, -12);
		FormData fd_text_1 = new FormData();
		fd_text_1.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_text_1.top = new FormAttachment(0, 143);
		text_1.setLayoutData(fd_text_1);
		
		Button btnRecoverPassword = new Button(this, SWT.NONE);
		fd_text.right = new FormAttachment(btnRecoverPassword, 0, SWT.RIGHT);
		FormData fd_btnRecoverPassword = new FormData();
		fd_btnRecoverPassword.right = new FormAttachment(100, -41);
		fd_btnRecoverPassword.left = new FormAttachment(100, -163);
		btnRecoverPassword.setLayoutData(fd_btnRecoverPassword);
		btnRecoverPassword.setText("Recover Password");
		
		Label lblForgotPassword = new Label(this, SWT.NONE);
		lblForgotPassword.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.NORMAL));
		FormData fd_lblForgotPassword = new FormData();
		fd_lblForgotPassword.top = new FormAttachment(0, 41);
		fd_lblForgotPassword.left = new FormAttachment(0, 40);
		lblForgotPassword.setLayoutData(fd_lblForgotPassword);
		lblForgotPassword.setText("Forgot Password");
		
		Label lblSecretQuestion = new Label(this, SWT.NONE);
		fd_text.left = new FormAttachment(0, 174);
		FormData fd_lblSecretQuestion = new FormData();
		fd_lblSecretQuestion.right = new FormAttachment(text, -26);
		lblSecretQuestion.setLayoutData(fd_lblSecretQuestion);
		lblSecretQuestion.setText("Secret Question :");
		
		Label lblSecretAnswer = new Label(this, SWT.NONE);
		fd_lblSecretQuestion.bottom = new FormAttachment(lblSecretAnswer, -18);
		fd_text_1.left = new FormAttachment(lblSecretAnswer, 26);
		FormData fd_lblSecretAnswer = new FormData();
		fd_lblSecretAnswer.top = new FormAttachment(0, 146);
		fd_lblSecretAnswer.right = new FormAttachment(lblSecretQuestion, 0, SWT.RIGHT);
		lblSecretAnswer.setLayoutData(fd_lblSecretAnswer);
		lblSecretAnswer.setText("Secret Answer :");
		
		Button btnBackToLogin = new Button(this, SWT.NONE);
		btnBackToLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loginWindow.getLoginView().setVisible(true);
				loginWindow.getForgotPasswordView().setVisible(false);
			}
		});
		fd_btnRecoverPassword.top = new FormAttachment(btnBackToLogin, 0, SWT.TOP);
		FormData fd_btnBackToLogin = new FormData();
		fd_btnBackToLogin.right = new FormAttachment(lblSecretQuestion, 0, SWT.RIGHT);
		fd_btnBackToLogin.bottom = new FormAttachment(100, -45);
		fd_btnBackToLogin.left = new FormAttachment(lblForgotPassword, 0, SWT.LEFT);
		btnBackToLogin.setLayoutData(fd_btnBackToLogin);
		btnBackToLogin.setText("Back to Login");

	}

}
