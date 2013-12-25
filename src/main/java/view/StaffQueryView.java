package view;

import helper.ConversionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.FormAttachment;

import querycomposer.CriteriaComposer;
import querycomposer.QueryComposerException;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.wb.rcp.databinding.TreeBeanAdvisor;
import org.eclipse.wb.rcp.databinding.TreeObservableLabelProvider;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.wb.rcp.databinding.BeansSetObservableFactory;
import org.eclipse.jface.databinding.viewers.ObservableSetTreeContentProvider;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.layout.TableColumnLayout;

import dao.HibernateUtil;
import dto.Staff;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.swt.widgets.Label;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.wb.rcp.databinding.BeansListObservableFactory;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;

public class StaffQueryView extends Composite {
	private DataBindingContext m_bindingContext;
	private MainWindow window;
	
	private List<CriteriaComposer> criteriaList = new ArrayList<CriteriaComposer>();
	private CriteriaComposer criteriaComposer;
	private TreeViewer treeViewer;
	private Text query;
	private Combo variable;
	private Combo operation;
	private Button addCriteria;
	private Button btnDeleteCriteria;
	private Button btnResetAll;
	private Composite composite_1;
	private Table staffTable;
	private TableViewer staffTableViewer;
	
	private List<Staff> staffList = new ArrayList<Staff>();
	private TableColumn tblclmnStaffId;
	private TableViewerColumn tableViewerColumn;
	private TableColumn tblclmnFirstName;
	private TableViewerColumn tableViewerColumn_1;
	private TableColumn tblclmnLastName;
	private TableViewerColumn tableViewerColumn_2;
	private TableColumn tblclmnSalary;
	private TableViewerColumn tableViewerColumn_3;
	private TableColumn tblclmnDateOfBirth;
	private TableViewerColumn tableViewerColumn_4;
	private TableColumn tblclmnDateOfJoin;
	private TableViewerColumn tableViewerColumn_5;
	private TableColumn tblclmnEmail;
	private TableViewerColumn tableViewerColumn_6;
	private Text resultCount;
	
	public void setWindow(MainWindow window) {
		this.window = window;
	}
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StaffQueryView(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new FormLayout());
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 10);
		fd_composite.left = new FormAttachment(0, 10);
		fd_composite.bottom = new FormAttachment(0, 512);
		fd_composite.right = new FormAttachment(0, 210);
		composite.setLayoutData(fd_composite);
		
		treeViewer = new TreeViewer(composite, SWT.BORDER);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent arg0) {
				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
				CriteriaComposer criteriaSel = (CriteriaComposer) selection.getFirstElement();
				
				if(criteriaSel == null) return;
				
				if(!criteriaSel.isRoot() && !criteriaSel.isEntity()) {
					btnDeleteCriteria.setEnabled(true);
					ITreeSelection selectiontree = ((ITreeSelection) treeViewer.getSelection());
					CriteriaComposer criteriaParent = (CriteriaComposer) selectiontree.getPaths()[0].getParentPath().getLastSegment();
					addCriteria.setText("Edit Criteria");
					
					variable.setItems(criteriaParent.getAttributeNames());
					variable.setText(criteriaSel.getReadableCriteriaName());
					operation.setText(criteriaSel.operationName());
					query.setText(criteriaSel.getQuery());
					
				} else if (criteriaSel.isEntity()) {
					btnDeleteCriteria.setEnabled(false);
					addCriteria.setText("Add Criteria");
					
					variable.setItems(criteriaSel.getAttributeNames());
					operation.setText("");
					query.setText("");
				}
			}
		});
		Tree tree = treeViewer.getTree();
		FormData fd_tree = new FormData();
		fd_tree.bottom = new FormAttachment(0, 502);
		fd_tree.right = new FormAttachment(0, 200);
		fd_tree.top = new FormAttachment(0);
		fd_tree.left = new FormAttachment(0);
		tree.setLayoutData(fd_tree);
		
		variable = new Combo(this, SWT.NONE);
		FormData fd_variable = new FormData();
		fd_variable.left = new FormAttachment(composite, 311);
		variable.setLayoutData(fd_variable);
		
		operation = new Combo(this, SWT.NONE);
		fd_variable.bottom = new FormAttachment(operation, -6);
		fd_variable.right = new FormAttachment(operation, 0, SWT.RIGHT);
		FormData fd_operation = new FormData();
		fd_operation.left = new FormAttachment(composite, 311);
		fd_operation.right = new FormAttachment(100, -10);
		operation.setLayoutData(fd_operation);
		operation.setItems(CriteriaComposer.operationNames());
		
		query = new Text(this, SWT.BORDER);
		fd_operation.bottom = new FormAttachment(query, -6);
		FormData fd_query = new FormData();
		fd_query.left = new FormAttachment(composite, 311);
		fd_query.right = new FormAttachment(100, -10);
		query.setLayoutData(fd_query);
		
		addCriteria = new Button(this, SWT.NONE);
		fd_query.bottom = new FormAttachment(addCriteria, -6);
		addCriteria.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
				CriteriaComposer criteriaSel = (CriteriaComposer) selection.getFirstElement();
				if(criteriaSel == null) return;
				if(!criteriaSel.isRoot() && !criteriaSel.isEntity()) {
					criteriaSel.setReadableCriteriaName(variable.getText());
					criteriaSel.setQuery(query.getText());
					criteriaSel.setOperation(operation.getSelectionIndex());
					return;
				}
				if(criteriaSel.isEntity()) {
					criteriaSel.addCriteria(CriteriaComposer.newCriteria(ConversionUtil.fromReadable(variable.getText(), false), query.getText(), operation.getSelectionIndex()));
				}
				treeViewer.refresh();
			}
		});
		FormData fd_addCriteria = new FormData();
		fd_addCriteria.right = new FormAttachment(100, -10);
		fd_addCriteria.bottom = new FormAttachment(composite, 0, SWT.BOTTOM);
		addCriteria.setLayoutData(fd_addCriteria);
		addCriteria.setText("Add Criteria");
		
		Button btnExecuteQuery = new Button(this, SWT.NONE);
		btnExecuteQuery.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CriteriaComposer criteriaComposer = (CriteriaComposer) criteriaList.toArray()[0];
				try {
					HibernateUtil.openSession();
					staffList.clear();
					staffList.addAll((Collection<? extends Staff>) criteriaComposer.distinct().list());
					resultCount.setText(Integer.toString(staffList.size()));
					HibernateUtil.closeSession();
					
					staffTableViewer.refresh();
				} catch (QueryComposerException e1) {
					e1.printStackTrace();
				}
			}
		});
		FormData fd_btnExecuteQuery = new FormData();
		fd_btnExecuteQuery.top = new FormAttachment(addCriteria, 0, SWT.TOP);
		fd_btnExecuteQuery.left = new FormAttachment(composite, 6);
		btnExecuteQuery.setLayoutData(fd_btnExecuteQuery);
		btnExecuteQuery.setText("Execute Query");
		
		composite_1 = new Composite(this, SWT.NONE);
		TableColumnLayout tcl_composite_1 = new TableColumnLayout();
		composite_1.setLayout(tcl_composite_1);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.bottom = new FormAttachment(variable, -6);
		fd_composite_1.right = new FormAttachment(variable, 0, SWT.RIGHT);
		fd_composite_1.left = new FormAttachment(composite, 6);
		fd_composite_1.top = new FormAttachment(0, 10);
		composite_1.setLayoutData(fd_composite_1);
		
		staffTableViewer = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		staffTable = staffTableViewer.getTable();
		staffTable.setHeaderVisible(true);
		staffTable.setLinesVisible(true);
		
		tableViewerColumn = new TableViewerColumn(staffTableViewer, SWT.NONE);
		tblclmnStaffId = tableViewerColumn.getColumn();
		tcl_composite_1.setColumnData(tblclmnStaffId, new ColumnPixelData(53, true, true));
		tblclmnStaffId.setText("Staff Id");
		
		tableViewerColumn_1 = new TableViewerColumn(staffTableViewer, SWT.NONE);
		tblclmnFirstName = tableViewerColumn_1.getColumn();
		tcl_composite_1.setColumnData(tblclmnFirstName, new ColumnPixelData(106, true, true));
		tblclmnFirstName.setText("First Name");
		
		tableViewerColumn_2 = new TableViewerColumn(staffTableViewer, SWT.NONE);
		tblclmnLastName = tableViewerColumn_2.getColumn();
		tcl_composite_1.setColumnData(tblclmnLastName, new ColumnPixelData(110, true, true));
		tblclmnLastName.setText("Last Name");
		
		tableViewerColumn_6 = new TableViewerColumn(staffTableViewer, SWT.NONE);
		tblclmnEmail = tableViewerColumn_6.getColumn();
		tcl_composite_1.setColumnData(tblclmnEmail, new ColumnPixelData(106, true, true));
		tblclmnEmail.setText("Email");
		
		tableViewerColumn_3 = new TableViewerColumn(staffTableViewer, SWT.NONE);
		tblclmnSalary = tableViewerColumn_3.getColumn();
		tcl_composite_1.setColumnData(tblclmnSalary, new ColumnPixelData(93, true, true));
		tblclmnSalary.setText("Salary");
		
		tableViewerColumn_4 = new TableViewerColumn(staffTableViewer, SWT.NONE);
		tblclmnDateOfBirth = tableViewerColumn_4.getColumn();
		tcl_composite_1.setColumnData(tblclmnDateOfBirth, new ColumnPixelData(99, true, true));
		tblclmnDateOfBirth.setText("Date Of Birth");
		
		tableViewerColumn_5 = new TableViewerColumn(staffTableViewer, SWT.NONE);
		tblclmnDateOfJoin = tableViewerColumn_5.getColumn();
		tcl_composite_1.setColumnData(tblclmnDateOfJoin, new ColumnPixelData(150, true, true));
		tblclmnDateOfJoin.setText("Date Of Join");
		
		btnResetAll = new Button(this, SWT.NONE);
		btnResetAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resetStaffCriteriaComposer();
				treeViewer.refresh();
			}
		});
		FormData fd_btnResetAll = new FormData();
		fd_btnResetAll.right = new FormAttachment(btnExecuteQuery, 70, SWT.RIGHT);
		fd_btnResetAll.bottom = new FormAttachment(100, -10);
		fd_btnResetAll.left = new FormAttachment(btnExecuteQuery, 2);
		btnResetAll.setLayoutData(fd_btnResetAll);
		btnResetAll.setText("Reset All");
		
		btnDeleteCriteria = new Button(this, SWT.NONE);
		btnDeleteCriteria.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
				CriteriaComposer criteriaSel = (CriteriaComposer) selection.getFirstElement();
				if(criteriaSel == null) return;
				
				if(!criteriaSel.isRoot() && !criteriaSel.isEntity()) {
					ITreeSelection selectiontree = ((ITreeSelection) treeViewer.getSelection());
					CriteriaComposer criteriaParent = (CriteriaComposer) selectiontree.getPaths()[0].getParentPath().getLastSegment();
					criteriaParent.getCriteriaList().remove(criteriaSel);
				}
				treeViewer.refresh();
			}
		});
		FormData fd_btnDeleteCriteria = new FormData();
		fd_btnDeleteCriteria.top = new FormAttachment(query, 6);
		fd_btnDeleteCriteria.left = new FormAttachment(variable, 0, SWT.LEFT);
		btnDeleteCriteria.setLayoutData(fd_btnDeleteCriteria);
		btnDeleteCriteria.setText("Delete Criteria");
		
		Label lblResultSize = new Label(this, SWT.NONE);
		FormData fd_lblResultSize = new FormData();
		fd_lblResultSize.top = new FormAttachment(variable, 3, SWT.TOP);
		fd_lblResultSize.left = new FormAttachment(composite, 6);
		lblResultSize.setLayoutData(fd_lblResultSize);
		lblResultSize.setText("Result Size :");
		
		resultCount = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		FormData fd_resultCount = new FormData();
		fd_resultCount.left = new FormAttachment(lblResultSize, 6);
		fd_resultCount.top = new FormAttachment(variable, 0, SWT.TOP);
		resultCount.setLayoutData(fd_resultCount);
		
		resetStaffCriteriaComposer();
		
		m_bindingContext = initDataBindings();
		
	}
	
	public void resetStaffCriteriaComposer() {
		if(criteriaList.size() == 0) {
			criteriaComposer = new CriteriaComposer("Staff");
			criteriaList.add(criteriaComposer);
		} else {
			criteriaList.get(0).deleteAll();
		}
		criteriaList.get(0).addCriterias(
				CriteriaComposer.newCriteria("publicationList"),
				CriteriaComposer.newCriteria("workshopList"),
				CriteriaComposer.newCriteria("externalLectures")
		);
		
		try {
			criteriaList.get(0).compileRelations(null);
		} catch (QueryComposerException e1) {
			e1.printStackTrace();
		}
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = BeansObservables.observeMaps(listContentProvider.getKnownElements(), Staff.class, new String[]{"staffId", "firstName", "lastName", "email", "salary", "dateOfBirth", "dateOfJoin"});
		staffTableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		staffTableViewer.setContentProvider(listContentProvider);
		//
		IObservableList selfList = Properties.selfList(Staff.class).observe(staffList);
		staffTableViewer.setInput(selfList);
		//
		BeansListObservableFactory treeObservableFactory = new BeansListObservableFactory(CriteriaComposer.class, "criteriaList");
		TreeBeanAdvisor treeAdvisor = new TreeBeanAdvisor(CriteriaComposer.class, "readableCriteriaName", "criteriaList", null);
		ObservableListTreeContentProvider treeContentProvider = new ObservableListTreeContentProvider(treeObservableFactory, treeAdvisor);
		treeViewer.setLabelProvider(new TreeObservableLabelProvider(treeContentProvider.getKnownElements(), CriteriaComposer.class, "readableCriteriaName", null));
		treeViewer.setContentProvider(treeContentProvider);
		//
		IObservableList selfList_1 = Properties.selfList(CriteriaComposer.class).observe(criteriaList);
		treeViewer.setInput(selfList_1);
		//
		return bindingContext;
	}
}
