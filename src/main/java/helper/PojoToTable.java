package helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PojoToTable {
	java.lang.Class<?> model;
	List<Field> columnFields = new ArrayList<Field>();
	List<Object[]> rows = new ArrayList<Object[]>();
	int columnCount;
	
	private static boolean isWrapperType(Class<?> clazz) {
        return getWrapperTypes().contains(clazz);
    }

    private static Set<Class<?>> getWrapperTypes() {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        ret.add(String.class);
        ret.add(Date.class);
        return ret;
    }
	
    public static String[] getColumnsOfClass(java.lang.Class<?> model) {
    	PojoToTable pj = new PojoToTable(model);
		return pj.getColumnNames();
    }
    
	public PojoToTable(java.lang.Class<?> model) {
		this.model = model;
		findColumnNames();
		this.columnCount = columnFields.size();
	}
	
	private void findColumnNames() {
		Field []fields = model.getDeclaredFields();
		
		for(Field field : fields) {
			if(field.getAnnotation(annotation.Expose.class) == null && isWrapperType(field.getType())) {
				columnFields.add(field);
			}
		}
	}
	
	public String[] getColumnNames() {
		String []names = new String[columnCount];
		
		for(int i = 0; i < columnCount; i++) {
			names[i] = ConversionUtil.toReadable(columnFields.get(i).getName());
		}
		
		return names;
	}
	
	public int getColumnCount() {
		return columnCount;
	}
	
}
