package com.paul.filedelofx;

public class Enseignant extends Personne{
private String login;
    public Enseignant(String login, String nom, String prenom) {
        super(nom, prenom);
        this.login = login;
    }    

    public String getLogin() {
        return login;
    }
    
    
}
