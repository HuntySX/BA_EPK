package com.company.Process_Mining;

public class Process_Mining_Main {

    Process_Mining_JSON_Read Reader;
    Process_Mining_Miner Miner;

    public Process_Mining_Main() {
        Reader = new Process_Mining_JSON_Read();
        Miner = new Process_Mining_Miner();
    }

    public void run() {
        Reader.Read_From_File();
        Miner.start_Mining();
    }
}
