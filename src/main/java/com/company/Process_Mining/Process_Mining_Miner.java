package com.company.Process_Mining;

public class Process_Mining_Miner {

    private Process_Mining_JSON_Read Reader;


    public Process_Mining_Miner() {
    }

    public Process_Mining_Miner(Process_Mining_JSON_Read Reader) {
        this.Reader = Reader;
    }


    public Process_Mining_JSON_Read getReader() {
        return Reader;
    }

    public void setReader(Process_Mining_JSON_Read reader) {
        Reader = reader;
    }

    public void start_Mining() {
        System.out.println("Starting Filter");


    }
}
