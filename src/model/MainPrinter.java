package model;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.print.*;
import javax.imageio.ImageIO;

public class MainPrinter implements Printable, ActionListener {

    private static final int WIDTH = 595;
    private static final int HEIGHT = 842;
    private final static String title = "FICHE PRET DE MATERIEL";
    private Image logo;

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        
        try {
            logo = ImageIO.read(getClass().getResource("/view/images/negro.png"));

        } catch (Exception e) {
        }
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        
        g.drawImage(logo, WIDTH/50, HEIGHT/100, null);
        
        /* Now we perform our rendering */
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);

        int fontSize = Math.min(WIDTH, HEIGHT) / 40;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));

        int textWidth = (int) (g.getFont().getStringBounds(title, frc).getWidth());
        int textHeight = (int) (g.getFont().getStringBounds(title, frc).getHeight());

        g.drawString(title, WIDTH / 2 - textWidth / 2, HEIGHT / 10 - textHeight / 2);

        //g.drawString("FICHE D'EMPRUNT!", 258, 100);
//        try {
//            FileReader fr = new FileReader(ticket);
//            BufferedReader br = new BufferedReader(fr);
//
//            String s;
//            while ((s = br.readLine()) != null) {
//                y += interline;
//                g2.drawString(s, x, y);
//            }
//        } catch (IOException e) {
//            throw new PrinterException("File to print does not exist (" + ticket.getAbsolutePath() +") !");
//        }

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    @Override
    public void actionPerformed(ActionEvent e
    ) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                /* The job did not successfully complete */
            }
        }
    }
}
