package learn.spring.ioc.annotation.configuration.lookup;

/**
 * CommandManager.
 */
public abstract class CommandManager {

    protected abstract Command createCommand();
}