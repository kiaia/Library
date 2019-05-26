import java.util.Calendar;

public class borrowList {
    String isbn;
    String name;
    Calendar borrowtime;
    Calendar returntime;
    int renewtimes=0;
    borrowList(String isbn,String name){
        borrowtime=Calendar.getInstance();
        this.name=name;
        returntime=Calendar.getInstance();
        returntime.add(Calendar.DATE,30);
        this.isbn=isbn;
    }
    borrowList(String isbn,String name,String year,String mouth,String day,String hour,String min,String sec,String year1,String mouth1,String day1,String hour1,String min1,String sec1){
        borrowtime=Calendar.getInstance();
            this.name=name;
            returntime=Calendar.getInstance();
        returntime.add(Calendar.DATE,30);

            this.isbn=isbn;
            borrowtime.set(Calendar.YEAR,Integer.parseInt(year.trim()));
        borrowtime.set(Calendar.MONTH,Integer.parseInt(mouth.trim()));
        borrowtime.set(Calendar.DATE,Integer.parseInt(day.trim()));
        borrowtime.set(Calendar.HOUR,Integer.parseInt(hour.trim()));
        borrowtime.set(Calendar.MINUTE,Integer.parseInt(min.trim()));
        borrowtime.set(Calendar.SECOND,Integer.parseInt(sec.trim()));
        returntime.set(Calendar.YEAR,Integer.parseInt(year1.trim()));
        returntime.set(Calendar.MONTH,Integer.parseInt(mouth1.trim()));
        returntime.set(Calendar.DATE,Integer.parseInt(day1.trim()));
        returntime.set(Calendar.HOUR,Integer.parseInt(hour1.trim()));
        returntime.set(Calendar.MINUTE,Integer.parseInt(min1.trim()));
        returntime.set(Calendar.SECOND,Integer.parseInt(sec1.trim()));

    }
     int renew(Calendar now){
         long t1 = returntime.getTimeInMillis();
         long t2 = now.getTimeInMillis();


         long days = (t2 - t1)/(24 * 60 * 60 * 1000);
         if (renewtimes!=0) return 3;
    if(days>=3||days<0)return 4;
    else {returntime.add(Calendar.DATE,14);renewtimes++; return 1;}

    }
}
