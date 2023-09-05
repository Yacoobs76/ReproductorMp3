
package reproductormp3pro;

import javax.swing.ImageIcon;
import javax.swing.JLabel;



public class TemasFondo {

    public TemasFondo(String rut,JLabel label) {
        try{
        label.setIcon(new ImageIcon(getClass().getResource("/reproductormp3pro/fondos/"+rut)));
        }catch(NullPointerException e){System.out.println("No Image rut"+ e);}
    }
    
    
    
    
}
