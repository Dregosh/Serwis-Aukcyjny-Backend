package com.sda.serwisaukcyjnybackend.application.command;

import javax.validation.Valid;

public interface CommandHandler<T extends Command<R>, R> {
    CommandResult<R> handle(@Valid T command);
    Class<? extends Command<R>> commandClass();
}
