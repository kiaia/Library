/**

 　　* Copyright KaiKiaia.

 　　* All right reserved.

 　　*/
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.regex.*;
import java.math.BigDecimal;
import java.lang.String;
import java.util.Collection;
import java.util.*;
import java.util.Iterator;
import static java.lang.Integer.min;

public class Librarytest {
    public static void main(String[] args) {

       Console test1 = new Console();
       boolean runningstate=test1.run();
        while(runningstate){runningstate=test1.run();}

        //santi.print();
        /* 推荐测试命令
        超级用户账号：super1  密码：123456
        超级用户账号：super2  密码：123456
        超级用户账号：super3  密码：123456
        注册普通用户 输入账号密码后 按y确认
        注册管理员  登录超级用户账号后 使用addorgan命令 输入账号密码后 按y确认
        使用help命令查看帮助
        添加的书籍数据 9787309132519, fuzizaixiang,wo,12.7,30,20
        修改的书籍数据 9787121316388,9787111301974,two body,liucixi,12.7,30,20
        search words -d al
        search words -w al
        search words -d
        search isbn 9787121316388
        search isbn 23
        exit
        help
        ctrl+z
        采用shell式命令输入，目前-d/-w还不能省略
         */
        /*
        8981
        123
        exit
        super1
        123456
        searchuser
         */
    }
}

class Console {
    Library buaa = new Library();
    Userdatebase testUser= new Userdatebase();
    String input = "";
    String cons="";
    String modification = "";
    String modification2 = "";
    String code = "";
    int page=1;
    Scanner scan = new Scanner(System.in);
    Calendar now= Calendar.getInstance();


    Console() {
        File file = new File("src/inputbook.txt");
        char[] data = new char[8192];

        try (Reader reader = new FileReader(file)) {
            
            reader.read(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String res=String.valueOf(data);
        String[] record = res.split("\n");

        for(int i=0;i<record.length;i++){
            String[] member=record[i].split(",");
                buaa.addBook(new Book(member[1],member[0],member[2],member[3],
                        Integer.parseInt(member[4]),
                        Integer.parseInt(member[5].toString().trim())));
        }
        /*Book sant1 = new Book("9787121316388", "three body", "liucixi", "12.7", 30, 20);
        Book sant2 = new Book("9787040406641", "aldatabase", "liucixi", "12.7", 30, 20);
        Book sant3 = new Book("9787302424505", "algorithm", "wo", "12.7", 30, 20);
        Book sant4 = new Book("9787507603507", "alaa", "wo", "12.7", 30, 20);
        Book sant5 = new Book("9787539654706", "testbook", "wo", "12.7", 30, 20);
        Book sant6 = new Book("9787530218723", "yuannv", "wo", "12.7", 30, 20);
        Book sant7 = new Book("9787530218648", "banshengyuan", "wo", "12.7", 30, 20);
        Book sant8 = new Book("9787531741220", "chunmingwaishi", "zhanghenshui", "12.7", 30, 20);
        Book sant9 = new Book("9787559625243", "xiaofengsanwen", "wo", "12.7", 30, 20);
        Book sant10 = new Book("9787559420589", "jvzheng", "wo", "12.7", 30, 20);
        Book sant11= new Book("9787020145881", "huangsha", "wo", "12.7", 30, 20);
        Book sant12 = new Book("9787500320616", "kanbudong", "wo", "12.7", 30, 20);
        //Book sant14= new Book("9787309132519", "fuzizaixiang", "wo", "12.7", 30, 20);
        //9787309132519, fuzizaixiang,wo,12.7,30,20
        //9787121316388，9787111301974,,three body,liucixi,12.7,30,20

        buaa.addBook(sant1);
        buaa.addBook(sant3);
        buaa.addBook(sant2);
        buaa.addBook(sant4);
        buaa.addBook(sant5);
        buaa.addBook(sant6);
        buaa.addBook(sant7);
        buaa.addBook(sant8);
        buaa.addBook(sant9);
        buaa.addBook(sant10);
        buaa.addBook(sant11);
        buaa.addBook(sant12);
        //buaa.addBook(sant14);*/





    }

    boolean run() {

        //Library testLib = new Library();
        String logininres=login();
        while((logininres.equals("quit"))||(logininres.equals("useridwrong"))||(logininres.equals("passwordswrong")))
        {
         if(logininres.equals("quit")){savebook();saveuser();saveborrow();return false;}
             logininres=login();
        }
        String loginlevel=testUser.userTreeMap.get(logininres).level.toString();
        System.out.println("setup in "+loginlevel+"mode");


        if(loginlevel.equals("user"))
        {mainMenu(); return userprogram(logininres);}
        if(loginlevel.equals("organizer"))
        {organMenu();return organprogram(logininres);}
        if(loginlevel.equals("superuser"))
        { superMenu(); return superprogram(logininres);}

        return false;



    }
    boolean userprogram(String username){
        input = "";
        cons="";
        modification = "";
        modification2 = "";
        code = "";
        while (!input.equals("exit")) {

            input = inputF();
            if (input.equals("error")) {
                System.out.println("wrong isbn");
                continue;
            }
            if (input.equals("exit")) {
                return true;
            }
            if (input.equals("quit")) {
                savebook();
                saveuser();
                saveborrow();
                return false;
            }
            switch (cons) {
                case "search":
                    this.search();
                    break;
                case "help":
                    this.mainMenu();
                    break;
                case "exit":
                    break;
                case "borrow":
                    this.borrowbook(username);
                    break;
                case "renew":
                    this.renewbook(username);
                    break;
                case "returnbook":
                    this.returnBook(username);
                    break;
                case "showmybook":
                    this.showmybook(username,buaa);
                    break;
                case "addtime":
                    addtime();
                    break;
                case "showtime":
                    showtime(now);
                    break;
                default:
                    System.out.println("input error ,input help to get help");

            }


        }
        return true;
    }
    boolean showtime(Calendar now){

        Date tasktime=now.getTime();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(tasktime));
        return true;
    }

    boolean organprogram(String username){
        input = "";
        cons="";
        modification = "";
        modification2 = "";
        code = "";
        while (!input.equals("exit")) {

            input = inputF();
            if (input.equals("error")) {
                System.out.println("wrong isbn");
                continue;
            }
            if (input.equals("exit")) {
                return true;
            }
            if (input.equals("quit")) {
                savebook();
                saveuser();
                saveborrow();
                return false;
            }
            switch (cons) {
                case "search":
                    this.search();
                    break;
                case "help":
                    this.mainMenu();
                    this.organMenu();
                    break;
                case "exit":
                    break;
                case "searchuser":
                    searchuser();
                    break;
                case "addbook":
                    addBook();
                    break;
                case "alterbook":
                    alterbook();
                    break;
                case "deletebook":
                    deletbook();
                    break;
                case"borrowlist":
                    borrowlist();
                    break;
                case "reducenoborrow":
                    reducenoborrow();
                    break;
                default:
                    System.out.println("input error ,input help to get help");

            }
            input = "";
            cons="";
            modification = "";
            modification2 = "";
            code = "";

        }
        return true;
    }
    boolean superprogram(String username){
        input = "";
        cons="";
        modification = "";
        modification2 = "";
        code = "";
        while (!input.equals("exit")) {

            input = inputF();
            if (input.equals("error")) {
                System.out.println("wrong isbn");
                continue;
            }
            if (input.equals("exit")) {
                return true;
            }
            if (input.equals("quit")) {
                savebook();
                saveuser();
                saveborrow();
                return false;
            }
            switch (cons) {
                case "search":
                    this.search();
                    break;
                case "help":
                    this.mainMenu();
                    this.organMenu();
                    this.superMenu();
                    break;
                case "exit":
                    break;
                case "addbook":
                    addBook();
                        break;
                case "alterbook":
                    alterbook();
                    break;
                case "deletebook":
                    deletbook();
                    break;
                case "searchuser":
                    searchuser();
                    break;
                case "searchorgan":
                    searchorgan();
                    break;
                case "addorgan":
                    addorgan();
                    break;
                case "addtime":
                    addtime();
                    break;
                default:
                    System.out.println("input error ,input help to get help");

            }
            input = "";
            cons="";
            modification = "";
            modification2 = "";
            code = "";

        }
        return true;
    }
     private String login(){
        String userid;
        String password;

         System.out.println("please input your userid and password,if you want to register a new count");
         System.out.println("input the userid and password and comfirm to make a new count");
         System.out.println("Please set a password with a length greater than 5 and less than 30.");
         System.out.println("userid:");
         userid=inputP();if(userid.equals("quit"))return "quit";
         System.out.println("password:");
         password=inputP();if(password.equals("quit"))return "quit";
         if(testUser.checkcount(userid,password).equals("useridwrong")){
             System.out.println(" the  userid you input didn'exit, input \'y\' to make a new count");
             String inputs=scan.nextLine();
             if(inputs.equals("y"))
             {
                 int res=testUser.addUser(userid,password);
                 if(res==1){
                 System.out.println("addusersucc");
                 return testUser.userTreeMap.get(userid).getName();}
             else if(res==2){
                     System.out.println("\n" +
                             "Please set a password with a length greater than 5 and less than 30.");
                     return "useridwrong";}

         } else  return "useridwrong";}
         if(testUser.checkcount(userid,password).equals("passwordswrong")){
             System.out.println(" the password is wrong,please try again");
              return "passwordswrong";
         }
         return  testUser.userTreeMap.get(userid).getName().toString();

     }



    String inputP(){
        String res="";
        //String password="";
        try{res = scan.nextLine();}catch(NoSuchElementException e){  savebook();saveuser();saveborrow();System.out.println("强制退出");return "exit";}
        return res;
    }


    String inputF() {
        String inputstr = " ";
        String[] temp = new String[4];
        input="";
        cons = "";
        modification = "";
        modification2 = "";
        code = "";

        //  while(scan.hasNextLine()){
        try{inputstr = scan.nextLine();}catch(NoSuchElementException e){  savebook();saveuser();saveborrow();System.out.println("强制退出");return "exit";} //输入ctrl+z抛出异常
        //catch(EOFException b){ System.out.println("强制退出");}
        //  }

        {int i = 0;

            for (String retval : inputstr.split(" +", 4)) {
                //System.out.println(retval);
                temp[i] = retval;
                i++;
            }

            if(temp[0]!=null)cons = temp[0];
            if(temp[1]!=null)modification = temp[1];
            if(temp[2]!=null)modification2 = temp[2];
            if(temp[3]!=null)code = temp[3];}
        if(modification.equals("isbn")&&(!ISBN.pubcheckisbn(modification2)))return "error";

        //scan.close();
        return inputstr;
    }


    void search() {
        if (modification.equals("isbn"))
            System.out.println(buaa.getBookByIsbn(this.modification2).getTitle());
        if (modification.equals("words")) {
            page = 1;
            if (modification2.equals( "-d")) {
                List<Book> matchbook = buaa.getBooksByKeyword(code);
                searchhelp(matchbook);

            }
            if (modification2 .equals( "-w")||(modification2.equals("")&&code.equals(""))) {
                List<Book> matchbook = buaa.getBooksByKeyword(code);
                Collections.sort(matchbook, new Comparator<Book>() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        return o1.getTitle().compareTo(o2.getTitle()); //升序排列
                    }
                });
                searchhelp(matchbook);

            }
        }

    }
    void searchhelp(List<Book> matchbook){
        System.out.format("%-15s %-15s %-15s %-15s %-7s %7s\n",new String("title"),new String("isbn"),new String("author"),new String("price"),new String("rest"),new String("total"));

        if (matchbook.size() < 10)
            for (int i = 0; i < matchbook.size(); i++) {
                System.out.format("%-15s %-15s %-15s %-15s %-7d %7d\n",matchbook.get(i).getTitle(),matchbook.get(i).code.isbn,matchbook.get(i).getAuthor(),matchbook.get(i).getPrice(),matchbook.get(i).getRest(),matchbook.get(i).getTotal());
            }
        if (matchbook.size() >= 10) {
            for (int i = 0; i < 10; i++) {
                System.out.format("%-15s %-15s %-15s %-15s %-7d %7d\n",matchbook.get(i).getTitle(),matchbook.get(i).code.isbn,matchbook.get(i).getAuthor(),matchbook.get(i).getPrice(),matchbook.get(i).getRest(),matchbook.get(i).getTotal());
            }
            System.out.println("now you are in page"+page);
            Pagehelp();
            while (showoptions( matchbook.size(), matchbook)) {
            }
        }

    }


    void showlist( List<Book> matchbook,int matchlist) {
        System.out.format("%-15s %-15s %-15s %-15s %-7s %7s\n",new String("title"),new String("isbn"),new String("author"),new String("price"),new String("rest"),new String("total"));
        for (int i = 0; i < (min(10,matchlist-(page - 1) * 10)); i++) {
            System.out.format("%-15s %-15s %-15s %-15s %-7d %7d\n",matchbook.get(i).getTitle(),matchbook.get(i).code.isbn,matchbook.get(i).getAuthor(),matchbook.get(i).getPrice(),matchbook.get(i).getRest(),matchbook.get(i).getTotal());
        }
        System.out.println("now you are in page"+page);

    }
    void showuserlist( List<User> matchbook,int matchlist) {
        for (int i = 0; i < (min(10,matchlist-(page - 1) * 10)); i++) {

            System.out.print(matchbook.get(i + (page - 1) * 10).getName()+" ");
            System.out.println(matchbook.get(i + (page - 1) * 10).getPassword());
        }
        System.out.println("now you are in page"+page);

    }
    boolean showoptions(int matchlist, List<Book> matchbook) {
        Scanner scan = new Scanner(System.in);
        int ins = scan.nextInt();
        if (ins == 0 && checkRange(page, matchlist, ins)) {page++;showlist( matchbook,matchlist);}
        else if (ins == -1 && checkRange(page, matchlist, ins)) {page--;showlist( matchbook,matchlist);}
        else if ((ins<-2||ins>0) &&checkRange(page, matchlist, ins)) {page=ins;showlist( matchbook,matchlist);}
        else if (ins == -2) return false;
        Pagehelp();
        return true;

    }
    void Pagehelp(){
        System.out.println("input \"0\" to see the next page");
        System.out.println("input \"-1\" to see the last page");
        System.out.println("input \"[number]\" to see the designated page");
        System.out.println("if you want to borrow a book ,use borrow (isbn)");
        System.out.println("input \"-2\" to exit");
    }
    boolean showuseroptions(int matchlist, List<User> matchbook) {
        Scanner scan = new Scanner(System.in);
        int ins = scan.nextInt();
        if (ins == 0 && checkRange(page, matchlist, ins)) {page++;showuserlist( matchbook,matchlist);}
        else if (ins == -1 && checkRange(page, matchlist, ins)) {page--;showuserlist( matchbook,matchlist);}
        else if ((ins<-2||ins>0) &&checkRange(page, matchlist, ins)) {page=ins;showuserlist( matchbook,matchlist);}
        else if (ins == -2) return false;
        Pagehelp();
        return true;

    }

    boolean checkRange(int page, int matchlist, int ins) {
        if (page == 1 && ins == -1) {
            System.out.println("This is  the first page!");
            return false;
        }
        if (page == (matchlist /10+1) && ins == 0) {
            System.out.println("This is  the last page!");
            return false;
        }
        if (ins < -2|| ins > (matchlist / 10 + 1)) {
            System.out.println("The designated page is out of range");
            return false;
        }
        return true;


    }
    String addorgan(){
        String organid="";
        String password="";
        System.out.println("please input  organid and password you want to register ");
        System.out.println("input the userid and password and comfirm to make a new count");
//
        System.out.println("userid:");
        organid=inputP();if(organid.equals("exit"))return "exit";
        System.out.println("password:");
        password=inputP();if(password.equals("exit"))return "exit";
        if(testUser.checkcount(organid,password).equals("useridwrong")){
            System.out.println(" the  userid you input didn'exit, input \'y\' to make a new count");
            String inputs=scan.nextLine();
            if(inputs.equals("y")){testUser.addorgan(organid,password);return "success";}}
            if(testUser.checkcount(organid,password).equals("passwordwrong"))
            {System.out.println(" the  id you input has existed,please change a new id");}

        return "failed";

    }
    boolean searchorgan(){
        List<User> matchbook = testUser.searchorgan(modification);
        Collections.sort(matchbook, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().compareTo(o2.getName()); //升序排列
            }
        });
        searchuserhelp(matchbook);
        return true;

    }
    boolean searchuser(){
        List<User> matchbook = testUser.searchuser(modification);
        Collections.sort(matchbook, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().compareTo(o2.getName()); //升序排列
            }
        });
        searchuserhelp(matchbook);
        return true;
    }
    void searchuserhelp(List<User> matchbook){
        if (matchbook.size() < 10)
            for (int i = 0; i < matchbook.size(); i++) {
                System.out.print(matchbook.get(i).getName()+" ");
                System.out.println(matchbook.get(i).getPassword());
            }
        if (matchbook.size() >= 10) {
            for (int i = 0; i < 10; i++) {
                System.out.print(matchbook.get(i).getName()+" ");
                System.out.println(matchbook.get(i).getPassword());
            }
            System.out.println("now you are in page"+page);
            Pagehelp();
            while (showuseroptions( matchbook.size(), matchbook)) {
            }
        }

    }


    void mainMenu() {
        System.out.println("input following code to get information");
        System.out.println("\"search isbn (isbncode)\" to get the book    ");
        System.out.println("\"search words -d/-w (keywords) \" to get the book ,\"-d\"means sort by ISBN ascending order，\"-w\"means sort by heading dictionary order ");
        System.out.println("search words -d/-w without keywords will show all the book in the lib");
        System.out.println("\"help\" to get help ");
        System.out.println("\"exit\" to exit this count ");
        System.out.println("\"quit\" to quit this program ");
        System.out.println("\"borrow (isbn) (-t)\" to borrow a book ");
        System.out.println("\"renew (isbn) (-t)\" to renew a book ");
        System.out.println("\"returnbook (isbn) (-t)\" to return a book ");
        System.out.println("The above three commands can only use the time modified by addtime when - t is used, otherwise the current system time will be used automatically.");
        System.out.println("\"showmybook\" to show your related books ");
        System.out.println("\"showtime\" to get current time ");
        System.out.println("\"addtime\" to Delay the current time by ten days ");






    }
    String addBook(){
        System.out.println("\"input code title author price,totalnumber,restnumber\" ");
        System.out.println("example:\"addbook,9787121316388,three body,liucixi,12.7,30,20");

            String inputstr = " ";
            String[] temp = new String[9];
            String code="";
            String title = "";
            String author = "";
            String price = "";
            int totalnumber=0;
            int resnumber=0;

            //  while(scan.hasNextLine()){
            try{inputstr = scan.nextLine();}catch(NoSuchElementException e){ savebook();saveuser();saveborrow(); System.out.println("强制退出");return "exit";} //输入ctrl+z抛出异常
            //catch(EOFException b){ System.out.println("强制退出");}
            //  }

            {int j = 0;

                for (String retval : inputstr.split(",", 20)) {
                    //System.out.println(retval);
                    temp[j] = retval;
                    j++;
                }}

                if(temp[0]!=null)code=temp[0];
        if(temp[1]!=null)title = temp[1];
        if(temp[2]!=null) author = temp[2];
        if(temp[3]!=null) price = temp[3];
        if(temp[4]!=null)totalnumber=Integer.parseInt(temp[4]);
        if(temp[5]!=null)resnumber=Integer.parseInt(temp[5]);
                Book newbook=new Book(code,title,author,price,totalnumber,resnumber);
            if(!buaa.addBook(newbook))return "addfalse";

            //scan.close();
            System.out.println("successfully addbook");
            return "addtrue";


    }
    String alterbook(){
        System.out.println("\"input old code  new code title author price,totalnumber,restnumber\" ");
        System.out.println("example:\"9787121316388，9787111301974,,three body,liucixi,12.7,30,20");

        String inputstr = " ";
        String[] temp = new String[9];
        String code="";
        String code1="";
        String title = "";
        String author = "";
        String price = "";
        int totalnumber=0;
        int resnumber=0;

        //  while(scan.hasNextLine()){
        try{inputstr = scan.nextLine();}catch(NoSuchElementException e){  savebook();saveuser();saveborrow();System.out.println("强制退出");return "exit";} //输入ctrl+z抛出异常
        //catch(EOFException b){ System.out.println("强制退出");}
        //  }

        {int j = 0;

            for (String retval : inputstr.split(",", 20)) {
                //System.out.println(retval);
                temp[j] = retval;
                j++;
            }}

        if(temp[0]!=null)code=temp[0];
        if(temp[1]!=null)code1=temp[1];
        if(temp[2]!=null)title = temp[2];
        if(temp[3]!=null)author = temp[3];
        if(temp[4]!=null)price = temp[4];
        if(temp[5]!=null)totalnumber=Integer.parseInt(temp[5]);
        if(temp[6]!=null)resnumber=Integer.parseInt(temp[6]);
        //Book newbook=new Book(code,title,author,price,totalnumber,resnumber);
        if(!buaa.changeBook(code,code1,title,author,price,totalnumber,resnumber))return "addfalse";

        //scan.close();
        System.out.println("successfully alterbook");
        return "altertrue";


    }
    boolean deletbook(){
        if(!buaa.deleteBook(modification))return false;
        System.out.println("successfully deletebook");
        return true;

    }



    void organMenu(){

        System.out.println("input following code to get information");
        System.out.println("\"search isbn (isbncode)\" to get the book    ");
        System.out.println("\"search words -d/-w (keywords) \" to get the book ,\"-d\"means sort by ISBN ascending order，\"-w\"means sort by heading dictionary order ");
        System.out.println("search words -d/-w without keywords will show all the book in the lib");
        System.out.println("\"help\" to get help ");
        System.out.println("\"exit\" to exit this count ");
        System.out.println("\"quit\" to quit this program ");
        System.out.println("\"addbook\" ");
        System.out.println("\"deletbook isbn\"");
        System.out.println("\"searchuser userid\"");
        System.out.println("\"reducenoborrow (userid)\"to reduce the noborrow-time for a user");
        System.out.println("\"borrowlist (userid)\"to get a user's related books");

    }
    void superMenu(){

        System.out.println("input following code to get information");
        System.out.println("\"search isbn (isbncode)\" to get the book    ");
        System.out.println("\"search words -d/-w (keywords) \" to get the book ,\"-d\"means sort by ISBN ascending order，\"-w\"means sort by heading dictionary order ");
        System.out.println("search words -d/-w without keywords will show all the book in the lib");
        System.out.println("\"help\" to get help ");
        System.out.println("\"exit\" to exit this count ");
        System.out.println("\"quit\" to quit this program ");
        System.out.println("\"addbook\" ");
        System.out.println("\"deletbook isbn\"");
        System.out.println("\"searchuser userid\"");
        System.out.println("\"searchorgan organid\" ");
        System.out.println("\"addorgan\"");


    }
    boolean addtime(){
        now.add(Calendar.DATE, 10);
        showtime(now);
        return true;
    }
    boolean borrowbook(String username){
        int res;
        if((modification.equals(null)||!ISBN.pubcheckisbn(modification))){
            System.out.println("ISBN WRONG"); return false;}
        if(modification2.equals("-t"))res=testUser.userTreeMap.get(username).borrowBook(modification,buaa,now);
       else res=testUser.userTreeMap.get(username).borrowBook(modification,buaa,now=Calendar.getInstance());
        Date tasktime=testUser.userTreeMap.get(username).noBorrow.getTime();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       if(res==1){System.out.println("borrow book successfully!");return true;}
       else if(res==0)System.out.println("If any books are not returned, please return them and borrow them again.");
       else if(res==2)System.out.println("You are forbidden to borrow books until "+df.format(tasktime));
       else if(res==3)System.out.println("You have borrowed four books, which is the maximum number.");
       else if(res==4)System.out.println("All the books have been lent out.");
        return false;
    }


    boolean renewbook(String username){
        User user1=testUser.userTreeMap.get(username);
        int res;
        if((modification.equals(null)||!ISBN.pubcheckisbn(modification))){
            System.out.println("ISBN WRONG"); return false;}
        if(modification2.equals("-t"))res=testUser.userTreeMap.get(username).renew(modification,now);
        else res=testUser.userTreeMap.get(username).renew(modification,now=Calendar.getInstance());
        if(res==1){System.out.println("Successful renewal of books");return true;}
        else if(res==0)System.out.println("If any books are not returned, please return them and borrow them again.");
        else if(res==2)System.out.println("You are forbidden to borrow books until"+testUser.userTreeMap.get(username).noBorrow.toString());
        else if(res==3)System.out.println("It can only be renewed once.");
        else if(res==4)System.out.println("It can be renewed only three days before it expires.");
        return false;

    }
    boolean showmybook(String username, Library lib){
        User user1=testUser.userTreeMap.get(username);
        user1.checkRent(now);
        System.out.println("The books you borrowed:"+user1.normalRent.size());
        showmyrentbook(username,user1.normalRent,lib);
        System.out.println("Books you have returned:"+user1.returnRent.size());
        showmyrentbook(username,user1.returnRent,lib);
        System.out.println("Books you've overdue:"+user1.unreturnedRent.size());
        showmyrentbook(username,user1.unreturnedRent,lib);
        return true;
    }
    boolean showmyrentbook(String username,Map<String,borrowList> Rent, Library lib){
        User user1=testUser.userTreeMap.get(username);
        int size=Rent.size();
        for(String key : Rent.keySet())
        {System.out.print(key+ " "+lib.getBookByIsbn(key).getTitle()+" "+showtime(Rent.get(key).returntime));
        System.out.print(" returntime:");showtime(Rent.get(key).returntime);}
        return  true;
    }
    boolean returnBook(String username){
        User user1=testUser.userTreeMap.get(username);
        if((modification.equals(null)||!ISBN.pubcheckisbn(modification))){
            System.out.println("ISBN WRONG"); return false;}
        int res;
        if(modification2.equals("-t"))res=testUser.userTreeMap.get(username).returnbook(modification,buaa,now);
        else res=testUser.userTreeMap.get(username).returnbook(modification,buaa,now=Calendar.getInstance());
        if(res==1){System.out.println("Successful returned a book");return true;}
        else if(res==0)System.out.println("If any books are not returned, please return them and borrow them again.");// TODO: 2019/5/11 outofisbn
        return false;
    }
    void savebook(){
        StringBuilder book=new StringBuilder();
        for(String key: buaa.libraryMap.keySet()){
            Book temp=buaa.libraryMap.get(key);
          book.append(temp.getTitle());
          book.append(",");
          book.append(temp.code.isbn);
            book.append(",");
            book.append(temp.getAuthor());
            book.append(",");
            book.append(temp.getPrice());
            book.append(",");
            book.append(temp.getRest());
            book.append(",");
            book.append(temp.getTotal());
            book.append("\n");
        }


        book.setLength(book.length() - 1);



        File file = new File("src/inputbook.txt");
        try (OutputStream os = new FileOutputStream(file)) {


            String book2=book.toString();
            byte[] data = book2.getBytes();

            os.write(data);
            System.out.println("bookmessage successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void saveuser(){
        StringBuilder us=new StringBuilder();
        for(String key: testUser.userTreeMap.keySet()){
            User temp=testUser.userTreeMap.get(key);
            us.append(temp.getName());
            us.append(",");
            us.append(temp.getPassword());
            us.append(",");
            us.append(temp.level.toString());
            us.append(",");
            us.append(temp.noBorrow.get(Calendar.YEAR));
            us.append(",");
            us.append(temp.noBorrow.get(Calendar.MONTH));
            us.append(",");
            us.append(temp.noBorrow.get(Calendar.DATE));
            us.append(",");
            us.append(temp.noBorrow.get(Calendar.HOUR));
            us.append(",");
            us.append(temp.noBorrow.get(Calendar.MINUTE));
            us.append(",");
            us.append(temp.noBorrow.get(Calendar.SECOND));
            us.append("\r\n");
        }


        us.setLength(us.length() - 1);



        File file = new File("src/inputuser.txt");
        try (OutputStream os = new FileOutputStream(file)) {


            String us2=us.toString();
            byte[] data = us2.getBytes();

            os.write(data);
            System.out.println("usermessage successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void saveborrow(){
        StringBuilder us=new StringBuilder();
        for(String key: testUser.userTreeMap.keySet()){
            for(String key2:testUser.userTreeMap.get(key).normalRent.keySet())
            {borrowList temp=testUser.userTreeMap.get(key).normalRent.get(key2);
                us.append(temp.isbn);
                us.append(",");
                us.append(temp.name);
                us.append(",");
                us.append(key);
                us.append(",");
                us.append("normal");
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.YEAR));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.MONTH));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.DATE));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.HOUR));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.MINUTE));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.SECOND));
                us.append(",");
                us.append(temp.returntime.get(Calendar.YEAR));
                us.append(",");
                us.append(temp.returntime.get(Calendar.MONTH));
                us.append(",");
                us.append(temp.returntime.get(Calendar.DATE));
                us.append(",");
                us.append(temp.returntime.get(Calendar.HOUR));
                us.append(",");
                us.append(temp.returntime.get(Calendar.MINUTE));
                us.append(",");
                us.append(temp.returntime.get(Calendar.SECOND));
                us.append("\r\n");}
        }

        for(String key: testUser.userTreeMap.keySet()){
            for(String key2:testUser.userTreeMap.get(key).returnRent.keySet())
            {borrowList temp=testUser.userTreeMap.get(key).returnRent.get(key2);
                us.append(temp.isbn);
                us.append(",");
                us.append(temp.name);
                us.append(",");
                us.append(testUser.userTreeMap.get(key).getName());
                us.append(",");
                us.append("returned");
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.YEAR));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.MONTH));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.DATE));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.HOUR));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.MINUTE));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.SECOND));
                us.append(",");
                us.append(temp.returntime.get(Calendar.YEAR));
                us.append(",");
                us.append(temp.returntime.get(Calendar.MONTH));
                us.append(",");
                us.append(temp.returntime.get(Calendar.DATE));
                us.append(",");
                us.append(temp.returntime.get(Calendar.HOUR));
                us.append(",");
                us.append(temp.returntime.get(Calendar.MINUTE));
                us.append(",");
                us.append(temp.returntime.get(Calendar.SECOND));
                us.append("\r\n");}
        }
        for(String key: testUser.userTreeMap.keySet()){
            for(String key2:testUser.userTreeMap.get(key).unreturnedRent.keySet())
            {borrowList temp=testUser.userTreeMap.get(key).unreturnedRent.get(key2);
                us.append(temp.isbn);
                us.append(",");
                us.append(temp.name);
                us.append(",");
                us.append(testUser.userTreeMap.get(key).getName());
                us.append(",");
                us.append("unreturned");
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.YEAR));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.MONTH));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.DATE));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.HOUR));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.MINUTE));
                us.append(",");
                us.append(temp.borrowtime.get(Calendar.SECOND));
                us.append(",");
                us.append(temp.returntime.get(Calendar.YEAR));
                us.append(",");
                us.append(temp.returntime.get(Calendar.MONTH));
                us.append(",");
                us.append(temp.returntime.get(Calendar.DATE));
                us.append(",");
                us.append(temp.returntime.get(Calendar.HOUR));
                us.append(",");
                us.append(temp.returntime.get(Calendar.MINUTE));
                us.append(",");
                us.append(temp.returntime.get(Calendar.SECOND));
                us.append("\r\n");}
        }


        us.setLength(us.length() - 1);



        File file = new File("src/borrow.txt");
        try (OutputStream os = new FileOutputStream(file)) {


            String us2=us.toString();
            byte[] data = us2.getBytes();

            os.write(data);
            System.out.println("borrowmessage successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    boolean borrowlist(){
        if((modification.equals(null)||testUser.userTreeMap.get(modification)==null)){
            System.out.println("the userid wrong"); return false;}
        else showmybook(modification,buaa);
        return true;
    }
    boolean reducenoborrow(){
        if((modification.equals(null)||testUser.userTreeMap.get(modification)==null)){
            System.out.println("the userid wrong"); return false;}
        else
        {testUser.userTreeMap.get(modification).noBorrow.add(Calendar.MONTH,-1);
        System.out.println("Prohibition of borrowing time has been reduced by one month"); return false;}

    }




}

class Library {
    TreeMap<String, Book> libraryMap;

    public Library() {
        libraryMap = new TreeMap<String, Book>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2); //升序排列
            }
        });

    }

    public boolean addBook(Book addbook) {
        if (libraryMap.containsKey(addbook.code.isbn))
        {System.out.println("图书已经包含，将更新其信息");return false;}

        if (!addbook.code.checkisbn()) {
              System.out.println("isbn信息错误，请更正");
            return false;
        }
        if (addbook.getPrice() < 0 || addbook.getRest() < 0) {
            System.out.println("价格或数量信息错误，请更正");
            return false;
        }
        libraryMap.put(addbook.code.isbn, addbook);
        return true;

    }

    public Book getBookByIsbn(String isbn) {
        final Book book = libraryMap.get(isbn);
        return book;
    }


    public boolean changeBook(String isbn, String isbn2, String title, String author, String price, int total,
                              int rest) {
        BigDecimal newprice = new BigDecimal(price);
        if (libraryMap.containsKey(isbn))
            System.out.println("图书已经包含，将更新其信息");
        else {
            System.out.println("图书信息未包含，请确认isbn代码");
            //return false;
        }
        if (!ISBN.pubcheckisbn(isbn2)) {
            System.out.println("isbn错误");
            return false;
        }
        if(libraryMap.containsKey(isbn2)) {
            System.out.println("更正的isbn信息已存在，无法操作，若错在isbn错填，请删除错填isbn的书目项重新录入");
        return false;}
        if (newprice.doubleValue() < 0 || rest < 0) {
            System.out.println("价格或数量信息错误，请更正");
            return false;
        }
        this.deleteBook(isbn);
        Book temp = new Book(isbn2, title, author, price, total, rest);
        libraryMap.put(temp.code.isbn, temp);
        System.out.println("successfully alter");
        return true;
    }

    public boolean deleteBook(String isbn) {

        libraryMap.remove(isbn);
        return true;
    }

    public List<Book> getBooksByKeyword(String keyword) {
        List<Book> matchbook = new ArrayList<>();
        for (Map.Entry<String, Book> entry : libraryMap.entrySet()) {
            if (entry.getValue().getTitle().indexOf(keyword) > -1) matchbook.add(entry.getValue());
        }
        return matchbook;

    }






}

class ISBN {
    /**
     *
     */

    private static final int _12 = 12;
    String isbn;

    ISBN(String isbn) {
        this.isbn = isbn;
    }

    void setISBN(String isbn) {
        this.isbn = isbn;
    }

    ISBN() {
    }

    boolean checkisbn() {
        String isbn2 = isbn;
        if (isbn2.length() != 13 && isbn2.length() != 10)
            return false;
        if (isbn2.length() == 10) {
            return checkisbn10(isbn2);
        }
        if (isbn2.length() == 13) {

            return checkisbn13(isbn2);
        }
        return false;

    }

    public static boolean pubcheckisbn(String isbn2) {

        if (isbn2.length() != 13 && isbn2.length() != 10)
            return false;
        if (isbn2.length() == 10) {
            return checkisbn10(isbn2);
        }
        if (isbn2.length() == 13) {

            return checkisbn13(isbn2);
        }
        return false;

    }

    static boolean checkisbn10(String isbn) {
        String isbn2 = isbn;
        int sum = 0;
        for (var i = 0; i < 9; i++) {
            sum += (int) (isbn2.charAt(i) - '0') * (11 - i);
        }
        if (isbn2.charAt(9) == 'X')
            sum += 10;
        else
            sum += isbn2.charAt(9) - '0';
        if (sum % 11 != 0)
            return false;
        return true;
    }

    static boolean checkisbn13(String isbn) {
        String isbn2 = isbn;
        if (isbn2.substring(0, 3).equals("978") && isbn2.substring(0, 3).equals("979"))
            return false;
        else if (isbn2.charAt(12) == 'X')
            return false;

        int sum = 0;
        for (var i = 0; i < 13; i++) {
            if (i % 2 == 0)
                sum += (int) (isbn2.charAt(i) - '0') * 1;
            else
                sum += (int) (isbn2.charAt(i) - '0') * 3;

        }

        if (sum % 10 != 0)
            return false;
        return true;
    }

    String toString(String code) {
        String output = code;
        if (code.length() == 10)
            output = String.format("%s-%s", code.substring(0, 9), code.substring(9));
        if (code.length() == 13)
            output = String.format("%s-%s-%s", code.substring(0, 3), code.substring(2, 12), code.substring(12));
        return output;
    }

}

class Book {
    ISBN code;
    private String title;
    private String author;
    private BigDecimal price;//= new BigDecimal("2.111");
    private int total;
    private int rest;

    Book(String code, String title, String author, String price, int rest, int total) {
        this.code = new ISBN(code);
        this.title = title;
        this.author = author;
        this.price = new BigDecimal(price);
        this.total = total;
        this.rest = rest;
    }

    ISBN getCode() {
        return code;
    }

    void setCode(String code) {
        this.code.isbn = code;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getAuthor() {
        return author;
    }

    void setAuthor(String author) {

        this.author = author;
    }

    double getPrice() {
        return price.doubleValue();
    }

    void setPrice(String price) {
        BigDecimal newprice = new BigDecimal(price);
        this.price = newprice;
    }

    int getTotal() {
        return total;

    }

    void setTotal(int total) {
        this.total = total;
    }

    int getRest() {
        return rest;

    }

    void setRest(int rest) {
        this.rest = rest;
    }

    void print() {
        System.out.println("ISBN:" + this.code.isbn);
        System.out.println("author:" + author);
        System.out.println("title:" + title);
        System.out.println("price:" + (double) price.doubleValue());
        System.out.println("total:" + total);
        System.out.println("rest:" + rest);

    }

}