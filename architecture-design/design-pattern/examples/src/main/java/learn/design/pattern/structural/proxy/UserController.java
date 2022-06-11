package learn.design.pattern.structural.proxy;

/**
 * 用户相关服务，提供了登录和注册接口
 */
public class UserController implements IUserController {

    @Override
    public Object login(String telephone, String password) {
        // 登录逻辑
        System.out.println("login...");
        return null;
    }

    @Override
    public Object register(String telephone, String password) {
        // 注册逻辑
        System.out.println("register...");
        return null;
    }
}

