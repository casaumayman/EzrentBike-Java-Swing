/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HuyTuan
 */
public class FileHandle {

    private File file = null;
    private OutputStream outputStream = null;

    public FileHandle() {
        file = new File("account.dat");
    }

    public void write(String id, String username, String name, String endodePass, int profileId) {
        try {
            outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(id);
            outputStreamWriter.write("\n");
            outputStreamWriter.write(username);
            outputStreamWriter.write("\n");
            outputStreamWriter.write(name);
            outputStreamWriter.write("\n");
            outputStreamWriter.write(endodePass);
            outputStreamWriter.write("\n");
            outputStreamWriter.write(Integer.toString(profileId));
            outputStreamWriter.flush();
            outputStreamWriter.close();
            outputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileHandle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> read(){
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = "";
            ArrayList<String> res = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                res.add(line);
            }
            reader.close();
            inputStreamReader.close();
            return res;
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            Logger.getLogger(FileHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void clear(){
        try {
            if (isExist()) {
                System.out.println(file.getName() + " is deleted! " + (file.delete() ? "true" : "false"));
            } else {
                System.out.println("file not exist.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExist() {
        return file.exists();
    }
}
