package com.example.demo1.model.singleton;

import com.example.demo1.model.strategy.ConsoleLogStrategy;
import com.example.demo1.model.strategy.LogStrategy;

public class LoggerService {
    private static LoggerService instance;


    private LogStrategy strategy = new ConsoleLogStrategy();


    private LoggerService() {}


    public static synchronized LoggerService getInstance() {
        if (instance == null) {
            instance = new LoggerService();
        }
        return instance;
    }

    public void setLogStrategy(LogStrategy strategy) {
        this.strategy = strategy;
    }


    public void log(String message) {
        if (strategy != null) {
            strategy.log(message);
        }
    }
}
