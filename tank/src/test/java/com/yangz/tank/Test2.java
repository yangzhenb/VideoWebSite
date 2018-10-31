package com.yangz.tank;


import java.io.*;

public class Test2 {


    public static void main(String[] args) throws Exception{
        String calssStr = getStr();
        Class<Person> c = getClass(calssStr);
        Person p = c.newInstance();
        p.speak();
    }

    /*
        从txt文件中读取配置
     */
    public static String getStr() throws Exception{
        File file = new File("D:\\ss.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file),32);
        StringBuffer sb = new StringBuffer();
        String s = "";
        while((s = reader.readLine()) != null){
            sb.append(s);
        }
        return sb.toString();
    }
    /*
        根据字符串加载对应的类
     */
    public static Class<Person> getClass(String classname) throws Exception{
        Class c = Class.forName(classname);
        return c;
    }
}


interface Person{
    void speak();
}

class ABC implements Person{
    @Override
    public void speak() {
        System.out.println("你好");
    }
}
class BBC implements Person{
    @Override
    public void speak() {
        System.out.println("hello");
    }

}