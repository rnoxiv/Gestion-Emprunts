package database.interfaces;

import java.util.ArrayList;
import javax.swing.JFrame;
import model.Emprunt;
import model.Materiel;
import model.Personel;

public interface ICrud {
    
    public void addEmprunt(Emprunt emprunt);
    
    public void addMateriel(Materiel materiel);
    
    public void addPersonel(Personel personel);
    
    public void removeEmprunt(String keyword);
    
    public void removeMateriel(String keyword);
    
    public void removePersonel(String keyword);
    
    public ArrayList<Emprunt> getAllEmprunts();
    
    public ArrayList<Materiel> getAllMateriels(String[] choice);
    
    public ArrayList<Personel> getAllPersonels();
    
    public void updateEmprunt(Emprunt emprunt);
    
    public void updateMateriel(Materiel materiel);
    
    public void returnMateriel(Materiel materiel);
    
    public void updatePersonel(Personel personel);
    
    public ArrayList<String> getAllGroupe();
    
    public ArrayList<String> getSousGroupe(String groupe);
    
    public ArrayList<String> getDesignations(String sousgroupe);
    
    public String getPeronelID(String[] perso);
    
    public String getPersonelName(String id);
    
    public Personel getPersonel(String id);
    
    public String getDesignation(String id);
    
    public String getEmpruntMaterielID(String id);
    
    public ArrayList<Emprunt> searchEmprunt(JFrame frame, String keyword);
    
    public ArrayList<Materiel> searchMateriel(JFrame frame, String keyword);
    
    public ArrayList<Personel> searchPersonel(JFrame frame, String keyword);
    
}
