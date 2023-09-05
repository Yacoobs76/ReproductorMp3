package reproductormp3pro;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;




/**
 *
 * @author Yacoobs Cort. Mart. miercoles 10 de mayo de 2017
 * Ultima modificación 04-11-2018
 * Esto es el comienzo de lo que podria ser un eculizador Grafico 
 * es muy sencillo de hacer pero podriamos complicar el codigo y llegar a 
 * darle un aspecto mas profesional todo depende de que estes buscando
 * 
 * Esta clase de por si sola no hace nada, es necesiario llamar a la clase y pasar le
 * los datos de las variables E1....E14 mediante sus metodos setters...
 */
public class Ecualizador_Graphics extends javax.swing.JFrame {

    
    public Ecualizador_Graphics(JPanel jPanel_Ecualizador)  {
        
        this.jPanel_Ecualizador = jPanel_Ecualizador;
        
        setTitle("Ecualizador Grafico V.0.1");
        initComponents();
        jPanel_Ecualizador.add(new Ecualizador());
        try {
            img = ImageIO.read(new URL(getClass().getResource("fondos/FondoEcualizador.png").toString()));
        } catch (IOException ex) {
            Logger.getLogger(Ecualizador_Graphics.class.getName()).log(Level.SEVERE, null, ex);
        }
        //setVisible(true);
        
    }

    
    public class Ecualizador extends JComponent{

        @Override
        public void paint(Graphics g) {
            
            super.paint(g); //To change body of generated methods, choose Tools | Templates.
            
            Graphics2D g2 =  (Graphics2D) g;

            //Dibujamos un fondo para nuestro Ecualizador obteniendo la Dimension de jPanel
            g2.drawImage(img, 0, 0, jPanel_Ecualizador.getWidth(),jPanel_Ecualizador.getHeight(),null);
            
            //Dato importante las variables E ..... tienen que estar comprendidad entre valores de 0 a 130...
            //Los bubles for dibujaran nuestros pequeños cuadrados llamando al metodo cuando este prodeda
            //El metodo Math.abs() transforma el numero obtenido por E1 en positivo siempre...
           
            BucleFor(E1, 30, g2);
            BucleFor(E2, 40, g2);
            BucleFor(E3, 50, g2);
            BucleFor(E4, 60, g2);
            BucleFor(E5, 70, g2);
            BucleFor(E6, 80, g2);
            BucleFor(E7, 90, g2);
            BucleFor(E8, 100, g2);
            BucleFor(E9, 110, g2);
            BucleFor(E10, 120, g2);
            BucleFor(E11, 130, g2);
            BucleFor(E12, 140, g2);
            BucleFor(E13, 150, g2);
            BucleFor(E14, 160, g2);
            BucleFor(E15, 170, g2);
            BucleFor(E16, 180, g2);
            BucleFor(E17, 190, g2);
            BucleFor(E18, 200, g2);
            BucleFor(E19, 210, g2);
            BucleFor(E20, 220, g2);
            BucleFor(E21, 230, g2);
            BucleFor(E22, 240, g2);
            BucleFor(E23, 250, g2);
            BucleFor(E24, 260, g2);
            BucleFor(E25, 270, g2);
            BucleFor(E26, 280, g2);
            BucleFor(E27, 290, g2);
            BucleFor(E28, 300, g2);
            BucleFor(E29, 310, g2);
            BucleFor(E30, 320, g2);
            BucleFor(E31, 330, g2);
            BucleFor(E32, 340, g2);
            BucleFor(E33, 350, g2);
            BucleFor(E34, 360, g2);
            BucleFor(E35, 370, g2);
            BucleFor(E36, 380, g2);
            BucleFor(E37, 390, g2);
            BucleFor(E38, 400, g2);
            BucleFor(E39, 410, g2);
            BucleFor(E40, 420, g2);
            BucleFor(E41, 430, g2);
            BucleFor(E42, 440, g2);
            BucleFor(E43, 450, g2);
            BucleFor(E44, 460, g2);
            BucleFor(E45, 470, g2);
            //BucleFor(E46, 480, g2);
            //BucleFor(E47, 500, g2);
            //BucleFor(E48, 510, g2);
            
        }
    }
    
    public void BucleFor(int E, int Cord, Graphics2D g2){
        for (int x=0;x<130;x=x+3){
                 if (Math.abs(E)>x){Dibujar_Grafico(Cord,x, g2);}
            }
    }
    
    public void Dibujar_Grafico(int cordenada,int x, Graphics2D g2){
        
        g2.setColor(Color.GREEN);
        
        if (x>100){
            g2.setColor(Color.RED);
        }
        else if (x>80){
            g2.setColor(Color.ORANGE);
        }
        g2.fillRoundRect(cordenada, 140-x, 5, 2, 5, 5);     
        g2.setColor(Color.BLACK); 
        g2.drawRoundRect(cordenada, 140-x, 5, 2, 5, 5);

    }
    
    public void Repintado(){
        jPanel_Ecualizador.repaint();
    }
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(486, 246));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 766, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private JPanel jPanel_Ecualizador;
    private BufferedImage img;
    private  int E1,E2,E3,E4,E5,E6,E7,E8,E9,E10,E11,E12,E13,E14,E15,E16,E17,E18,E19,
            E20,E21,E22,E23,E24,E25,E26,E27,E28,E29,E30,E31,E32,E33,E34,E35,E36,E37,
            E38,E39,E40,E41,E42,E43,E44,E45,E46,E47,E48,E49,E50;

    public void setE1(int E1) {
        this.E1 = E1;
    }

    public void setE2(int E2) {
        this.E2 = E2;
    }

    public void setE3(int E3) {
        this.E3 = E3;
    }

    public void setE4(int E4) {
        this.E4 = E4;
    }

    public void setE5(int E5) {
        this.E5 = E5;
    }

    public void setE6(int E6) {
        this.E6 = E6;
    }

    public void setE7(int E7) {
        this.E7 = E7;
    }

    public void setE8(int E8) {
        this.E8 = E8;
    }

    public void setE9(int E9) {
        this.E9 = E9;
    }

    public void setE10(int E10) {
        this.E10 = E10;
    }

    public void setE11(int E11) {
        this.E11 = E11;
    }

    public void setE12(int E12) {
        this.E12 = E12;
    }

    public void setE13(int E13) {
        this.E13 = E13;
    }

    public void setE14(int E14) {
        this.E14 = E14;
    }
    
    public void setE15(int E15) {
        this.E15 = E15;
    }
    
    public void setE16(int E16) {
        this.E16 = E16;
    }
    
    public void setE17(int E17) {
        this.E17 = E17;
    }
    
    public void setE18(int E18) {
        this.E18 = E18;
    }
    
    public void setE19(int E19) {
        this.E19 = E19;
    }
    
    public void setE20(int E20) {
        this.E20 = E20;
    }
    
    public void setE21(int E21) {
        this.E21 = E21;
    }
    
    public void setE22(int E22) {
        this.E22 = E22;
    }
    
    public void setE23(int E23) {
        this.E23 = E23;
    }
    
    public void setE24(int E24) {
        this.E24 = E24;
    }
    
    public void setE25(int E25) {
        this.E25 = E25;
    }
    
    public void setE26(int E26) {
        this.E26 = E26;
    }
    
    public void setE27(int E27) {
        this.E27 = E27;
    }
    
    public void setE28(int E28) {
        this.E28 = E28;
    }
    
    public void setE29(int E29) {
        this.E29 = E29;
    }
    
    public void setE30(int E30) {
        this.E30 = E30;
    }
    
    public void setE31(int E31) {
        this.E31 = E31;
    }
    
    public void setE32(int E32) {
        this.E32 = E32;
    }
    
    public void setE33(int E33) {
        this.E33 = E33;
    }
    
    public void setE34(int E34) {
        this.E34 = E34;
    }
   
    public void setE35(int E35) {
        this.E35 = E35;
    }
    
    public void setE36(int E36) {
        this.E36 = E36;
    }
    
    public void setE37(int E37) {
        this.E37 = E37;
    }
    
    public void setE38(int E38) {
        this.E38 = E38;
    }
    
    public void setE39(int E39) {
        this.E39 = E39;
    }
    
    public void setE40(int E40) {
        this.E40 = E40;
    }
    
    public void setE41(int E41) {
        this.E41 = E41;
    }
    
    public void setE42(int E42) {
        this.E42 = E42;
    }
    
    public void setE43(int E43) {
        this.E43 = E43;
    }
    
    public void setE44(int E44) {
        this.E44 = E44;
    }
    
    public void setE45(int E45) {
        this.E45 = E45;
    }
    
    public void setE46(int E46) {
        this.E46 = E46;
    }
    
    public void setE47(int E47) {
        this.E47 = E47;
    }
    
    public void setE48(int E48) {
        this.E48 = E48;
    }
    
    public void setE49(int E49) {
        this.E49 = E49;
    }
    
    public void setE50(int E50) {
        this.E50 = E50;
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
