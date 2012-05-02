package jobs;
import models.Database;
import play.jobs.*;

@OnApplicationStart
public class Bootstrap extends Job{

	public void doJob(){
		
		this.addUsers();
		this.addCalendars();
		this.addEvents();
		
	}

	private void addUsers() {
		Database.addUser("Bob","asdf");
		Database.addUser("Alfonso","qwertz");
		Database.addUser("Alina","alina");
	}
	
	private void addCalendars() {
		Database.addCalendar("Bobs Geburtstagskalender","Bob");
		Database.addCalendar("Bobs Feiertagskalender","Bob");
		Database.addCalendar("Bobs Ferienkalender","Bob");
		Database.addCalendar("Calendario di Alfonso","Alfonso");
		Database.addCalendar("Alinas Arbeitskalender","Alina");
		Database.addCalendar("Alinas Freizeitkalender","Alina");
	}
	
	private void addEvents() {
		Database.getUserByName("Bob").addEventToCalendar("Bobs Geburtstagskalender","Geburtstag Marta","08.10.2011 00:00", "08.10.2011 23:59", false);
		Database.getUserByName("Bob").addEventToCalendar("Bobs Geburtstagskalender","Geburtstag Giovanni","09.10.2011 00:00", "09.10.2011 23:59",false);
		Database.getUserByName("Bob").addEventToCalendar("Bobs Geburtstagskalender","Geburtstag Toni","11.10.2011 00:00", "11.10.2011 23:59",false);
		Database.getUserByName("Bob").addEventToCalendar("Bobs Feiertagskalender","Weihnachten","24.12.2011 00:00", "24.12.2011 23:59",false);
		Database.getUserByName("Bob").addEventToCalendar("Bobs Feiertagskalender","Nationalfeiertag 2011","01.08.2011 00:00", "01.08.2011 23:59",false);
		Database.getUserByName("Bob").addEventToCalendar("Bobs Feiertagskalender","Nationalfeiertag 2012","01.08.2012 00:00", "01.08.2012 23:59",false);
		Database.getUserByName("Bob").addEventToCalendar("Bobs Ferienkalender","Weihnachtsferien","23.12.2011 00:00", "05.01.2012 23:59",false);
		
		Database.getUserByName("Alfonso").addEventToCalendar("Calendario di Alfonso","Compleanno di Mamma","13.10.2011 00:00", "13.10.2011 23:59",true);
		Database.getUserByName("Alfonso").addEventToCalendar("Calendario di Alfonso","Compleanno di Nonna","25.11.2011 00:00", "25.11.2011 23:59",true);
		Database.getUserByName("Alfonso").addEventToCalendar("Calendario di Alfonso","Dinero a Giovanni","07.10.2011 13:00", "07.10.2011 12:00",true);
		
		Database.getUserByName("Alina").addEventToCalendar("Alinas Arbeitskalender","Boring tasks","03.10.2011 12:00", "03.10.2011 15:30",false);
		Database.getUserByName("Alina").addEventToCalendar("Alinas Arbeitskalender","More boring tasks","03.10.2011 16:00", "03.10.2011 18:00",false);
		Database.getUserByName("Alina").addEventToCalendar("Alinas Arbeitskalender","Meeting with boss","03.10.2011 20:00", "03.10.2011 20:45",true);
		Database.getUserByName("Alina").addEventToCalendar("Alinas Freizeitkalender","Soccer","09.10.2011 12:30", "09.10.2011 17:00",true);
		Database.getUserByName("Alina").addEventToCalendar("Alinas Freizeitkalender","Boxing","08.10.2011 15:00", "08.10.2011 16:30",true);
		Database.getUserByName("Alina").addEventToCalendar("Alinas Freizeitkalender","Enjoying Weekend","09.10.2011 00:00", "10.10.2011 23:59",true);
		
		
	}

}
