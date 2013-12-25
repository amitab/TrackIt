package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.layout.TableColumnLayout;

import dto.Publication;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.swt.widgets.DateTime;

public class AllPublicationsView extends Composite {
	private DataBindingContext m_bindingContext;
	private Table table;
	private List<Publication> publications = new ArrayList<Publication>();
	private TableViewer tableViewer;
	private MainWindow window;
	private TableColumnLayout tcl_composite;
	private Text publicationId;
	private Text publicationName;
	private DateTime date;
	
	public void display() {
		for(Publication p : publications) {
			System.out.println(p.getPublicationName());
		}
		System.out.println();
	}
	
	public void setPublications(List<Publication> publications) {		
		this.publications.addAll(publications);
		display();
		tableViewer.refresh();
	}
	
	public void setWindow(MainWindow window) {
		this.window = window;
	}
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AllPublicationsView(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		FormData fd_scrolledComposite = new FormData();
		fd_scrolledComposite.top = new FormAttachment(0, 10);
		fd_scrolledComposite.left = new FormAttachment(0, 10);
		fd_scrolledComposite.bottom = new FormAttachment(0, 512);
		fd_scrolledComposite.right = new FormAttachment(0, 480);
		scrolledComposite.setLayoutData(fd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		tcl_composite = new TableColumnLayout();
		composite.setLayout(tcl_composite);
		
		tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		// publication columns
		createPublicationColumns();
		
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		// Editing Parts
		
		Button btnSave = new Button(this, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display();
				tableViewer.refresh();
			}
		});
		FormData fd_btnSave = new FormData();
		fd_btnSave.left = new FormAttachment(100, -90);
		fd_btnSave.bottom = new FormAttachment(100, -10);
		fd_btnSave.right = new FormAttachment(100, -10);
		btnSave.setLayoutData(fd_btnSave);
		btnSave.setText("Save");
		
		publicationId = new Text(this, SWT.BORDER);
		FormData fd_publicationId = new FormData();
		fd_publicationId.right = new FormAttachment(100, -10);
		fd_publicationId.left = new FormAttachment(100, -153);
		publicationId.setLayoutData(fd_publicationId);
		
		publicationName = new Text(this, SWT.BORDER);
		fd_publicationId.bottom = new FormAttachment(100, -465);
		FormData fd_publicationName = new FormData();
		fd_publicationName.right = new FormAttachment(btnSave, 0, SWT.RIGHT);
		fd_publicationName.left = new FormAttachment(publicationId, 0, SWT.LEFT);
		fd_publicationName.top = new FormAttachment(publicationId, 6);
		publicationName.setLayoutData(fd_publicationName);
		
		Label lblPublicationId = new Label(this, SWT.NONE);
		FormData fd_lblPublicationId = new FormData();
		fd_lblPublicationId.top = new FormAttachment(publicationId, 3, SWT.TOP);
		fd_lblPublicationId.right = new FormAttachment(publicationId, -6);
		lblPublicationId.setLayoutData(fd_lblPublicationId);
		lblPublicationId.setText("Publication Id :");
		
		Label lblPublicationName = new Label(this, SWT.NONE);
		FormData fd_lblPublicationName = new FormData();
		fd_lblPublicationName.top = new FormAttachment(publicationName, 3, SWT.TOP);
		fd_lblPublicationName.right = new FormAttachment(publicationName, -6);
		lblPublicationName.setLayoutData(fd_lblPublicationName);
		lblPublicationName.setText("Publication Name :");
		
		date = new DateTime(this, SWT.DROP_DOWN);
		FormData fd_date = new FormData();
		fd_date.right = new FormAttachment(btnSave, 0, SWT.RIGHT);
		fd_date.top = new FormAttachment(publicationName, 6);
		fd_date.left = new FormAttachment(publicationId, 0, SWT.LEFT);
		date.setLayoutData(fd_date);
		
		Label lblPublicationDate = new Label(this, SWT.NONE);
		FormData fd_lblPublicationDate = new FormData();
		fd_lblPublicationDate.top = new FormAttachment(date, 3, SWT.TOP);
		fd_lblPublicationDate.right = new FormAttachment(lblPublicationId, 0, SWT.RIGHT);
		lblPublicationDate.setLayoutData(fd_lblPublicationDate);
		lblPublicationDate.setText("Publication Date :");
		
		m_bindingContext = initDataBindings();

	}
	
	public void removeColumns() {
		table.setRedraw( false );
		while ( table.getColumnCount() > 0 ) {
		    table.getColumns()[ 0 ].dispose();
		}
		table.setRedraw( true );
	}
	
	public void createPublicationColumns() {
		// publication columns
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tcl_composite.setColumnData(tblclmnNewColumn, new ColumnPixelData(150, true, true));
		tblclmnNewColumn.setText("Publication Id");
		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnNewColumn = tableViewerColumn.getColumn();
		tcl_composite.setColumnData(tblclmnNewColumn, new ColumnPixelData(150, true, true));
		tblclmnNewColumn.setText("Publication Name");tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnNewColumn = tableViewerColumn.getColumn();
		tcl_composite.setColumnData(tblclmnNewColumn, new ColumnPixelData(150, true, true));
		tblclmnNewColumn.setText("Publication Date");
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = BeansObservables.observeMaps(listContentProvider.getKnownElements(), Publication.class, new String[]{"publicationId", "publicationName", "date"});
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewer.setContentProvider(listContentProvider);
		//
		IObservableList selfList = Properties.selfList(Publication.class).observe(publications);
		tableViewer.setInput(selfList);
		//
		IObservableValue observeSingleSelectionTableViewer = ViewerProperties.singleSelection().observe(tableViewer);
		IObservableValue tableViewerPublicationIdObserveDetailValue = BeanProperties.value(Publication.class, "publicationId", int.class).observeDetail(observeSingleSelectionTableViewer);
		IObservableValue observeTextPublicationIdObserveWidget = WidgetProperties.text(SWT.Modify).observe(publicationId);
		bindingContext.bindValue(tableViewerPublicationIdObserveDetailValue, observeTextPublicationIdObserveWidget, null, null);
		//
		IObservableValue observeSingleSelectionTableViewer_1 = ViewerProperties.singleSelection().observe(tableViewer);
		IObservableValue tableViewerPublicationNameObserveDetailValue = BeanProperties.value(Publication.class, "publicationName", String.class).observeDetail(observeSingleSelectionTableViewer_1);
		IObservableValue observeTextPublicationNameObserveWidget = WidgetProperties.text(SWT.Modify).observe(publicationName);
		bindingContext.bindValue(tableViewerPublicationNameObserveDetailValue, observeTextPublicationNameObserveWidget, null, null);
		//
		IObservableValue observeSingleSelectionTableViewer_2 = ViewerProperties.singleSelection().observe(tableViewer);
		IObservableValue tableViewerDateObserveDetailValue = BeanProperties.value(Publication.class, "date", Date.class).observeDetail(observeSingleSelectionTableViewer_2);
		IObservableValue observeSelectionDateObserveWidget = WidgetProperties.selection().observe(date);
		bindingContext.bindValue(tableViewerDateObserveDetailValue, observeSelectionDateObserveWidget, null, null);
		//
		return bindingContext;
	}
}
