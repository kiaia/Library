import javax.swing.text.html.ListView;
import java.util.*;

public class User {
    public Userform level;
    public String name;
    private String password;
    Calendar noBorrow=Calendar.getInstance();
    public Map<String,borrowList> normalRent = new TreeMap<String,borrowList >();
    public Map<String,borrowList> returnRent = new TreeMap<String,borrowList >();
    Map<String,borrowList> unreturnedRent = new TreeMap<String,borrowList >();



    public int borrowBook(String isbn,Library lib,Calendar now){
        if(!(boolean)checkRent(now)) return 0;
        if(!checkNoborrow(now)) return 2;
        if(normalRent.size()>=4) {
            return 3;
        }
        int rest=lib.getBookByIsbn(isbn).getRest();
        if(rest<=0)return 4;
        lib.getBookByIsbn(isbn).setRest(rest-1);
        normalRent.put(isbn,new borrowList(isbn,lib.getBookByIsbn(isbn).getTitle()));
        return 1;
    }
    public int renew(String isbn,Calendar now){
        if(!(boolean)checkRent(now)) return 0;
        if(!checkNoborrow(now)) return 2;
        return normalRent.get(isbn).renew(now);

    }
    public int returnnormalbook(String isbn ,Library lib , Calendar now ){
        checkRent(now);
        if(normalRent.get(isbn)==null)return 0;
        int rest=lib.getBookByIsbn(isbn).getRest();
        lib.getBookByIsbn(isbn).setRest(rest+1);
        borrowList temp=normalRent.get(isbn);
        normalRent.remove(isbn);
        returnRent.put(isbn,temp);
        return 1;
    }
    public int returnoutbook(String isbn ,Library lib , Calendar now ){
        checkRent(now);
        int rest=lib.getBookByIsbn(isbn).getRest();
        lib.getBookByIsbn(isbn).setRest(rest+1);
        borrowList temp=unreturnedRent.get(isbn);
        unreturnedRent.remove(isbn);
        returnRent.put(isbn,temp);
        if(checkRent(now)){
            noBorrow=Calendar.getInstance();
            noBorrow.add(Calendar.DATE,30);
        }
        return 1;
    }
    public int returnbook(String isbn,Library lib,Calendar now){
        checkRent(now);
        if(unreturnedRent.containsKey(isbn))return returnoutbook(isbn,lib,now);
        else return returnnormalbook(isbn,lib,now);
    }

    public boolean checkRent(Calendar now){
        for(String key : normalRent.keySet()){
            borrowList temp = normalRent.get(key);
            if(temp.returntime.before(now))
        {
            String tempname=temp.isbn;
        normalRent.remove(tempname);
        unreturnedRent.put(tempname,temp);
        return false;
        }
            if(unreturnedRent.size()>0)return false;
    }
    return true;}

    public boolean checkNoborrow(Calendar now){
        if(noBorrow.before(now))return true;
        else return false;
    }






    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public User(String name,String password,Userform a)
    {
        this.name=name;
        this.password=password;
        this.level=a;
    }

}
