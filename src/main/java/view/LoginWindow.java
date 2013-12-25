package view;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

import controller.LoginWindowController;

public class LoginWindow {

	protected Shell shell;
	private LoginView loginComposite;
	private ForgotPasswordView forgotComposite;
	private LoginWindowController controller;
	
	public LoginView getLoginView() {
		return loginComposite;
	}
	
	public ForgotPasswordView getForgotPasswordView() {
		return forgotComposite;
	}
	
	public LoginWindowController getController() {
		return controller;
	}
	
	public void close() {
		shell.dispose();
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LoginWindow window = new LoginWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setController(LoginWindowController controller) {
		this.controller = controller;
	}
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		
		Monitor primary = display.getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = shell.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation (x, y);
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 336);
		shell.setText("SWT Application");
		shell.setLayout(new FormLayout());
		loginComposite = new LoginView(shell, SWT.NONE, this);
		FormData fd_loginComposite = new FormData();
		fd_loginComposite.bottom = new FormAttachment(100, -10);
		fd_loginComposite.right = new FormAttachment(100, -10);
		fd_loginComposite.top = new FormAttachment(0);
		fd_loginComposite.left = new FormAttachment(0);
		loginComposite.setLayoutData(fd_loginComposite);
		loginComposite.setVisible(true);
		
		forgotComposite = new ForgotPasswordView(shell, SWT.NONE, this);
		fd_loginComposite = new FormData();
		fd_loginComposite.bottom = new FormAttachment(100, -10);
		fd_loginComposite.right = new FormAttachment(100, -10);
		fd_loginComposite.top = new FormAttachment(0);
		fd_loginComposite.left = new FormAttachment(0);
		forgotComposite.setLayoutData(fd_loginComposite);
		forgotComposite.setVisible(false);
	}
	
	public void displayError(String title, String message) {
		MessageDialog.openError(shell, title, message);
	}

	public void displayInfo(String title, String message) {
		MessageDialog.openInformation(shell, title, message);
	}
	
}
