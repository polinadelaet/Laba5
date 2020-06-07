package app.services;

import app.controller.components.serviceMediator.Service;

import java.security.NoSuchAlgorithmException;

public interface PasswordHashService extends Service {
    String hash(String password) throws NoSuchAlgorithmException;
}
