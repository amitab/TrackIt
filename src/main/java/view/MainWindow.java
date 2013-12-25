package view;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import controller.MainWindowController;

public class MainWindow {

	protected Shell shell;
	private Menu menuBar;
	private Label label;
	private MainWindowController controller;

	private MenuItem fileMenu, publicationMenu, workshopMenu, aboutMenu;
	private Menu fileSubMenu, publicationSubMenu, workshopSubMenu, aboutSubMenu, querySubMenu;
	private MenuItem mntmExit, createPublicationMenuItem, viewAllPubMenuItem, upcomingWorkMenuItem, 
	createWorkMenuItem, developersMenuItem, homeMenuItem, queryMenuItem, staffQueryMenuItem;
	
	private DevelopersView devComposite;
	private LandingView landingComposite;
	private AllPublicationsView allPublicationsComposite;
	
	private StaffQueryView staffQueryComposite;
	

	public MainWindowController getController() {
		return controller;
	}
	
	public void setController(MainWindowController controller) {
		this.controller = controller;
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
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		try {
			Display display = Display.getDefault();
			Realm.runWithDefault(SWTObservables.getRealm(display),new Runnable()
		      {
		         public void run() {
					Display display = Display.getDefault();
					createContents();
					shell.open();
					shell.layout();
					while (!shell.isDisposed()) {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					}
		         }
		      });
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void hideAll() {
		Control[] composites = shell.getChildren();
		for(Control control : composites) {
			control.setVisible(false);
		}
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(800, 600);
		shell.setText("SWT Application");
		shell.setLayout(new FormLayout());
		
		label = new Label(shell, SWT.CENTER);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0);
		fd_label.left = new FormAttachment(0);
		label.setLayoutData(fd_label);
	    label.setBounds(shell.getClientArea());

	    menuBar = new Menu(shell, SWT.BAR);

	    shell.setMenuBar(menuBar);
	    
	    // The file Menu
	    
	    fileMenu = new MenuItem(menuBar, SWT.CASCADE);
	    fileMenu.setText("File");
	    
	    fileSubMenu = new Menu(fileMenu);
	    fileMenu.setMenu(fileSubMenu);
	    
	    homeMenuItem = new MenuItem(fileSubMenu, SWT.NONE);
	    homeMenuItem.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		hideAll();
	    		landingComposite.setVisible(true);
	    	}
	    });
	    homeMenuItem.setText("Home");
	    
	    mntmExit = new MenuItem(fileSubMenu, SWT.NONE);
	    mntmExit.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		close();
	    	}
	    });
	    mntmExit.setText("Exit");
	    
	    // The publications menu
	    
	    publicationMenu = new MenuItem(menuBar, SWT.CASCADE);
	    publicationMenu.setText("Publication");
	    
	    publicationSubMenu = new Menu(publicationMenu);
	    publicationMenu.setMenu(publicationSubMenu);
	    
	    createPublicationMenuItem = new MenuItem(publicationSubMenu, SWT.NONE);
	    createPublicationMenuItem.setText("Create a new publication");
	    
	    viewAllPubMenuItem = new MenuItem(publicationSubMenu, SWT.NONE);
	    viewAllPubMenuItem.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		hideAll();
	    		allPublicationsComposite.setPublications(controller.getAllYourPublications());
	    		allPublicationsComposite.setVisible(true);
	    	}
	    });
	    viewAllPubMenuItem.setText("View all publications");
	    
	    // The workshop menu
	    
	    workshopMenu = new MenuItem(menuBar, SWT.CASCADE);
	    workshopMenu.setText("Workshop");
	    
	    workshopSubMenu = new Menu(workshopMenu);
	    workshopMenu.setMenu(workshopSubMenu);
	    
	    upcomingWorkMenuItem = new MenuItem(workshopSubMenu, SWT.NONE);
	    upcomingWorkMenuItem.setText("View upcoming workshops");
	    
	    createWorkMenuItem = new MenuItem(workshopSubMenu, SWT.NONE);
	    createWorkMenuItem.setText("Create a new workshop");
	    
	    // The about menu
	    
	    aboutMenu = new MenuItem(menuBar, SWT.CASCADE);
	    aboutMenu.setText("About");
	    
	    aboutSubMenu = new Menu(aboutMenu);
	    aboutMenu.setMenu(aboutSubMenu);
	    
	    developersMenuItem = new MenuItem(aboutSubMenu, SWT.NONE);
	    developersMenuItem.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		hideAll();
	    		devComposite.setVisible(true);
	    	}
	    });
	    developersMenuItem.setText("Developers");
	    
	    // The Query Menu
	    
	    queryMenuItem = new MenuItem(menuBar, SWT.CASCADE);
	    queryMenuItem.setText("Query");
	    
	    querySubMenu = new Menu(queryMenuItem);
	    queryMenuItem.setMenu(querySubMenu);
	    
	    staffQueryMenuItem = new MenuItem(querySubMenu, SWT.NONE);
	    staffQueryMenuItem.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		hideAll();
	    		staffQueryComposite.setVisible(true);
	    	}
	    });
	    staffQueryMenuItem.setText("Staff Query");
	    
	    
	    // Composites
	    FormData compositeFormData = new FormData();
	    compositeFormData.left = new FormAttachment(label, 10);
	    compositeFormData.right = new FormAttachment(label, 774, SWT.RIGHT);
	    compositeFormData.top = new FormAttachment(0, 10);
	    compositeFormData.bottom = new FormAttachment(0, 532);
	    
	    
	    landingComposite = new LandingView(shell, SWT.NONE);
	    landingComposite.setLayoutData(compositeFormData);
	    landingComposite.setVisible(true);
	    
	    devComposite = new DevelopersView(shell, SWT.NONE);
	    devComposite.setLayoutData(compositeFormData);
	    devComposite.setVisible(false);
	    
	    allPublicationsComposite = new AllPublicationsView(shell, SWT.NONE);
	    allPublicationsComposite.setLayoutData(compositeFormData);
	    allPublicationsComposite.setVisible(false);
	    
	    staffQueryComposite = new StaffQueryView(shell, SWT.NONE);
	    staffQueryComposite.setLayoutData(compositeFormData);
	    staffQueryComposite.setVisible(false);
	    
	}
}
