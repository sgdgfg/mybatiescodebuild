import java.io.File;
import java.io.FileOutputStream;

public class fileExecute {
    String location ;// 生成文件位置
    String  namespace;//  mybaties 接口位置
    String polo;// 数据库映射类位置
    table table;

    public void setMapper( ){     //生成mapper.xml
         String head="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                 "<!DOCTYPE mapper\n" +
                 "        PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
                 "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                 "<mapper namespace=\""+namespace+table.tableName+"Dao"+"\">";
         String list="";
        for(int i=0;i<table.key.length;i++){
            if(i==table.key.length-1){
                list+="#{"+table.key[i][0]+"}";
                break;
            }
            list+="#{"+table.key[i][0]+"},";
        }
         for(int i=0;i<table.list.length;i++){
             if(i==table.list.length-1){
                 list+="#{"+table.list[i][0]+"}";
                 break;
             }
        list+="#{"+table.list[i][0]+"},";
          }

         String insert=" <insert id=\"add\" parameterType=\""+polo+"\" >\n" +
                 "\t        insert into "+table.tableName+"  values ("+list+")\n" +
                 "\t    </insert>";

         String ky="";
        for(int i=0;i<table.key.length;i++){
            if(i==table.key.length-1){
                ky+=table.key[i][0]+"="+  "#{"+table.key[i][0]+"}";
                break;
            }
            ky+=table.key[i][0]+"="+  "#{"+table.key[i][0]+"} and  ";
        }
         String delect="<delete id=\"delete\" parameterType=\""+polo+"\" >\n" +
                 "\t        delete from "+table.tableName+" where "+ky+"\n" +
                 "\t    </delete>";

        String get="  <select id=\"get\" parameterType=\"String\" resultType=\"polo.thePolo\">\n" +
                "\t        select * from   "+table.tableName+"  where  "+ky+"\n" +
                "\t    </select>";
       String list1="  <select id=\"list\" resultType=\""+polo+"\">\n" +
               "\t        select * from  "+table.tableName+"\n" +
               "\t    </select>\n" +
               "</mapper>\n";
       File mapper =new File(location+"//"+table.tableName+"-mapper.xml");
       String allss=head+insert+delect+get+list1;
       try{
           FileOutputStream fileOutputStream=new FileOutputStream(mapper);
           fileOutputStream.write(allss.getBytes());
       }catch(Exception e){
           e.printStackTrace();
       }

    }



    public void setInter(){                // 生成mybaties 接口
        String key="";
        for(int i=0;i<table.key.length;i++){
            if(i==table.key.length-1){
                key+=table.key[i][1]+"  "+table.key[i][0];
            break;
            }
            key+=table.key[i][1]+"  "+table.key[i][0]+",";
        }
        String ss="public interface "+table.tableName+"Dao"+" {\n" +
                "public void add("+table.tableName+"  "+table.tableName+");\n" +
                "public "+table.tableName+"  get("+key+");\n" +
                "public void delete ("+table.tableName+"  "+table.tableName+");\n" +
                "public "+table.tableName+"[]  list();\n" +
                "}";

        File mapper =new File(location+"//"+table.tableName+"Dao.java");

        try{
            FileOutputStream fileOutputStream=new FileOutputStream(mapper);
            fileOutputStream.write(ss.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setPolo(){
        String ss="public class "+table.tableName+"{  \n";
        for(int i=0;i<table.key.length;i++){
            ss+=table.key[i][1]+"   "+table.key[i][0]+";\n";
        }
        for(int i=0;i<table.list.length;i++){
            ss+=table.list[i][1]+"   "+table.list[i][0]+";\n";
        }
        for(int i=0;i<table.key.length;i++){
            ss+="public "+table.key[i][1]+"   get" +table.key[i][0]+"(){ return "+table.key[i][0]+";}\n";
            ss+="public  void"+"   set" +table.key[i][0]+"( "+table.key[i][1] +" "+ table.key[i][0]+"){ this."+table.key[i][0]+"="+table.key[i][0]+";}\n";
        }
        for(int i=0;i<table.list.length;i++){
            ss+="public "+table.list[i][1]+"   get" +table.list[i][0]+"(){ return "+table.list[i][0]+";}\n";
            ss+="public  void"+"   set" +table.list[i][0]+"( "+table.list[i][1] +" "+ table.list[i][0]+"){ this."+table.list[i][0]+"="+table.list[i][0]+";}\n";
        }
        ss+="}";

        File mapper =new File(location+"//"+table.tableName+".java");

        try{
            FileOutputStream fileOutputStream=new FileOutputStream(mapper);
            fileOutputStream.write(ss.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setController(){

    }
}
