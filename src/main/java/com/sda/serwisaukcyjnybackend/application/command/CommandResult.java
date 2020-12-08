package com.sda.serwisaukcyjnybackend.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandResult<T> {
    private final CommandResultType resultType;
    private final T payload;

    private static CommandResult<Void> created() {
        return new CommandResult<>(CommandResultType.CREATED, null);
    }

    private static <T> CommandResult<T> created(T payload) {
        return new CommandResult<>(CommandResultType.CREATED, payload);
    }

    private static CommandResult<Void> accepted() {
        return new CommandResult<>(CommandResultType.ACCEPTED, null);
    }

    private static <T> CommandResult<T> accepted(T payload) {
        return new CommandResult<>(CommandResultType.ACCEPTED, payload);
    }

    private static CommandResult<Void> failed() {
        return new CommandResult<>(CommandResultType.FAILED, null);
    }

    private static <T> CommandResult<T> failed(T payload) {
        return new CommandResult<>(CommandResultType.FAILED, payload);
    }
}
