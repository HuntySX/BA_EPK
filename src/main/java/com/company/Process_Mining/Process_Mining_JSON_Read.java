package com.company.Process_Mining;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Process_Mining_JSON_Read {

    JSONArray InstanceList;

    public Process_Mining_JSON_Read() {

    }

    public void Read_From_File() {

        JSONParser Functionparser = new JSONParser();

        try (FileReader reader = new FileReader("./InstanceLog/Line.json")) {
            Object obj = Functionparser.parse(reader);

            InstanceList = (JSONArray) obj;

            System.out.println("Resolving Parser");

            /*for (Object object: InstanceList) {
                System.out.println( object);
            }*/
            System.out.println("Parser resolved");

            InstanceList.forEach(Instance -> parseInstanceObject((JSONObject) Instance));


            //Iterate over employee array
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void parseInstanceObject(JSONObject inst) {
        //Get employee first name
        String Proc_Status = (String) inst.get("Status");
        long Instance_ID = (long) inst.get("Instance_ID");
        System.out.println(Instance_ID + ": " + Proc_Status.toString());

    }

}
