package com.example.demo1.model.strategy;

import java.util.List;

public class CombinedLogStrategy implements LogStrategy {
    private final List<LogStrategy> strategies;

    public CombinedLogStrategy(List<LogStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public void log(String message) {
        for (LogStrategy strategy : strategies) {
            strategy.log(message);
        }
    }
}
