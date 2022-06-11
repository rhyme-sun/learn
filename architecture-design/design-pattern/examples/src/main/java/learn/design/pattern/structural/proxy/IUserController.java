package learn.design.pattern.structural.proxy;

/**
 * IUserController.
 */
public interface IUserController {

    Object login(String telephone, String password);

    Object register(String telephone, String password);
}
