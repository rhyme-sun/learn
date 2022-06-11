package learn.design.pattern.behavioral.command;

import learn.design.pattern.behavioral.command.impl.CellingHighCommand;
import learn.design.pattern.behavioral.command.impl.CellingMediumCommand;
import learn.design.pattern.behavioral.command.impl.CellingOffCommand;
import learn.design.pattern.behavioral.command.impl.LightOffCommand;
import learn.design.pattern.behavioral.command.impl.LightOnCommand;
import learn.design.pattern.behavioral.command.receiver.CellingFan;
import learn.design.pattern.behavioral.command.receiver.Light;

/**
 * 命令加载器
 */
public class Main {

	public static void main(String[] args) {

		RemoteControl remoteControl = new RemoteControl();
		Light light = new Light();
		LightOnCommand lightOnCommand = new LightOnCommand(light);
		LightOffCommand lightOffCommand = new LightOffCommand(light);

		// 将灯的开关绑定带 0 号按键
		remoteControl.setCommands(0, lightOnCommand, lightOffCommand);
		// 开灯
		remoteControl.onButtonWasPushed(0);
		// 关灯
		remoteControl.offButtonWasPushed(0);
		// 取消关灯
		remoteControl.undoButtonWasPushed();
	}
}
