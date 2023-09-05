
package reproductormp3pro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;

public class JLaTexto {

    public JLaTexto(String fuente,String texto,JLabel label,Color color,int tamano) {
        //System.out.println(fuente);
        label.setText(texto);
        label.setForeground(color);
        label.setPreferredSize(new Dimension(300,20));
        label.setFont(new Font(fuente, 1, tamano));
    }
    
}
