import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;

public class main {


    public static void main(String[] args){
    execute("teach","root","123123","C:","Polo","mapper");

   }



   public   static void execute(String database,String username,String password,String fileLocation,String polo,String namespace){
        fileExecute file=new fileExecute();
        file.location=fileLocation;
        file.polo=polo;
        file.namespace=namespace;
       connMysql conn=new connMysql();
       conn.database=database;
       conn.PASSWORD=password;
       conn.USERNAME=username;
       List<String> tableName=conn.getTableNames();
     for(int i=0;i<tableName.size();i++){
      LinkedHashMap<String,String> column= getColumn(conn,tableName.get(i));
      LinkedHashMap<String,String> key= getPrimaryKey(conn,tableName.get(i));
         table tt=new table();
         tt.setTable(tableName.get(i),key,column);
         file.table=tt;
         file.setPolo();
         file.setMapper();
         file.setInter();
         file.setController();
     }

   }

   public static LinkedHashMap<String,String>  getPrimaryKey( connMysql conn,String tableName){

        List<String> columnName=conn.getColumnNames(tableName);
        List<String> columnType=conn.getColumnTypes(tableName);
       LinkedHashMap<String,String> column=new LinkedHashMap<>();
       LinkedHashMap<String,String> key=new LinkedHashMap<>();
        for(int i=0;i<columnName.size();i++){
         column.put(columnName.get(i),columnType.get(i));
        }

        List<String> keyName=conn.getPrimary(tableName);
        for(int i=0;i<keyName.size();i++){
             key.put(keyName.get(i),column.get(keyName.get(i)));
        }
        return key;
   }
   public static LinkedHashMap<String,String> getColumn(connMysql conn,String tableName){
       List<String> columnName=conn.getColumnNames(tableName);
       List<String> columnType=conn.getColumnTypes(tableName);
       LinkedHashMap<String,String> column=new LinkedHashMap<>();
       List<String>  keyName=conn.getPrimary(tableName);
        for(int i=0;i<columnName.size();i++){
            if( keyName.indexOf(columnName.get(i))!=-1){

            }else{
            column.put(columnName.get(i),columnType.get(i));
            }
        }
       return  column;
   }


}
