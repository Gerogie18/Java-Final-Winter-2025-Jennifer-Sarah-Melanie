package org.keyin.user;

public class UserService {
    private final UserDAO userDAO;

    //security logic
    public boolean hasRole(String userId, String role) {
        User user = userDAO.getUserById(userId);
        return user != null && user.getRole().equals(role);
    }

    public User login(String username, String password) throws AuthenticationException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            throw new AuthenticationException("Invalid username or password");
        }
    }

    //Functions for USER DAO operations
    public void createUser(User user) {
        userDAO.createUser(user);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(String userId) {
        userDAO.deleteUserById(userId);
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User searchUserByUsername(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }

    public User searchUserByPhoneNumber(int phoneNumber) throws SQLException {
        return userDAO.getUserByPhoneNumber(phoneNumber);
    }

    public User searchUserByAddress(String address) throws SQLException {
        return userDAO.getUserByAddress(address);
    }

    public User searchUserByEmail(String email) throws SQLException {
        return userDAO.getUserByEmail(email);
    }
}
