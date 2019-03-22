package com.impltech.chatApp.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonReadingUtil {

    public static JSONObject readFile(String filePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        File file = new File(ClassLoader.getSystemResource(filePath).getFile());
        Object obj = parser.parse(new FileReader(file));
        return (JSONObject) obj;
    }
}
