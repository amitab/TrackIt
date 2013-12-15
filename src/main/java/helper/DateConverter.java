package helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
	private static final String[] formats = { 
        "yyyy-MM-dd",
        "dd-MM-yyyy",
        "MM-dd-yyyy",
        "MMMM",
        "yyyy",
        "MM/dd/yyyy",
        "yyyy/MM/dd",
        "yyyy/dd/MM",
        "MM-yyyy",
        "yyyy-MM"
    };
	
	public static Date parse(String d, String format) throws Exception {
		Date date;
		if (d != null) {
		    for (String parse : formats) {
		        SimpleDateFormat sdf = new SimpleDateFormat(parse);
		        try {
		            date = sdf.parse(d);
		            format = parse;
		            return date;
		        } catch (Exception e) {
		        	
		        }
		    }
		}
		
		throw new Exception("Could not parse date");
	}
}
