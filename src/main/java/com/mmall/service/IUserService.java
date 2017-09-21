package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

import javax.servlet.http.HttpSession;


public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> validate(String validatedString, String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkForgetAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String username, String newPassword, String tokenString);

    ServerResponse<String> resetPassword(String username, String newPassword, User user);

    ServerResponse<User> modifyUserInformation(User user);

    ServerResponse<User> getInformation(Integer userId);
}
