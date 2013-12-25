package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.ProgressBar;

public class Splash {

	protected Shell shell;
	private Display display;

	/**
	 * Launch the application.
	 * @param args
	 */
	
	public void close() {
		shell.dispose();
	}
	
	public static void main(String[] args) {
		try {
			Splash window = new Splash();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
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
		shell = new Shell(display, SWT.NO_TRIM);
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new FormLayout());
		
		Label label = new Label(shell, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Segoe UI", 25, SWT.NORMAL));
		label.setText("Loading Repullet...");
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0, 57);
		fd_label.left = new FormAttachment(0, 43);
		label.setLayoutData(fd_label);
		
		ProgressBar progressBar = new ProgressBar(shell, SWT.NONE);
		FormData fd_progressBar = new FormData();
		fd_progressBar.bottom = new FormAttachment(100, -82);
		fd_progressBar.left = new FormAttachment(label, 0, SWT.LEFT);
		fd_progressBar.right = new FormAttachment(100, -47);
		progressBar.setLayoutData(fd_progressBar);

	}
}
