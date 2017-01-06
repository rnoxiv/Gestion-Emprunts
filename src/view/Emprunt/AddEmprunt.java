package view.Emprunt;

import database.DatabaseManager;
import database.interfaces.ICommunicatorEmprunt;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.DocTemplate;
import model.Emprunt;
import model.Materiel;
import model.Personel;
import org.docx4j.openpackaging.exceptions.Docx4JException;

public class AddEmprunt extends javax.swing.JDialog {

    private ICommunicatorEmprunt iCommunicator;
    private DefaultTableModel defaultTableModel;
    private final DatabaseManager databaseManager;
    private Materiel selectedMateriel;
    private String[] sousGroupeS;
    private String[] designationS;
    private String[] materielToChoose;
    private String[] materielToChoose2;

    public AddEmprunt(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setResizable(false);
        sousGroupeS = new String[1];
        sousGroupeS[0] = "";
        designationS = new String[1];
        designationS[0] = "";
        databaseManager = new DatabaseManager();
        initComponents();

        setUpJTableModel();
    }

    public void setCommunicator(ICommunicatorEmprunt iCommunicator) {
        this.iCommunicator = iCommunicator;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        personel = new javax.swing.JComboBox();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        groupe = new javax.swing.JComboBox();
        sousgroupe = new javax.swing.JComboBox();
        designation = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        saveBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Effectuer un Nouvel Emprunt");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(51, 102, 0)));

        ArrayList<Personel> personels = allPersonels();

        String[] personelS = new String[personels.size()+1];
        personelS[0] = "";
        for(int i = 1; i < personels.size()+1;i++){
            personelS[i] = personels.get(i-1).getName() + " " + personels.get(i-1).getFirstname();
        }
        personel.setModel(new javax.swing.DefaultComboBoxModel(personelS));
        personel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                personelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(personel, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 214, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(personel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 158, 59));

        jLabel1.setFont(new java.awt.Font("MV Boli", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nouvel Emprunt");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(157, 157, 157))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 102, 0)));

        ArrayList<String> groupes = databaseManager.getAllGroupe();

        String[] groupeS = new String[groupes.size()+1];
        groupeS[0] = "";
        for(int i = 1; i < groupes.size()+1;i++){
            groupeS[i] = groupes.get(i-1);
        }
        groupe.setModel(new javax.swing.DefaultComboBoxModel(groupeS));
        groupe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                groupeItemStateChanged(evt);
            }
        });
        groupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupeActionPerformed(evt);
            }
        });

        sousgroupe.setModel(new javax.swing.DefaultComboBoxModel(sousGroupeS));
        sousgroupe.setEnabled(false);
        sousgroupe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sousgroupeItemStateChanged(evt);
            }
        });
        sousgroupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sousgroupeActionPerformed(evt);
            }
        });

        designation.setModel(new javax.swing.DefaultComboBoxModel(designationS));
        designation.setEnabled(false);
        designation.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                designationItemStateChanged(evt);
            }
        });
        designation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                designationActionPerformed(evt);
            }
        });

        jLabel2.setText("Groupe");

        jLabel5.setText("Sous-Groupe");

        jLabel6.setText("Désignation");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(groupe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sousgroupe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(designation, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(73, 73, 73)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(68, 68, 68)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sousgroupe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(designation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Materiel :");

        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Utilisateur:");

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(saveBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(37, 37, 37)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveBtn)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("Nouvel Emprunt");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public final void setUpJTableModel() {
        defaultTableModel = new DefaultTableModel();
        jTable1.setModel(defaultTableModel);
        defaultTableModel.addColumn("REF");
        defaultTableModel.addColumn("GROUPE");
        defaultTableModel.addColumn("SOUS-GROUPE");
        defaultTableModel.addColumn("DESIGNATION");
        defaultTableModel.addColumn("ETAT");
        defaultTableModel.addColumn("DATE D'ACHAT");
        defaultTableModel.addColumn("DATE REMPLACEMENT");
        defaultTableModel.addColumn("DISPO");
        refreshTable(new String[0]);
    }

    private void refreshTable(String[] s) {

        saveBtn.setEnabled(false);

        defaultTableModel.getDataVector().clear();

        ArrayList<Materiel> materiels = refreshRecord(s);

        materiels.stream().forEach((currentMateriel) -> {
            defaultTableModel.addRow(new Object[]{currentMateriel.getMaterielID(), currentMateriel.getGroupe(), currentMateriel.getSousGroupe(), currentMateriel.getDesignation(), currentMateriel.getEtat(), currentMateriel.getDateAchat(), currentMateriel.getDateRemplacement(), currentMateriel.getDispo()});
        });

        defaultTableModel.fireTableDataChanged();
    }

    private ArrayList<Materiel> refreshRecord(String[] s) {
        return databaseManager.getAllMateriels(s);
    }

    private ArrayList<Personel> allPersonels() {
        return databaseManager.getAllPersonels();
    }

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed

        if (verifyForm()) {
            Emprunt emprunt = new Emprunt();
            emprunt.setEmpruntMaterielID(selectedMateriel.getMaterielID());

            String[] splited = personel.getSelectedItem().toString().split("\\s+");
            
            if (splited.length >= 3) {
                String[] splited2 = new String[2];
                for (int i = 0; i < splited.length - 1; i++) {
                    splited2[0] += splited[i];
                }
                splited2[1] = splited[splited.length - 1];
                emprunt.setEmpruntPersonelID(databaseManager.getPeronelID(splited2));
            } else {
                emprunt.setEmpruntPersonelID(databaseManager.getPeronelID(splited));
            }
            
            this.iCommunicator.saveEmprunt(emprunt);
            selectedMateriel.setDispo("NON");
            selectedMateriel.setEmprunteur(personel.getSelectedItem().toString());
            selectedMateriel.setDatePret(new java.sql.Date(System.currentTimeMillis()).toString());
            databaseManager.updateMateriel(selectedMateriel);
            
            Personel personel = databaseManager.getPersonel(emprunt.getEmpruntPersonelID());
            
            //JDialog
            JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage("Voulez-vous imprimer une fiche d'Emprunt?");
            optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            JButton okBtn = new JButton();
            JButton noBtn = new JButton();

            okBtn.setText("Oui");
            noBtn.setText("Non");

            //okBtn.addActionListener(new MainPrinter());
            
            okBtn.addActionListener((ActionEvent evt1) -> {
                DocTemplate doctemplate = new DocTemplate();
                try {
                    doctemplate.insertIntoTemplate(personel, selectedMateriel);
                } catch (Docx4JException ex) {
                    Logger.getLogger(AddEmprunt.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AddEmprunt.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AddEmprunt.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Window w = SwingUtilities.getWindowAncestor(noBtn);

                if (w != null) {
                    w.setVisible(false);
                }
                        
            });

            noBtn.addActionListener((ActionEvent evt1) -> {
                Window w = SwingUtilities.getWindowAncestor(noBtn);

                if (w != null) {
                    w.setVisible(false);
                }
            });

            optionPane.setOptions(new Object[]{okBtn, noBtn});
            JDialog dialog = optionPane.createDialog(this, "Impression");
            dialog.setVisible(true);

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Please make sure you filled all the necessary Emprunt detail", "Can't Save!", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_saveBtnActionPerformed

    private void personelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_personelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_personelActionPerformed

    private void groupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_groupeActionPerformed

    private void sousgroupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sousgroupeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sousgroupeActionPerformed

    private void designationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_designationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_designationActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.rowAtPoint(evt.getPoint());

        selectedMateriel = new Materiel();

        selectedMateriel.setMaterielID(jTable1.getValueAt(row, 0).toString());
        selectedMateriel.setGroupe(jTable1.getValueAt(row, 1).toString());
        selectedMateriel.setSousGroupe(jTable1.getValueAt(row, 2).toString());
        selectedMateriel.setDesignation(jTable1.getValueAt(row, 3).toString());
        selectedMateriel.setEtat(jTable1.getValueAt(row, 4).toString());
        selectedMateriel.setDateAchat(jTable1.getValueAt(row, 5).toString());
        selectedMateriel.setDateRemplacement(jTable1.getValueAt(row, 6).toString());
        selectedMateriel.setDispo(jTable1.getValueAt(row, 7).toString());
        if ("NON".equals(selectedMateriel.getDispo())) {
            saveBtn.setEnabled(false);
        } else {
            saveBtn.setEnabled(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void groupeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_groupeItemStateChanged

        String s = groupe.getSelectedItem().toString();
        ArrayList<String> sousGroupes = databaseManager.getSousGroupe(s);
        sousGroupeS = new String[sousGroupes.size() + 1];
        sousGroupeS[0] = "";
        for (int i = 1; i < sousGroupes.size() + 1; i++) {
            sousGroupeS[i] = sousGroupes.get(i - 1);
        }

        designation.setModel(new javax.swing.DefaultComboBoxModel(sousGroupeS));
        designation.setEnabled(false);

        sousgroupe.setModel(new javax.swing.DefaultComboBoxModel(sousGroupeS));

        if (s.isEmpty()) {
            sousgroupe.setEnabled(false);
            materielToChoose = new String[0];
        } else {
            sousgroupe.setEnabled(true);
            materielToChoose = new String[1];
            materielToChoose[0] = s;
        }

        refreshTable(materielToChoose);
    }//GEN-LAST:event_groupeItemStateChanged

    private void sousgroupeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sousgroupeItemStateChanged
        String s = sousgroupe.getSelectedItem().toString();
        ArrayList<String> designations = databaseManager.getDesignations(s);
        designationS = new String[designations.size() + 1];
        designationS[0] = "";
        for (int i = 1; i < designations.size() + 1; i++) {
            designationS[i] = designations.get(i - 1);
        }

        designation.setModel(new javax.swing.DefaultComboBoxModel(designationS));

        if (s.isEmpty()) {
            materielToChoose2 = materielToChoose;
            designation.setEnabled(false);
        } else {
            materielToChoose2 = new String[materielToChoose.length + 1];
            designation.setEnabled(true);
            System.arraycopy(materielToChoose, 0, materielToChoose2, 0, materielToChoose.length);
            materielToChoose2[1] = s;
        }

        refreshTable(materielToChoose2);
    }//GEN-LAST:event_sousgroupeItemStateChanged

    private void designationItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_designationItemStateChanged
        String s = designation.getSelectedItem().toString();

        String[] materielToChoose3;

        if (s.isEmpty()) {
            materielToChoose3 = materielToChoose2;
            designation.setEnabled(false);
        } else {
            materielToChoose3 = new String[materielToChoose2.length + 1];
            designation.setEnabled(true);
            System.arraycopy(materielToChoose2, 0, materielToChoose3, 0, materielToChoose2.length);
            materielToChoose3[2] = s;
        }

        refreshTable(materielToChoose3);
    }//GEN-LAST:event_designationItemStateChanged
    private boolean verifyForm() {
        return !personel.getSelectedItem().toString().isEmpty();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox designation;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JComboBox groupe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox personel;
    private javax.swing.JButton saveBtn;
    private javax.swing.JComboBox sousgroupe;
    // End of variables declaration//GEN-END:variables

}
