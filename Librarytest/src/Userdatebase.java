import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;

public class Userdatebase {
    TreeMap<String, User> userTreeMap;

    public Userdatebase() {
        userTreeMap = new TreeMap<String, User>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2); //升序排列
            }
        });
        {File file = new File("src/inputuser.txt");
        char[] data = new char[8192];

        try (Reader reader = new FileReader(file)) {

            reader.read(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String res=String.valueOf(data);
        String[] record = res.split("\r\n");

        for(int i=0;i<record.length;i++){
            String[] member=record[i].split(",");

            this.addall(member[0],member[1],member[2]);
            this.setnoborrow(member[0],member[3],member[4],member[5],member[6],member[7],member[8]);

        }
        System.out.println("userdata connect successfully");}
        /*superadd("super1","123456");
        superadd("super2","123456");
        superadd("super3","123456");*/
        {File file = new File("src/borrow.txt");
            char[] data = new char[8192];

            try (Reader reader = new FileReader(file)) {

                reader.read(data);

            } catch (Exception e) {
                e.printStackTrace();
            }
            String res=String.valueOf(data);
            String[] record = res.split("\r\n");

            for(int i=0;i<record.length;i++){
                if(record[i]!=null)
                {String[] member=record[i].split(",");
                if(member.length>1)
                {if(member[3].equals("normal"))
                    userTreeMap.get(member[2]).normalRent.put(member[0],new borrowList(member[0],member[1],member[4],member[5],member[6],
                        member[7],member[8],member[9],member[10],member[11],member[12],member[13],member[14],member[15]));
                else if(member[3].equals("returned"))
                    userTreeMap.get(member[2]).returnRent.put(member[0],new borrowList(member[0],member[1],member[4],member[5],member[6],
                            member[7],member[8],member[9],member[10],member[11],member[12],member[13],member[14],member[15]));
                else if(member[3].equals("unreturned"))
                    userTreeMap.get(member[2]).unreturnedRent.put(member[0],new borrowList(member[0],member[1],member[4],member[5],member[6],
                            member[7],member[8],member[9],member[10],member[11],member[12],member[13],member[14],member[15]));}}


            }
            System.out.println("userborrowlist connect successfully");}

    }
    public boolean  setnoborrow(String user,String year,String mouth,String day,String hour,String min,String sec){
        Calendar borrowtime=userTreeMap.get(user).noBorrow;
        borrowtime.set(Calendar.YEAR,Integer.parseInt(year.trim()));
        borrowtime.set(Calendar.MONTH,Integer.parseInt(mouth.trim()));
        borrowtime.set(Calendar.DATE,Integer.parseInt(day.trim()));
        borrowtime.set(Calendar.HOUR,Integer.parseInt(hour.trim()));
        borrowtime.set(Calendar.MINUTE,Integer.parseInt(min.trim()));
        borrowtime.set(Calendar.SECOND,Integer.parseInt(sec.trim()));
        return true;
    }
    public int addall(String user,String password,String level){


        if(level.trim().equals("user"))return addUser(user,password);
        else if(level.trim().equals("organizer"))return addorgan(user,password);
        else if(level.trim().equals("superuser")){
           return superadd(user,password);}
        return 5;
    }

    public int addUser(String user,String password) {
        int res=checkuseradd(user,password);
        if(res!=1)return res;
        else

        userTreeMap.put(user, new User(user,password,Userform.user));

        return 1;

    }
    public int addorgan(String user,String password) {
       // if(checkuseradd(user,password))return false;

        userTreeMap.put(user, new User(user,password,Userform.organizer));
        System.out.println("addorgansucc");
        return 3;

    }
    private int superadd(String user,String password){
       // if(checkuseradd(user,password))return false;

        userTreeMap.put(user, new User(user,password,Userform.superuser));
        //System.out.println("superpower");
        return 4;

    }
    public int  checkuseradd(String user,String password)
    {if (userTreeMap.containsKey(user))
    {System.out.println("The account already exists. Please change the registered account.");
        return 0;}

        if (password.length()>30||password.length()<5) {

            return 2;
        }
    return 1;}
    public String checkcount(String user,String password){
        if(!userTreeMap.containsKey(user))return "useridwrong";
        if(!userTreeMap.get(user).getPassword().equals(password))return "passwordswrong";
        return userTreeMap.get(user).level.toString();

    }
    public List<User> searchuser(String keyword) {
        List<User> matchbook = new ArrayList<>();
        for (Map.Entry<String, User> entry : userTreeMap.entrySet()) {
            if (entry.getValue().getName().indexOf(keyword) > -1&&(entry.getValue().level.equals(Userform.user))) matchbook.add(entry.getValue());
        }
        return matchbook;

    }
    public List<User> searchorgan(String keyword) {
        List<User> matchbook = new ArrayList<>();
        for (Map.Entry<String, User> entry : userTreeMap.entrySet()) {
            if (entry.getValue().getName().indexOf(keyword) > -1&&(entry.getValue().level.equals(Userform.organizer))) matchbook.add(entry.getValue());
        }
        return matchbook;

    }

}
