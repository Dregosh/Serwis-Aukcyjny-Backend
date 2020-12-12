package com.sda.serwisaukcyjnybackend.application.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommandResult<T> {
    private final CommandResultType resultType;
    private final T payload;

    public static CommandResult<Void> ok() {
        return new CommandResult<>(CommandResultType.OK, null);
    }

    public static <T> CommandResult<T> created(T payload) {
        return new CommandResult<>(CommandResultType.OK, payload);
    }

    public static CommandResult<Void> failed() {
        return new CommandResult<>(CommandResultType.FAILED, null);
    }

    public static <T> CommandResult<T> failed(T payload) {
        return new CommandResult<>(CommandResultType.FAILED, payload);
    }
}
