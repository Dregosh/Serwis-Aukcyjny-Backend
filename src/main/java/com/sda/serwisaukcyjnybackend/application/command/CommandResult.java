package com.sda.serwisaukcyjnybackend.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandResult<T> {
    private final CommandResultType resultType;
    private final T payload;

    public static CommandResult<Void> created() {
        return new CommandResult<>(CommandResultType.CREATED, null);
    }

    public static <T> CommandResult<T> created(T payload) {
        return new CommandResult<>(CommandResultType.CREATED, payload);
    }

    public static CommandResult<Void> accepted() {
        return new CommandResult<>(CommandResultType.ACCEPTED, null);
    }

    public static <T> CommandResult<T> accepted(T payload) {
        return new CommandResult<>(CommandResultType.ACCEPTED, payload);
    }

    public static CommandResult<Void> failed() {
        return new CommandResult<>(CommandResultType.FAILED, null);
    }

    public static <T> CommandResult<T> failed(T payload) {
        return new CommandResult<>(CommandResultType.FAILED, payload);
    }
}
