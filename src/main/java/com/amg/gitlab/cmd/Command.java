package com.amg.gitlab.cmd;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class Command {

    public static String execute(String cmd, File filePath) {
        BufferedReader br = null;
        Process p = null;
        StringBuilder sb = new StringBuilder();
        try {
            if (filePath == null) {
                p =  Runtime.getRuntime().exec(cmd);
            } else {
                p = Runtime.getRuntime().exec(cmd, null, filePath);
            }
            br = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
            String readLine = "";
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
                System.out.println(readLine);
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
