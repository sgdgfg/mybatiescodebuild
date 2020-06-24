import java.util.*;

public class table {

     String tableName;
    String[][] key;
    String[][] list;     //二维数组  列名 类型

    public void setTable(String tableName,String[][] key,String[][] list){
    this.tableName=tableName;
    this.key=key;
    this.list=list;

    }
    public void setTable(String tableName, HashMap<String,String> k, LinkedHashMap<String,String> l){
        this.tableName=tableName;
        int i=0;
        list=new String[l.size()][2];
        for (Map.Entry<String, String> entry: l.entrySet()) {
            list[i][0]=entry.getKey();
            list[i][1]=entry.getValue();
            i++;
        }
        i=0;
        key=new String[k.size()][2];
        for (Map.Entry<String,String> entry: k.entrySet()) {
            key[i][0]=entry.getKey();
            key[i][1]=entry.getValue();
            i++;
        }
       for(int t=0;t<list.length;t++){
        System.out.println(list[t][0]+"   "+list[t][1]);
              list[t][1]=transform(list[t][1]);
       }
        for(int t=0;t<key.length;t++){
        System.out.println(key[t][0]+"    "+key[t][1]);
            key[t][1]=transform(key[t][1]);}
    }

    public String transform(String a){
        if(a.equals("TEXT"))
            return "String";
        if(a.equals("INT"))
            return "int";
        if(a.equals("TIMESTAMP"))
            return "Time";
        if(a.equals("CHAR"))
            return "String";
        return a;
    }

}
