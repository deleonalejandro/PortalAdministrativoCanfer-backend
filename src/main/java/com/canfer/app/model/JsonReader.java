package com.canfer.app.model;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
 
@Service
public class JsonReader 
{
    public String readLog() 
    {
          
        try {
        	
        	List<File> filesInFolder = Files.walk(Paths.get("C:\\Users\\alex2\\PortalProveedores\\log\\activity"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        	String dataString   = "[";
        	
        	for( File file:filesInFolder) {
        	String contents = new String(Files.readAllBytes(Paths.get((file).getPath())));
        	dataString = dataString +contents;
        	}
        	
        	String jsonString   = StringUtils.chop(StringUtils.chop(dataString))+"]";
			return  jsonString;
			
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null; 
        } catch (IOException e) {
            e.printStackTrace();
            return null; 
        } 
    }
    
 
}



