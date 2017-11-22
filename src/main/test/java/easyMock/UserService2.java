package easyMock;

/**
 * Created by u6035457 on 9/25/2017.
 */
public class UserService2 {

    private UserDao userDao;

    public boolean registerUser(User user){

        if (user.type.equals("vip")){
            return userDao.insertUser(user);
        }else {
            System.out.println("only vip can be registered!");
            userDao.insertUser(user);
            userDao.insertUser(user);

            return false;
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}