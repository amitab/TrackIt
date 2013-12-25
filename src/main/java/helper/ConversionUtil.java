package helper;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import querycomposer.QueryComposerException;

public class ConversionUtil {
	private static final String[] formats = { 
        "dd-MM-yyyy",
        "MM-yyyy",
        "MMMM yyyy",
        "MMMM",
        "yyyy",
    };
	
	private static String dateFormat = "";
	
	public static String toReadable(String unreadable) {
		final StringBuilder result = new StringBuilder();
		String[] words = unreadable.split("(?=\\p{Upper})");
		for(int i=0,l=words.length;i<l;++i) {
		  if(i>0) result.append(" ");      
		  result.append(Character.toUpperCase(words[i].charAt(0)))
		        .append(words[i].substring(1));

		}
		
		return result.toString();
	}
	
	public static String fromReadable(String readable, boolean root) {
		if(root) return readable;
		
		final StringBuilder result = new StringBuilder();
		
		String[] words = readable.split("\\s");
		
		for(int i=0,l=words.length;i<l;++i) {
			if(i == 0) {
				result.append(Character.toLowerCase(words[i].charAt(0)))
		        .append(words[i].substring(1));
			} else {
				result.append(words[i]);
			}
		}
		
		return result.toString();
	}
	
	public static Object parseDate(String d) throws Exception {
		Date date;
		if (d != null) {
		    for (String parse : formats) {
		        SimpleDateFormat sdf = new SimpleDateFormat(parse);
		        try {
		            date = sdf.parse(d);
		            Calendar cal = Calendar.getInstance();
		            cal.setTime(date);
		            
		            int month, year;
		            
		            switch(parse) {
		            	case "MMMM":
		            		dateFormat = "%m";
		            		month = cal.get(Calendar.MONTH) + 1;
		            		return month < 10? "0" + Integer.toString(month) : Integer.toString(month);
		            		
		            	case "yyyy":
		            		dateFormat = "%Y";
		            		year = cal.get(Calendar.YEAR);
		            		return Integer.toString(year);
		            		
		            	case "MMMM yyyy":
		            		dateFormat = "%m-%Y";
		            		
		            		month = cal.get(Calendar.MONTH) + 1;
		            		year = cal.get(Calendar.YEAR);
		            		return month < 10? "0" + Integer.toString(month) + "-" + Integer.toString(year) : Integer.toString(month) + "-" + Integer.toString(year);
		            	
		            	case "MM-yyyy":
		            		dateFormat = "%m-%Y";
		            		
		            		month = cal.get(Calendar.MONTH) + 1;
		            		year = cal.get(Calendar.YEAR);
		            		return month < 10? "0" + Integer.toString(month) + "-" + Integer.toString(year) : Integer.toString(month) + "-" + Integer.toString(year);
		            		
		            	default:
		            		dateFormat = null;
		            		return date;
		            }
		            
		        } catch (Exception e) {
		        	
		        }
		    }
		}
		
		throw new Exception("Could not parse date");
	}
	
	public static Object convertIfNeeded(Object value, Class<?> type) throws QueryComposerException {
		
		if (type.isPrimitive()) {
			if (boolean.class.equals(type)) {
				type = Boolean.class;
			} else if (char.class.equals(type)) {
				type = Character.class;
			} else if (byte.class.equals(type)) {
				type = Byte.class;
			} else if (short.class.equals(type)) {
				type = Short.class;
			} else if (int.class.equals(type)) {
				type = Integer.class;
			} else if (long.class.equals(type)) {
				type = Long.class;
			} else if (float.class.equals(type)) {
				type = Float.class;
			} else if (double.class.equals(type)) {
				type = Double.class;
			}
		}
		
		if (value == null)
			return null;
		if (type.isInstance(value))
			return value;
		
		

		if (String.class.equals(type)) {
			return value.toString();
		} else if (Number.class.isAssignableFrom(type)) {
			// the desired type is a number
			if (value instanceof Number) {
				// the value is also a number of some kind. do a conversion
				// to the correct number type.
				Number num = (Number) value;

				if (type.equals(Double.class)) {
					return new Double(num.doubleValue());
				} else if (type.equals(Float.class)) {
					return new Float(num.floatValue());
				} else if (type.equals(Long.class)) {
					return new Long(num.longValue());
				} else if (type.equals(Integer.class)) {
					return new Integer(num.intValue());
				} else if (type.equals(Short.class)) {
					return new Short(num.shortValue());
				} else {
					try {
						return type.getConstructor(String.class).newInstance(value.toString());
					} catch (IllegalArgumentException e) {
					} catch (SecurityException e) {
					} catch (InstantiationException e) {
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					} catch (NoSuchMethodException e) {
					}
				}
			} else if (value instanceof String) {
				//the value is a String. attempt to parse the string
				try {
					if (type.equals(Double.class)) {
						return Double.parseDouble((String) value);
					} else if (type.equals(Float.class)) {
						return Float.parseFloat((String) value);
					} else if (type.equals(Long.class)) {
						return Long.parseLong((String) value);
					} else if (type.equals(Integer.class)) {
						return Integer.parseInt((String) value);
					} else if (type.equals(Short.class)) {
						return Short.parseShort((String) value);
					} else if (type.equals(Byte.class)) {
						return Byte.parseByte((String) value);
					} 
				} catch (Exception ex) {
					//fall through to the error thrown below
				}
			}
		} else if (Class.class.equals(type)) {
			try {
				return Class.forName(value.toString());
			} catch (ClassNotFoundException e) {
				throw new QueryComposerException("Unable to convert value " + value.toString() + " to type Class");
			}
		} 
		
		else if(value instanceof String) {

			try {
				
				if (type.equals(Date.class)) {
					return parseDate((String) value);
				}
				
			} catch (Exception e) {
				// Fall through
			}
		}
		
		throw new QueryComposerException("Unable to convert value " + value.toString() + " of type " + value.getClass().getSimpleName() + " to type "
				+ type.getSimpleName());
	}
	
	
	public static String getRecentParseFormat() {
		return dateFormat;
	}
	
}
