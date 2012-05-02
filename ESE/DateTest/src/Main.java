import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		
		Calendar rightNow = Calendar.getInstance();
    	int month = rightNow.get(Calendar.MONTH);//Calendar.MONTH;
    	
    	//month = 7;
    	SimpleDateFormat dfs = new SimpleDateFormat();
    	String thisMonth = dfs.getDateFormatSymbols().getMonths()[month];

    	int today = Calendar.DAY_OF_MONTH+1;
        List<Integer> daysFromLastMonth = new ArrayList<Integer>();
        List<Integer> daysFromThisMonth = new ArrayList<Integer>();
        List<Integer> daysFromNextMonth = new ArrayList<Integer>();
        
        daysFromLastMonth =	getDaysFromLastMonth(month);
        daysFromThisMonth = getDaysFromThisMonth(month);
        daysFromNextMonth = getDaysFromNextMonth(month);
        
        int startOfLastMonth = daysFromLastMonth.get(0);
        
        System.out.println("Today: "+today);
        System.out.println("Month +1  (int): "+(month+1));
        System.out.println("Month (name): "+thisMonth);
        System.out.println("Start of last month: "+startOfLastMonth);
        
        System.out.print("days from last month:");
        for (int i=0; i < daysFromLastMonth.size(); i++) {
        	System.out.print(daysFromLastMonth.get(i)+" ");
        }
        System.out.print("\n");
        
        System.out.print("days from this month:");
        for (int i=0; i < daysFromThisMonth.size(); i++) {
        	System.out.print(daysFromThisMonth.get(i)+" ");
        }
        System.out.print("\n");
        

        System.out.print("days from next month:");
        for (int i=0; i < daysFromNextMonth.size(); i++) {
        	System.out.print(daysFromNextMonth.get(i)+" ");
        }
        System.out.print("\n");
	}
	
	public static List<Integer> getDaysFromThisMonth(int month) {
		Calendar cal = new GregorianCalendar();
		//cal.set(Calendar.MONTH) = month;
		//Calendar.MONTH
		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		List<Integer> l = new ArrayList<Integer>();
		for (int i=0; i < max; i++) {
			l.add(i+1);
		}
		
		return l;
	}
	
	public static List<Integer> getDaysFromLastMonth(int month) {
		List<Integer> l = new ArrayList<Integer>();
		Calendar thisMonth = new GregorianCalendar();
		Calendar lastMonth = new GregorianCalendar();
		thisMonth.set(Calendar.MONTH, month);
		lastMonth.set(Calendar.MONTH, month-1);
		
		int max = lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		thisMonth.setFirstDayOfWeek(Calendar.MONDAY);
		int start = thisMonth.get(Calendar.DAY_OF_WEEK);
		
		System.out.println(">>Start: "+start);
		
		for (int i=max-start; i < max; i++) {
			l.add(i+1);
		}
		return l;
	}
	
	public static List<Integer> getDaysFromNextMonth(int month) {
		List<Integer> l = new ArrayList<Integer>();
		Calendar thisMonth = new GregorianCalendar();
		Calendar nextMonth = new GregorianCalendar();
		thisMonth.set(Calendar.MONTH, month);
		nextMonth.set(Calendar.MONTH, month+1);
		
		int start = nextMonth.get(Calendar.DAY_OF_WEEK);
		int max = 7-start;
		
		System.out.println(">>Start: "+start);
		
		for (int i=0; i < max; i++) {
			l.add(i+1);
		}
		
		return l;
	}
}
