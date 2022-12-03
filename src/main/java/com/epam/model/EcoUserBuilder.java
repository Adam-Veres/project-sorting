package com.epam.model;

import com.epam.security.EcoUserRole;

import java.util.List;

public final class EcoUserBuilder {
    private String username;
    private String password;
    private String email;
    private EcoUserRole userRole;
    private boolean locked;
    private List<EcoService> services;

    private EcoUserBuilder() {
    }

    public static EcoUserBuilder anEcoUser() {
        return new EcoUserBuilder();
    }

    public EcoUserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public EcoUserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public EcoUserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public EcoUserBuilder withUserRole(EcoUserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public EcoUserBuilder withLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    public EcoUserBuilder withServices(List<EcoService> services) {
        this.services = services;
        return this;
    }

    public EcoUser build() {
        EcoUser ecoUser = new EcoUser();
        ecoUser.setUsername(username);
        ecoUser.setPassword(password);
        ecoUser.setEmail(email);
        ecoUser.setUserRole(userRole);
        ecoUser.setLocked(locked);
        ecoUser.setServices(services);
        return ecoUser;
    }
}
