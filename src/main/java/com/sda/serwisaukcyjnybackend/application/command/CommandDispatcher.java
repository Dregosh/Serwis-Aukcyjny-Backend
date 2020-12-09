package com.sda.serwisaukcyjnybackend.application.command;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommandDispatcher {
    private final Map<Class<Command>, CommandHandler> handlersMap = new ConcurrentHashMap<>();

    public <R> CommandResult<R> handle(Command command) {
        if (handlersMap.containsKey(command.getClass())) {
            return handlersMap.get(command.getClass()).handle(command);
        }
        throw new IllegalArgumentException(String.format("Could not find handler for command %s", command.getClass()));
    }

    public CommandDispatcher(CommandHandler ... handlers) {
        for (CommandHandler handler : handlers) {
            if(handlersMap.containsKey(handler.commandClass())) {
                throw new IllegalArgumentException(String.format("Could not register commands with same commandClass %s", handler.commandClass()));
            }
            handlersMap.put(handler.commandClass(), handler);
        }
    }
}
