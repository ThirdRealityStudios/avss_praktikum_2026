package de.fhms.jp253163.avss.arch.mvp.lms.view.data.command;

import de.fhms.jp253163.avss.arch.mvp.lms.view.contracts.command.ICommand;

public class CommandExit implements ICommand {
    @Override
    public String getCommand() {
        return "/exit";
    }

    @Override
    public String[] getParameters() {
        return new String[0];
    }
}
