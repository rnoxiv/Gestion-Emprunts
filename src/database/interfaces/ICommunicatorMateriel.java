package database.interfaces;

import model.Materiel;

public interface ICommunicatorMateriel {

    public void saveMateriel(Materiel materiel);

    public void editMateriel(Materiel materiel);
}
