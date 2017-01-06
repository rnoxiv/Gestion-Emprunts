package model;

public class Emprunt {
    
    private String empruntId, empruntDate, empruntDateRetour, empruntPersonel, empruntStatut, empruntMateriel, empruntMaterielID, empruntPersonelID, empruntQuantite;

    public String getEmpruntId() {
        return empruntId;
    }

    public void setEmpruntId(String empruntId) {
        this.empruntId = empruntId;
    }

    public String getEmpruntDate() {
        return empruntDate;
    }
    
    public void setEmpruntDate(String empruntDate) {
        this.empruntDate = empruntDate;
    }

    public String getEmpruntDateRetour() {
        return empruntDateRetour;
    }

    public void setEmpruntDateRetour(String empruntDateRetour) {
        this.empruntDateRetour = empruntDateRetour;
    }

    public String getEmpruntPersonel() {
        return empruntPersonel;
    }

    public void setEmpruntPersonel(String empruntPersonel) {
        this.empruntPersonel = empruntPersonel;
    }

    public String getEmpruntQuantite() {
        return empruntQuantite;
    }

    public void setEmpruntQuantite(String empruntQuantite) {
        this.empruntQuantite = empruntQuantite;
    }

    public String getEmpruntMateriel() {
        return empruntMateriel;
    }

    public void setEmpruntMateriel(String empruntMateriel) {
        this.empruntMateriel = empruntMateriel;
    }

    public String getEmpruntStatut() {
        return empruntStatut;
    }

    public void setEmpruntStatut(String empruntStatus) {
        this.empruntStatut = empruntStatus;
    } 

    public String getEmpruntMaterielID() {
        return this.empruntMaterielID;
    }

    public void setEmpruntMaterielID(String empruntMaterielID) {
        this.empruntMaterielID = empruntMaterielID;
    }

    public String getEmpruntPersonelID() {
        return this.empruntPersonelID;
    }

    public void setEmpruntPersonelID(String empruntPersonelID) {
        this.empruntPersonelID = empruntPersonelID;
    }
    
}
