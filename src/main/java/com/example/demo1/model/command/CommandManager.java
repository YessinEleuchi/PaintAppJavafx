package com.example.demo1.model.command;

import java.util.Stack;
import java.util.function.Consumer;

public class CommandManager {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();
    private final Consumer<String> log;

    public CommandManager(Consumer<String> log) {
        this.log = log;
    }

    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
        log.accept("Commande exécutée.");
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
            log.accept("Commande annulée.");
        } else {
            log.accept("Aucune action à annuler.");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
            log.accept("Commande refaite.");
        } else {
            log.accept("Aucune action à refaire.");
        }
    }
}
