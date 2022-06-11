package learn.design.pattern.behavioral.command;

import learn.design.pattern.behavioral.command.impl.DefaultCommand;

/**
 * 遥控器
 */
public class RemoteControl {

    /**
     * 遥控器开启命令列表
     */
    private Command[] onCommands = new Command[7];

    /**
     * 遥控器关闭命令列表
     */
    private Command[] offCommands = new Command[7];
    /**
     * 记录上次执行的命令
     */
    private Command undoCommand;

    public RemoteControl() {
        DefaultCommand defaultCommand = new DefaultCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = defaultCommand;
            offCommands[i] = defaultCommand;
        }
        undoCommand = defaultCommand;
    }

    /**
     * 给遥控器按键绑定指令命令
     *
     * @param spot       按键位置
     * @param onCommand  开启命令
     * @param offCommand 关闭命令
     */
    public void setCommands(int spot, Command onCommand, Command offCommand) {
        onCommands[spot] = onCommand;
        offCommands[spot] = offCommand;
    }

    /**
     * 按下开启按钮
     */
    public void onButtonWasPushed(int spot) {
        onCommands[spot].execute();
        undoCommand = onCommands[spot];
    }

    /**
     * 按下关闭按钮
     */
    public void offButtonWasPushed(int spot) {
        offCommands[spot].execute();
        undoCommand = offCommands[spot];
    }

    /**
     * 按下撤销按钮
     */
    public void undoButtonWasPushed() {
        undoCommand.undo();
    }
}
