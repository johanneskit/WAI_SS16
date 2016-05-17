package cal;

import java.util.Hashtable;
import javax.servlet.http.*;

public class Entries {

  private Hashtable entries;
  private static final String[] time = {"01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", 
					"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
					"17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "00:00" };
  public static final int rows = 24;

  public Entries () {   
   entries = new Hashtable (rows);
   for (int i=0; i < rows; i++) {
     entries.put (time[i], new Entry(time[i]));
   }
  }

  public int getRows () {
    return rows;
  }

  public Entry getEntry (int index) {
    return (Entry)this.entries.get(time[index]);
  }

  public int getIndex (String tm) {
    for (int i=0; i<rows; i++)
      if(tm.equals(time[i])) return i;
    return -1;
  }

  public void processRequest (HttpServletRequest request, String tm) {
    int index = getIndex (tm);
    if (index >= 0) {
      String descr = request.getParameter ("description");
      ((Entry)entries.get(time[index])).setDescription (descr);
    }
  }

}













