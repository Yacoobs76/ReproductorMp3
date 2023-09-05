
package reproductormp3pro;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;


public class EditorEtiquetas {
    AudioFile file;
    Tag tag;
    JList<String> jListListaCanciones;
    ArrayList<String> datos;

    public EditorEtiquetas(JLabel jLabelTitulo,JLabel jLabelGenero,JLabel jLabelComentario,JLabel jLabelGrupo,
            JLabel jLabelFecha,JLabel jLabelAlbum,JList<String> jListListaCanciones, ArrayList<String> datos) {
        this.jListListaCanciones=jListListaCanciones;
        this.datos=datos;
        
        labelsEdits(jLabelTitulo,"Titulo: ",FieldKey.TITLE);
        labelsEdits(jLabelGenero,"Genero: ",FieldKey.GENRE);
        labelsEdits(jLabelComentario,"Comentario: ",FieldKey.COMMENT);       
        labelsEdits(jLabelGrupo,"Grupo: ",FieldKey.ARTIST);
        labelsEdits(jLabelFecha,"Fecha: ",FieldKey.YEAR);
        labelsEdits(jLabelAlbum,"Album: ",FieldKey.ALBUM);
        
    }
    
    public void labelsEdits(JLabel label,String texto,FieldKey fieldkey){
        
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                int indice = jListListaCanciones.getSelectedIndex();
                if (indice!=-1){
                    
                    String cancion = datos.get(indice);                   
                    //System.out.println(indice + "Ruta: " +cancion);
                    
                    try {			 
                        file = AudioFileIO.read(new File(cancion));
                    } 
                    catch (CannotReadException | IOException | TagException | ReadOnlyFileException | 
                            InvalidAudioFrameException er) {er.printStackTrace();}
                    
                    tag = file.getTag();
                    //System.out.println(tag);
                    
                    if (tag!=null){
                        
                        String data =JOptionPane.showInputDialog("Introduzca el nuevo "+texto);
                        
                        if (data!=null){
                            try {
				tag.setField(fieldkey, data);
				AudioFileIO.write(file);
                                label.setText(texto + data);
			} 
			catch (CannotWriteException | KeyNotFoundException | FieldDataInvalidException e1) {
				e1.printStackTrace();}
  
                        }                       
                    }
                }          
            }
        });
        
        
        
    }



}
