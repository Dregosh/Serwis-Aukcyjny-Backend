package com.sda.serwisaukcyjnybackend.application.command;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@SuppressWarnings("rawtypes")
public class CommandDispatcher {
    private final Map<Class<Command>, CommandHandler> handlersMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <R> CommandResult<R> handle(Command<R> command) {
        if (handlersMap.containsKey(command.getClass())) {
            return handlersMap.get(command.getClass()).handle(command);
        }
        throw new IllegalArgumentException(String.format("Could not find handler for command %s", command.getClass()));
    }

    @SuppressWarnings("unchecked")
    public CommandDispatcher(CommandHandler ... handlers) {
        for (CommandHandler handler : handlers) {
            if(handlersMap.containsKey(handler.commandClass())) {
                throw new IllegalArgumentException(String.format("Could not register commands with same commandClass %s", handler.commandClass()));
            }
            handlersMap.put(handler.commandClass(), handler);
        }
    }
}
