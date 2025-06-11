package com.example.demo1.model.strategy;

public class ConsoleLogStrategy implements LogStrategy {
    @Override
    public void log(String message) {
        System.out.println("[LOG Console] " + message);
    }
}
