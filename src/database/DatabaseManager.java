package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import database.interfaces.ICrud;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Emprunt;
import model.Materiel;
import model.Personel;

public class DatabaseManager implements ICrud {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/empruntsDB";
    private static final String DB_USERNAME = "test";
    private static final String DB_PASSWORD = "test";
    private Connection connection;
    private Statement stmt;

    private static final String CREATE_EMPRUNT_TABLE = "CREATE TABLE EMPRUNT "
            + "(empruntID INT auto_increment NOT NULL PRIMARY KEY, "
            + " dateEmprunt VARCHAR(50) NOT NULL, "
            + " dateRetour VARCHAR(50), "
            + " statut VARCHAR(10) NOT NULL, "
            + " personel VARCHAR(100) NOT NULL, "
            + " materielID VARCHAR(255) NOT NULL, "
            + " personelID VARCHAR(255) NOT NULL)";

    private static final String CREATE_MATERIEL_TABLE = "CREATE TABLE MATERIEL "
            + "(materielID INT auto_increment NOT NULL PRIMARY KEY, "
            + " groupe VARCHAR(50) NOT NULL, "
            + " sousGroupe VARCHAR(50) NOT NULL, "
            + " designation VARCHAR(50) NOT NULL, "
            + " etat VARCHAR(20) NOT NULL, "
            + " dateAchat VARCHAR(50), "
            + " dateRemplacement VARCHAR(50), "
            + " dispo VARCHAR(50), "
            + " emprunteur VARCHAR(50), "
            + " datePret VARCHAR(50))";

    private static final String CREATE_PERSONEL_TABLE = "CREATE TABLE PERSONEL "
            + "(personelID INT auto_increment NOT NULL PRIMARY KEY, "
            + " nom VARCHAR(50) NOT NULL, "
            + " prenom VARCHAR(50) NOT NULL, "
            + " statut VARCHAR(50) NOT NULL)";

    public DatabaseManager() {
        openConnection();
    }

    private void openConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            stmt = connection.createStatement();
            stmt.execute(CREATE_EMPRUNT_TABLE);
            stmt.execute(CREATE_MATERIEL_TABLE);
            stmt.execute(CREATE_PERSONEL_TABLE);
            System.out.println("success!");
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }

    @Override
    public void addEmprunt(Emprunt emprunt) {
        try {
            String sql = "INSERT INTO EMPRUNT (dateEmprunt, materielID, personelID, personel, statut) VALUES ('" + new java.sql.Date(System.currentTimeMillis()).toString() + "', '" + emprunt.getEmpruntMaterielID() + "',"
                    + " '" + emprunt.getEmpruntPersonelID() + "', '" + getPersonelName(emprunt.getEmpruntPersonelID()) + "', 'Non Rendu' )";
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addMateriel(Materiel materiel) {
        try {
            String sql = "INSERT INTO MATERIEL (groupe, sousgroupe, designation, etat, dateAchat, dateRemplacement, dispo) VALUES ('" + materiel.getGroupe() + "', '" + materiel.getSousGroupe() + "',"
                    + " '" + materiel.getDesignation() + "', '" + materiel.getEtat() + "', '" + materiel.getDateAchat() + "', '" + materiel.getDateRemplacement() + "', 'OUI')";
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addPersonel(Personel personel) {
        try {
            String sql = "INSERT INTO PERSONEL (nom, prenom, statut) VALUES ('" + personel.getName() + "',"
                    + " '" + personel.getFirstname() + "', '" + personel.getStatut() + "')";
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeEmprunt(String keyword) {
        try {
            String sql = "DELETE FROM EMPRUNT  where empruntID =  '" + keyword + "'";
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeMateriel(String keyword) {
        try {
            String sql = "DELETE FROM MATERIEL  where materielID =  '" + keyword + "'";
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removePersonel(String keyword) {
        try {
            String sql = "DELETE FROM PERSONEL  where personelID =  '" + keyword + "'";
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Emprunt> getAllEmprunts() {
        ArrayList<Emprunt> emprunts = new ArrayList<>();

        try {
            String sql = "SELECT * FROM EMPRUNT";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Emprunt emprunt = new Emprunt();
                emprunt.setEmpruntId(rs.getString("empruntID"));
                emprunt.setEmpruntDate(rs.getString("dateEmprunt"));
                emprunt.setEmpruntStatut(rs.getString("statut"));
                emprunt.setEmpruntMaterielID(rs.getString("materielID"));
                emprunt.setEmpruntPersonelID(rs.getString("personelID"));
                emprunt.setEmpruntQuantite("1");
                emprunt.setEmpruntDateRetour(rs.getString("dateRetour"));
                emprunts.add(emprunt);
            }
            return emprunts;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emprunts;
    }

    @Override
    public ArrayList<Materiel> getAllMateriels(String[] c) {
        ArrayList<Materiel> materiels = new ArrayList<>();

        try {
            String sql = "SELECT * FROM MATERIEL ";

            if (c.length >= 1) {
                sql += "WHERE groupe = '" + c[0] + "'";
            }
            if (c.length >= 2) {
                sql += " AND sousGroupe = '" + c[1] + "'";
            }
            if (c.length >= 3) {
                sql += " AND designation = '" + c[2] + "'";
            }

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Materiel materiel = new Materiel();
                materiel.setMaterielID(rs.getString("materielID"));
                materiel.setDateAchat(rs.getString("dateAchat"));
                materiel.setDateRemplacement(rs.getString("dateRemplacement"));
                materiel.setDesignation(rs.getString("designation"));
                materiel.setDispo(rs.getString("dispo"));
                materiel.setEtat(rs.getString("etat"));
                materiel.setGroupe(rs.getString("groupe"));
                materiel.setSousGroupe(rs.getString("sousgroupe"));
                materiel.setEmprunteur(rs.getString("emprunteur"));
                materiel.setDatePret(rs.getString("datePret"));
                materiels.add(materiel);
            }
            return materiels;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return materiels;
    }

    @Override
    public ArrayList<Personel> getAllPersonels() {
        ArrayList<Personel> personels = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PERSONEL ";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Personel personel = new Personel();
                personel.setPersonelID(rs.getString("personelID"));
                personel.setName(rs.getString("nom"));
                personel.setFirstname(rs.getString("prenom"));
                personel.setStatut(rs.getString("statut"));
                personels.add(personel);
            }
            return personels;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return personels;
    }

    @Override
    public void updateEmprunt(Emprunt emprunt) {
        try {
            String sql = "UPDATE EMPRUNT set personelID = '" + emprunt.getEmpruntPersonelID() + "',"
                    + " statut = '" + emprunt.getEmpruntStatut() + "', dateRetour = '" + emprunt.getEmpruntDateRetour() + "',"
                    + " materielID = '" + emprunt.getEmpruntMaterielID() + "', personel = '" + emprunt.getEmpruntPersonel() + "' WHERE empruntID = '" + emprunt.getEmpruntId() + "'";
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateMateriel(Materiel materiel) {
        try {
            String sql = "UPDATE MATERIEL set groupe = '" + materiel.getGroupe() + "', sousgroupe = '" + materiel.getSousGroupe() + "',"
                    + " designation = '" + materiel.getDesignation() + "', etat = '" + materiel.getEtat() + "', dateAchat = '" + materiel.getDateAchat() + "',"
                    + " dateRemplacement = '" + materiel.getDateRemplacement() + "', dispo = '" + materiel.getDispo() + "', emprunteur = '" + materiel.getEmprunteur() + "',"
                    + " datePret = '" + materiel.getDatePret() + "' WHERE materielID = '" + materiel.getMaterielID() + "'";
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void returnMateriel(Materiel materiel) {
        try {
            String sql = "UPDATE MATERIEL set dispo = '" + materiel.getDispo() + "', emprunteur = '" + materiel.getEmprunteur() + "',"
                    + " datePret = '" + materiel.getDatePret() + "' WHERE materielID = '" + materiel.getMaterielID() + "'";
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updatePersonel(Personel personel) {
        try {

            String sql = "UPDATE PERSONEL set nom = '" + personel.getName() + "', prenom = '" + personel.getFirstname() + "',"
                    + " statut = '" + personel.getStatut() + "' WHERE personelID = '" + personel.getPersonelID() + "'";
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<String> getAllGroupe() {
        ArrayList<String> groupes = new ArrayList<>();

        try {
            String sql = "SELECT DISTINCT(groupe) AS groupe FROM MATERIEL ORDER BY groupe";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                groupes.add(rs.getString("groupe"));
            }
            return groupes;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groupes;
    }

    @Override
    public ArrayList<String> getSousGroupe(String g) {

        ArrayList<String> sousGroupes = new ArrayList<>();

        try {
            String sql = "SELECT DISTINCT(sousGroupe) AS sousGroupe FROM MATERIEL WHERE groupe = '" + g + "' ORDER BY sousGroupe";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                sousGroupes.add(rs.getString("sousGroupe"));
            }
            return sousGroupes;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sousGroupes;
    }

    @Override
    public String getDesignation(String id) {

        try {
            String sql = "SELECT designation FROM MATERIEL  WHERE materielID = '" + id + "'";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                return rs.getString("designation");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    @Override
    public ArrayList<String> getDesignations(String sg) {

        ArrayList<String> designations = new ArrayList<>();

        try {
            String sql = "SELECT DISTINCT(designation) AS designation FROM MATERIEL  WHERE sousgroupe = '" + sg + "' ORDER BY designation";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                designations.add(rs.getString("designation"));
            }
            return designations;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return designations;
    }

    @Override
    public String getEmpruntMaterielID(String id) {
        String res = "";
        try {
            String sql = "SELECT materielID FROM EMPRUNT WHERE empruntID = '" + id + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                res = rs.getString("materielID");
            }

            return res;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public String getPeronelID(String[] perso) {
        String res = "";
        try {
            String sql = "SELECT personelID FROM PERSONEL WHERE nom = '" + perso[0] + "' AND prenom = '" + perso[1] + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                res = rs.getString("personelID");
            }

            return res;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;

    }

    @Override
    public String getPersonelName(String id) {
        String res = "";
        try {
            String sql = "SELECT nom, prenom FROM PERSONEL WHERE personelID = '" + id + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                res = nom + " " + prenom;
            }

            return res;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public Personel getPersonel(String id) {
        Personel res = new Personel();
        try {
            String sql = "SELECT nom, prenom FROM PERSONEL WHERE personelID = '" + id + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                res.setName(rs.getString("nom"));
                res.setFirstname(rs.getString("prenom"));
            }

            return res;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public ArrayList<Emprunt> searchEmprunt(JFrame frame, String keyword) {

        ArrayList<Emprunt> emprunts = new ArrayList();

        String idToFind = "";

        try {
            String sql = "SELECT materielID from MATERIEL WHERE designation = '" + keyword + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                idToFind = rs.getString("materielID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String sql = "SELECT * FROM EMPRUNT WHERE materielID = '" + idToFind + "' OR personel = '" + keyword + "' OR statut = '" + keyword + "'";
            ResultSet rs = stmt.executeQuery(sql);
            int size = 0;
            if (rs != null) {
                rs.beforeFirst();
                rs.last();
                size = rs.getRow();

                System.out.println(size);

                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    Emprunt emprunt = new Emprunt();
                    emprunt.setEmpruntId(rs.getString("empruntID"));
                    emprunt.setEmpruntDate(rs.getString("dateEmprunt"));
                    emprunt.setEmpruntStatut(rs.getString("statut"));
                    emprunt.setEmpruntMaterielID(rs.getString("materielID"));
                    emprunt.setEmpruntPersonelID(rs.getString("personelID"));
                    emprunt.setEmpruntQuantite("1");
                    emprunt.setEmpruntDateRetour(rs.getString("dateRetour"));
                    emprunts.add(emprunt);
                }

                if (size == 1) {
                    JOptionPane.showMessageDialog(frame, "Il y a " + size + " élément qui correspond à la recherche");
                } else if (size > 1) {
                    JOptionPane.showMessageDialog(frame, "Il y a " + size + " éléments qui correspondent à la recherche");
                } else {
                    JOptionPane.showMessageDialog(frame, "Aucuns éléments trouvés");
                }
            }

            return emprunts;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emprunts;
    }

    @Override
    public ArrayList<Materiel> searchMateriel(JFrame frame, String keyword) {

        ArrayList<Materiel> materiels = new ArrayList();

        try {
            String sql = "SELECT * FROM MATERIEL WHERE groupe = '" + keyword + "' OR sousGroupe = '" + keyword + "' OR designation = '" + keyword + "' OR dispo = '" + keyword + "'";
            ResultSet rs = stmt.executeQuery(sql);
            int size = 0;
            if (rs != null) {
                rs.beforeFirst();
                rs.last();
                size = rs.getRow();

                System.out.println(size);

                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    Materiel materiel = new Materiel();
                    materiel.setMaterielID(rs.getString("materielID"));
                    materiel.setDateAchat(rs.getString("dateAchat"));
                    materiel.setDateRemplacement(rs.getString("dateRemplacement"));
                    materiel.setDesignation(rs.getString("designation"));
                    materiel.setDispo(rs.getString("dispo"));
                    materiel.setEtat(rs.getString("etat"));
                    materiel.setGroupe(rs.getString("groupe"));
                    materiel.setSousGroupe(rs.getString("sousgroupe"));
                    materiel.setEmprunteur(rs.getString("emprunteur"));
                    materiel.setDatePret(rs.getString("datePret"));
                    materiels.add(materiel);
                }

                if (size == 1) {
                    JOptionPane.showMessageDialog(frame, "Il y a " + size + " élément qui correspond à la recherche");
                } else if (size > 1) {
                    JOptionPane.showMessageDialog(frame, "Il y a " + size + " éléments qui correspondent à la recherche");
                } else {
                    JOptionPane.showMessageDialog(frame, "Aucuns éléments trouvés");
                }
            }

            return materiels;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return materiels;
    }

    @Override
    public ArrayList<Personel> searchPersonel(JFrame frame, String keyword) {

        ArrayList<Personel> personels = new ArrayList();

        try {
            String sql = "SELECT * FROM PERSONEL WHERE nom = '" + keyword + "' OR prenom = '" + keyword + "' OR statut = '" + keyword + "'";
            ResultSet rs = stmt.executeQuery(sql);
            int size = 0;
            if (rs != null) {
                rs.beforeFirst();
                rs.last();
                size = rs.getRow();

                System.out.println(size);

                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    Personel personel = new Personel();
                    personel.setPersonelID(rs.getString("personelID"));
                    personel.setName(rs.getString("nom"));
                    personel.setFirstname(rs.getString("prenom"));
                    personel.setStatut(rs.getString("statut"));
                    personels.add(personel);
                }

                if (size == 1) {
                    JOptionPane.showMessageDialog(frame, "Il y a " + size + " élément qui correspond à la recherche");
                } else if (size > 1) {
                    JOptionPane.showMessageDialog(frame, "Il y a " + size + " éléments qui correspondent à la recherche");
                } else {
                    JOptionPane.showMessageDialog(frame, "Aucuns éléments trouvés");
                }
            }

            return personels;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return personels;
    }
}
