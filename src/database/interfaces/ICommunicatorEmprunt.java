package database.interfaces;

import model.Emprunt;

public interface ICommunicatorEmprunt {

    public void saveEmprunt(Emprunt emprunt);
    
    public void editEmprunt(Emprunt emprunt);
}
