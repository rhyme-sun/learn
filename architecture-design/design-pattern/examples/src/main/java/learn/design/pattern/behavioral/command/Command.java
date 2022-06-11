package learn.design.pattern.behavioral.command;

/**
 * 命令对象
 */
public interface Command {

	/**
	 * 执行方法
	 */
	void execute();

	/**
	 * 撤销方法
	 */
	void undo();
}
