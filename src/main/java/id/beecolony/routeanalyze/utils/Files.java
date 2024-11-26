package id.beecolony.routeanalyze.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class Files {
    
    public String createFile(String filename) throws IOException{
        File newFile = new File(filename);

        if(!newFile.exists())
            if(newFile.createNewFile())
                return "file created";

        return null;
    }


    public String readFile(String filename) throws IOException{
        File newFile = new File(filename);

        String line = "";
        if(createFile(filename) != null){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile));

            line = bufferedReader.readLine();

            bufferedReader.close();
        }

        return line;
        
    }

    public void writeFile(String filename, String value) throws IOException{
        File newFile = new File(filename);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));

        bufferedWriter.newLine();
        bufferedWriter.write(value);

        bufferedWriter.close();
    }

}
