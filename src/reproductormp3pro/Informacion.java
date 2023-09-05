
package reproductormp3pro;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;


public class Informacion extends javax.swing.JFrame {

    public Informacion() {
        initComponents();
        //Creamos una variable dimension para obtener la resolucion de la pantalla
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        //Con este metodo le pasamos las cordenadas width y height y se las pasamos para que nos estableca la posicion
        setLocation((dimension.width)-670, (dimension.height)-520);
        setDefaultCloseOperation(1);
        setVisible(true);
        color = texto5.getForeground();
        llamada(texto4,"https://www.youtube.com/channel/UChFlaF3Ji9vWhizWyhBQdBg");
        llamada(texto5,"http://www.it.uu.se/edu/course/homepage/devgui/vt07/material/"
                + "api/basicplayer3.0/javazoom/jlgui/basicplayer/BasicPlayer.html");
        llamada(texto6,"http://www.javazoom.net/jlgui/api.html");
        llamada(texto7,"http://www.jthink.net/jaudiotagger");
        
        
    }

    public void llamada(JLabel label,String url){
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (URISyntaxException | IOException ex) {}
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(color);
            }
            
            
        });
        
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Version = new javax.swing.JLabel();
        texto7 = new javax.swing.JLabel();
        texto6 = new javax.swing.JLabel();
        texto5 = new javax.swing.JLabel();
        texto4 = new javax.swing.JLabel();
        texto3 = new javax.swing.JLabel();
        texto2 = new javax.swing.JLabel();
        texto1 = new javax.swing.JLabel();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Version.setFont(new java.awt.Font("Through the night", 1, 18)); // NOI18N
        Version.setForeground(new java.awt.Color(255, 153, 51));
        Version.setText("Version del Programa V.0.2");
        getContentPane().add(Version, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 310, -1));

        texto7.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        texto7.setForeground(new java.awt.Color(153, 153, 255));
        texto7.setText("Descarga libreria Jaudiotagger http://www.jthink.net/jaudiotagger/");
        getContentPane().add(texto7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 610, -1));

        texto6.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        texto6.setForeground(new java.awt.Color(153, 153, 255));
        texto6.setText("Descarga Basic Player http://www.javazoom.net/jlgui/api.html");
        getContentPane().add(texto6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        texto5.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        texto5.setForeground(new java.awt.Color(153, 153, 255));
        texto5.setText("Basic Player http://www.it.uu.se/edu/course/homepage/devgui/vt07/material/api/basicplayer3.0/javazoom/jlgui/basicplayer/BasicPlayer.html");
        getContentPane().add(texto5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 610, -1));

        texto4.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        texto4.setForeground(new java.awt.Color(153, 153, 255));
        texto4.setText("Alias Rey Huevos Duros...");
        getContentPane().add(texto4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 600, -1));

        texto3.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        texto3.setForeground(new java.awt.Color(204, 153, 0));
        texto3.setText("Programa Echo por Yacoobs Cort. Mart.");
        getContentPane().add(texto3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 600, -1));

        texto2.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        texto2.setForeground(new java.awt.Color(204, 153, 0));
        texto2.setText("Agradecimiento a todos los programadores por sus conocimientos prestados");
        getContentPane().add(texto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 610, -1));

        texto1.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        texto1.setForeground(new java.awt.Color(204, 153, 0));
        texto1.setText("Este programa esta echo para enseñar el conocimiento de programacion:");
        getContentPane().add(texto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 610, 20));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/fondos/music1.png"))); // NOI18N
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

   Color color;
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JLabel Version;
    private javax.swing.JLabel texto1;
    private javax.swing.JLabel texto2;
    private javax.swing.JLabel texto3;
    private javax.swing.JLabel texto4;
    private javax.swing.JLabel texto5;
    private javax.swing.JLabel texto6;
    private javax.swing.JLabel texto7;
    // End of variables declaration//GEN-END:variables
}
