
package reproductormp3pro;


import java.io.File;
import java.util.ArrayList;
import javax.swing.JList;



public class AgregarCanciones {
    
	String agregaCanciones[];

    public AgregarCanciones(String agregaCanciones[],File archivo,ArrayList<String> datos,
            JList<String> jListListaCanciones) {
        File RutaDirectorio = archivo;
        //System.out.println(RutaDirectorio);
                
        //Agregamos todos los archivos que contenga la ruta especifica con el metodo .list();
		agregaCanciones = RutaDirectorio.list();

        for (String objeto : agregaCanciones) {
            String extension = objeto.substring(objeto.length()-3,objeto.length());
            if (extension.equals("mp3")) {
                //System.out.println(objeto);
                datos.add(RutaDirectorio+"/"+objeto);
            }              
        }
        agregaCanciones = new String[datos.size()];
        int x=0;
         for (String anade:datos){
             File file = new File(anade);		
             agregaCanciones[x]=file.getName();	
             x++;      
         }   

         jListListaCanciones.setListData(agregaCanciones);
         
         this.agregaCanciones=agregaCanciones;
    }
    
    
    
public String[] agregaGet(){
        return agregaCanciones;
    }
}