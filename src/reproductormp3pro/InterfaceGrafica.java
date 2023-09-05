package reproductormp3pro;
import com.mpatric.mp3agic.ID3v2;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

/*
Programa Echo por Yacoobs Cort. Mart.
Alias Rey Huevos Duros...
21/11/2016 10:00 AM..
Ultima modificación 03-01-2020
Programa java Reproductor de Audio formatos mp3
Consulta libreria Basic Player http://www.it.uu.se/edu/course/homepage/devgui/vt07/material/api/basicplayer3.0/javazoom/jlgui/basicplayer/BasicPlayer.html
Descarga de libreria Basic Player http://www.javazoom.net/jlgui/api.html
Descarga y pagina oficial libreria Jaudiotagger http://www.jthink.net/jaudiotagger/
*/

public class InterfaceGrafica extends javax.swing.JFrame {
    
    //Variables importantes que intervienen en el programa...........
    
    //Instanciamos la Clase Ecualizador Graphics para poder llamarla y poder usar sus metodos
    private Ecualizador_Graphics E_G;
    
    private Map ElementosMap;
    float []eculi = new float [9];
    int E0,E1,E2,E3,E4,E5,E6,E7,E8,E9;
    private Color c = Color.BLACK;
    private final Color c1= new Color(153,255,153);
    private final Color c2= new Color(255,255,204);
    private final String fuente1="Georgia";
    private final String fuente2="Segoe Print";
    private boolean mute=false;
    private boolean bloquear=false;
    private boolean repitaCancion=false;
    private boolean siguiente=false;
    private float balance=0.5f;
    private float volumenM;
    private float volumen=0.8f;
    
    private final BasicPlayer Audio = new BasicPlayer();
    FileNameExtensionFilter filtrado = new FileNameExtensionFilter("Solo Mp3","mp3","jpg");
    
    private String ruta = System.getProperty("user.home") + "/music";

    private final JFileChooser abrirFile  = new JFileChooser(new File(ruta));
    
    private File archivo= null;
    private Tag tag;
    private  AudioFile audiofile = new AudioFile();
    
    private String agregaCanciones[]= new String[10];
    private final ArrayList<String> datos = new ArrayList<>();
    

    public InterfaceGrafica() {
        
        //Llamadas a metodos del programa...
        
        initComponents();
        
        E0=E1=E2=E3=E4=E5=E6=E7=E8=E9=0;
        E_G = new Ecualizador_Graphics(jPanel_Ecualizador);
        SlidersChange();
        basic_playerlistener();
        jlistlistener();
        new EditorEtiquetas(jLabelTitulo,jLabelGenero,jLabelComentario,jLabelGrupo,jLabelFecha,jLabelAlbum,jListListaCanciones,datos);
        
        //setResizable(false); 
          
        new JLaTexto(fuente1, "Ruta: "+ruta, jLabelRuta, Color.WHITE, 15);       
     
        //Importa un mensaje emergente de ayuda a cada boton......
        new ToolTipText(jButtonReproducir, "Pulse para Reproducir");
        new ToolTipText(jButtonPausar, "Pulse para Pausar");
        new ToolTipText(jButtonParar, "Pulse para detener");
        new ToolTipText(jButtonAvance, "Pulse para Avanzar");
        new ToolTipText(jButtonRetroceso, "Pulse Para Retroceder");
        new ToolTipText(jButtonAgregarCanciones, "Agrega los mp3 del directorio");
        new ToolTipText(jButtonRuta, "Establece la ruta por defecto para agregar Canciones");
        new ToolTipText(jButtonDirectorio, "Agrega una sola cancion desde una ruta");
        new ToolTipText(jButtonRepetir, "Activa la repeticion continua de la cancion");
        new ToolTipText(jButtonSiguiente, "Al terminar la cancion reproduce la siguiente de la lista");
        new ToolTipText(jButtonMute, "Activa/DesActiva el silencio Total");
        new ToolTipText(jButtonLimpiarLista, "Selecciona para borrar toda la lista de canciones....");
        
//Importa iconos esteticos en dos posiciones a los botones.........
        new BotonesEscucha(jButtonReproducir,"Reproducir.png","ReproducirP.png");
        new BotonesEscucha(jButtonParar,"Parar.png","PararP.png");
        new BotonesEscucha(jButtonPausar, "Pausar.png", "PausarP.png");
        new BotonesEscucha(jButtonAvance, "Avance.png", "AvanceP.png");
        new BotonesEscucha(jButtonRetroceso, "Retroceso.png", "RetrocesoP.png");
        new BotonesEscucha(jButtonAgregarCanciones, "Agregar.png", "AgregarP.png");
        new BotonesEscucha(jButtonDirectorio, "Directorio.png", "DirectorioP.png");
        new BotonesEscucha(jButtonRuta, "Ruta.png", "RutaP.png");
        new BotonesEscucha(jButtonSiguiente, "Siguiente.png", "SiguienteP.png");
        new BotonesEscucha(jButtonRepetir, "Repetir.png", "RepetirP.png");
        new BotonesEscucha(jButtonMute, "Mute.png", "MuteP.png");
        new BotonesEscucha(jButtonReset, "Reset.png", "ResetP.png");
        new BotonesEscucha(jButtonLimpiarLista, "Borrar.png", "BorrarP.png");
        
        jSliderBalance.setEnabled(false);
        jSliderControlVolumen.setEnabled(false);
        jSliderProgresoMp3.setEnabled(false); 
        
        
        
        setTitle("Version del Programa V.0.2");
    }
    
    @SuppressWarnings("unchecked") 
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuEmergente = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListListaCanciones = new javax.swing.JList<>();
        jButtonReproducir = new javax.swing.JButton();
        jButtonParar = new javax.swing.JButton();
        jButtonPausar = new javax.swing.JButton();
        jButtonRetroceso = new javax.swing.JButton();
        jButtonAvance = new javax.swing.JButton();
        jButtonDirectorio = new javax.swing.JButton();
        jButtonRuta = new javax.swing.JButton();
        jButtonAgregarCanciones = new javax.swing.JButton();
        jButtonSiguiente = new javax.swing.JButton();
        jButtonRepetir = new javax.swing.JButton();
        jButtonMute = new javax.swing.JButton();
        jSliderControlVolumen = new javax.swing.JSlider();
        jSliderBalance = new javax.swing.JSlider();
        jSliderProgresoMp3 = new javax.swing.JSlider();
        jButtonReset = new javax.swing.JButton();
        jLabelRuta = new javax.swing.JLabel();
        jLabelCancion = new javax.swing.JLabel();
        jLabelTexto = new javax.swing.JLabel();
        jLabelTitulo = new javax.swing.JLabel();
        jLabelGenero = new javax.swing.JLabel();
        jLabelTamano = new javax.swing.JLabel();
        jLabelTiempo = new javax.swing.JLabel();
        jLabelGrupo = new javax.swing.JLabel();
        jLabelFecha = new javax.swing.JLabel();
        jLabelComentario = new javax.swing.JLabel();
        jLabelAlbum = new javax.swing.JLabel();
        jLabelFRate = new javax.swing.JLabel();
        jLabelBitrate = new javax.swing.JLabel();
        jLabelPista = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jLabelTranscurrido = new javax.swing.JLabel();
        jLabelVolumen = new javax.swing.JLabel();
        jLabelBalance = new javax.swing.JLabel();
        jLabelFondo = new javax.swing.JLabel();
        jLabelInterface = new javax.swing.JLabel();
        jLabelImagen = new javax.swing.JLabel();
        jButtonLimpiarLista = new javax.swing.JButton();
        jButtonNoEfecto = new javax.swing.JButton();
        jButtonEfecto1 = new javax.swing.JButton();
        jButtonEfecto2 = new javax.swing.JButton();
        jPanel_Ecualizador = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelTemaFondo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuOpciones = new javax.swing.JMenu();
        jMenuTemas = new javax.swing.JMenu();
        jMenuItemTtortuga = new javax.swing.JMenuItem();
        jMenuItemTnieve = new javax.swing.JMenuItem();
        jMenuItemEstrellas = new javax.swing.JMenuItem();
        jMenuItemCuadros = new javax.swing.JMenuItem();
        jMenuItemOjas = new javax.swing.JMenuItem();
        jMenuItemMusica = new javax.swing.JMenuItem();
        jMenuVersion = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jMenuItem2.setText("Eliminar Cancion ");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        MenuEmergente.add(jMenuItem2);

        jMenuItem3.setText("Añadir Cancion");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        MenuEmergente.add(jMenuItem3);

        jMenuItem4.setText("Siguiente Cancion");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        MenuEmergente.add(jMenuItem4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jListListaCanciones.setBackground(new java.awt.Color(102, 102, 102));
        jListListaCanciones.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        jListListaCanciones.setForeground(new java.awt.Color(255, 255, 255));
        jListListaCanciones.setComponentPopupMenu(MenuEmergente);
        jListListaCanciones.setFocusable(false);
        jListListaCanciones.setSelectionBackground(new java.awt.Color(204, 255, 255));
        jScrollPane1.setViewportView(jListListaCanciones);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 390, 270));

        jButtonReproducir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/ReproducirP.png"))); // NOI18N
        jButtonReproducir.setBorderPainted(false);
        jButtonReproducir.setContentAreaFilled(false);
        jButtonReproducir.setFocusable(false);
        jButtonReproducir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReproducirActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonReproducir, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 620, 40, 40));

        jButtonParar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/PararP.png"))); // NOI18N
        jButtonParar.setBorderPainted(false);
        jButtonParar.setContentAreaFilled(false);
        jButtonParar.setFocusable(false);
        jButtonParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPararActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonParar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 620, 40, 40));

        jButtonPausar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/PausarP.png"))); // NOI18N
        jButtonPausar.setBorderPainted(false);
        jButtonPausar.setContentAreaFilled(false);
        jButtonPausar.setFocusable(false);
        jButtonPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPausarActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonPausar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 620, 50, 40));

        jButtonRetroceso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/RetrocesoP.png"))); // NOI18N
        jButtonRetroceso.setBorderPainted(false);
        jButtonRetroceso.setContentAreaFilled(false);
        jButtonRetroceso.setFocusable(false);
        jButtonRetroceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRetrocesoActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonRetroceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 620, 50, 40));

        jButtonAvance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/AvanceP.png"))); // NOI18N
        jButtonAvance.setBorderPainted(false);
        jButtonAvance.setContentAreaFilled(false);
        jButtonAvance.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonAvance.setFocusable(false);
        jButtonAvance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAvanceActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonAvance, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 620, 50, 40));

        jButtonDirectorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/DirectorioP.png"))); // NOI18N
        jButtonDirectorio.setBorderPainted(false);
        jButtonDirectorio.setContentAreaFilled(false);
        jButtonDirectorio.setFocusable(false);
        jButtonDirectorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDirectorioActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonDirectorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 660, 50, 50));

        jButtonRuta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/RutaP.png"))); // NOI18N
        jButtonRuta.setBorderPainted(false);
        jButtonRuta.setContentAreaFilled(false);
        jButtonRuta.setFocusable(false);
        jButtonRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRutaActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonRuta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 660, 50, 50));

        jButtonAgregarCanciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/AgregarP.png"))); // NOI18N
        jButtonAgregarCanciones.setBorderPainted(false);
        jButtonAgregarCanciones.setContentAreaFilled(false);
        jButtonAgregarCanciones.setFocusable(false);
        jButtonAgregarCanciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarCancionesActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonAgregarCanciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 660, 50, 50));

        jButtonSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/SiguienteP.png"))); // NOI18N
        jButtonSiguiente.setBorderPainted(false);
        jButtonSiguiente.setContentAreaFilled(false);
        jButtonSiguiente.setFocusable(false);
        jButtonSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 620, 40, 40));

        jButtonRepetir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/RepetirP.png"))); // NOI18N
        jButtonRepetir.setBorderPainted(false);
        jButtonRepetir.setContentAreaFilled(false);
        jButtonRepetir.setFocusable(false);
        jButtonRepetir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRepetirActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonRepetir, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 620, 50, 40));

        jButtonMute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/MuteP.png"))); // NOI18N
        jButtonMute.setBorderPainted(false);
        jButtonMute.setContentAreaFilled(false);
        jButtonMute.setFocusable(false);
        jButtonMute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMuteActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonMute, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 670, 40, 40));

        jSliderControlVolumen.setBackground(new java.awt.Color(255, 255, 255));
        jSliderControlVolumen.setForeground(new java.awt.Color(255, 255, 255));
        jSliderControlVolumen.setValue(80);
        jSliderControlVolumen.setEnabled(false);
        jSliderControlVolumen.setOpaque(false);
        jPanel1.add(jSliderControlVolumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 540, 320, 20));

        jSliderBalance.setMinimum(-100);
        jSliderBalance.setValue(0);
        jSliderBalance.setOpaque(false);
        jPanel1.add(jSliderBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 570, 320, -1));

        jSliderProgresoMp3.setBackground(new java.awt.Color(255, 255, 255));
        jSliderProgresoMp3.setForeground(new java.awt.Color(255, 255, 255));
        jSliderProgresoMp3.setValue(0);
        jSliderProgresoMp3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSliderProgresoMp3.setOpaque(false);
        jPanel1.add(jSliderProgresoMp3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 740, 400, 20));

        jButtonReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/ResetP.png"))); // NOI18N
        jButtonReset.setBorderPainted(false);
        jButtonReset.setContentAreaFilled(false);
        jButtonReset.setFocusable(false);
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 670, 40, 40));

        jLabelRuta.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelRuta.setForeground(new java.awt.Color(255, 255, 255));
        jLabelRuta.setText("Ruta:");
        jPanel1.add(jLabelRuta, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, -1, -1));

        jLabelCancion.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelCancion.setText("Cancion:");
        jPanel1.add(jLabelCancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, -1, -1));

        jLabelTexto.setFont(new java.awt.Font("Segoe Print", 1, 15)); // NOI18N
        jLabelTexto.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTexto.setText("Etiquetas Id3 Mp3....");
        jPanel1.add(jLabelTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, -1, -1));

        jLabelTitulo.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelTitulo.setText("Titulo:");
        jPanel1.add(jLabelTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, -1, -1));

        jLabelGenero.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelGenero.setText("Genero:");
        jPanel1.add(jLabelGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, -1, -1));

        jLabelTamano.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelTamano.setText("Peso:");
        jPanel1.add(jLabelTamano, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 260, -1, -1));

        jLabelTiempo.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelTiempo.setForeground(new java.awt.Color(255, 255, 204));
        jLabelTiempo.setText("Duracion:");
        jPanel1.add(jLabelTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 350, -1, -1));
        jLabelTiempo.getAccessibleContext().setAccessibleName("jLabelTiempo");

        jLabelGrupo.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelGrupo.setText("Grupo:");
        jPanel1.add(jLabelGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 170, -1, -1));

        jLabelFecha.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelFecha.setText("Fecha:");
        jPanel1.add(jLabelFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 190, -1, -1));

        jLabelComentario.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelComentario.setText("Comentario:");
        jPanel1.add(jLabelComentario, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, -1, -1));

        jLabelAlbum.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelAlbum.setText("Album:");
        jPanel1.add(jLabelAlbum, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, -1, -1));

        jLabelFRate.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelFRate.setText("Velocidad Muestreo:");
        jPanel1.add(jLabelFRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 280, -1, -1));

        jLabelBitrate.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelBitrate.setText("Tasa de bits:");
        jPanel1.add(jLabelBitrate, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, -1, -1));

        jLabelPista.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelPista.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPista.setText("Pista:");
        jPanel1.add(jLabelPista, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 230, 80, -1));

        jLabelTotal.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelTotal.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotal.setText("Total:");
        jPanel1.add(jLabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 230, -1, -1));

        jLabelTranscurrido.setFont(new java.awt.Font("Georgia", 1, 15)); // NOI18N
        jLabelTranscurrido.setForeground(new java.awt.Color(255, 255, 204));
        jLabelTranscurrido.setText("Transcurrido:");
        jPanel1.add(jLabelTranscurrido, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 330, -1, -1));

        jLabelVolumen.setFont(new java.awt.Font("Segoe Print", 1, 15)); // NOI18N
        jLabelVolumen.setForeground(new java.awt.Color(153, 255, 153));
        jLabelVolumen.setText("Volumen: 80");
        jPanel1.add(jLabelVolumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 380, -1, -1));

        jLabelBalance.setFont(new java.awt.Font("Segoe Print", 1, 15)); // NOI18N
        jLabelBalance.setForeground(new java.awt.Color(153, 255, 153));
        jLabelBalance.setText("Balance: 0");
        jPanel1.add(jLabelBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 380, -1, -1));

        jLabelFondo.setFont(new java.awt.Font("Georgia", 0, 11)); // NOI18N
        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/fondos/temaAzul.png"))); // NOI18N
        jPanel1.add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, -1, -1));

        jLabelInterface.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/fondos/interface.png"))); // NOI18N
        jPanel1.add(jLabelInterface, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 610, -1, -1));
        jPanel1.add(jLabelImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 390, 270));

        jButtonLimpiarLista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/botones/BorrarP.png"))); // NOI18N
        jButtonLimpiarLista.setBorderPainted(false);
        jButtonLimpiarLista.setContentAreaFilled(false);
        jButtonLimpiarLista.setFocusable(false);
        jButtonLimpiarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpiarListaActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonLimpiarLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 50, 40));

        jButtonNoEfecto.setBackground(new java.awt.Color(0, 0, 0));
        jButtonNoEfecto.setForeground(new java.awt.Color(255, 255, 255));
        jButtonNoEfecto.setText("NoEfecto");
        jButtonNoEfecto.setContentAreaFilled(false);
        jButtonNoEfecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNoEfectoActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonNoEfecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 40, -1, -1));

        jButtonEfecto1.setBackground(new java.awt.Color(0, 0, 0));
        jButtonEfecto1.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEfecto1.setText("Efecto1");
        jButtonEfecto1.setContentAreaFilled(false);
        jButtonEfecto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEfecto1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonEfecto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 80, -1, -1));

        jButtonEfecto2.setBackground(new java.awt.Color(0, 0, 0));
        jButtonEfecto2.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEfecto2.setText("Efecto2");
        jButtonEfecto2.setContentAreaFilled(false);
        jButtonEfecto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEfecto2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonEfecto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 120, -1, -1));

        jPanel_Ecualizador.setMaximumSize(new java.awt.Dimension(486, 142));
        jPanel_Ecualizador.setOpaque(false);
        jPanel_Ecualizador.setPreferredSize(new java.awt.Dimension(486, 142));
        jPanel_Ecualizador.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel_Ecualizador, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 610, 480, 160));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 610, 420, 160));

        jLabelTemaFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reproductormp3pro/fondos/Tortuga.jpg"))); // NOI18N
        jPanel1.add(jLabelTemaFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jMenuOpciones.setText("Opciones");

        jMenuTemas.setText("Temas Fondos");

        jMenuItemTtortuga.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemTtortuga.setText("TemaTortuga");
        jMenuItemTtortuga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTtortugaActionPerformed(evt);
            }
        });
        jMenuTemas.add(jMenuItemTtortuga);

        jMenuItemTnieve.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemTnieve.setText("Tema Nieve");
        jMenuItemTnieve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTnieveActionPerformed(evt);
            }
        });
        jMenuTemas.add(jMenuItemTnieve);

        jMenuItemEstrellas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemEstrellas.setText("Tema Estrellas");
        jMenuItemEstrellas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEstrellasActionPerformed(evt);
            }
        });
        jMenuTemas.add(jMenuItemEstrellas);

        jMenuItemCuadros.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemCuadros.setText("Tema Cuadros");
        jMenuItemCuadros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCuadrosActionPerformed(evt);
            }
        });
        jMenuTemas.add(jMenuItemCuadros);

        jMenuItemOjas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOjas.setText("Tema Ojas");
        jMenuItemOjas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOjasActionPerformed(evt);
            }
        });
        jMenuTemas.add(jMenuItemOjas);

        jMenuItemMusica.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemMusica.setText("Tema Musica");
        jMenuItemMusica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMusicaActionPerformed(evt);
            }
        });
        jMenuTemas.add(jMenuItemMusica);

        jMenuOpciones.add(jMenuTemas);

        jMenuBar1.add(jMenuOpciones);

        jMenuVersion.setText("Version");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Informacion");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuVersion.add(jMenuItem1);

        jMenuBar1.add(jMenuVersion);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonReproducirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReproducirActionPerformed
      
       int indice = jListListaCanciones.getSelectedIndex();
       
       if (archivo!=null & Audio.getStatus()!=0 & indice!=-1){
           //System.out.println(Audio.getSleepTime());
           String ReproduceCancion = datos.get(indice);
           //System.out.println(ReproduceCancion);
           archivo = new File(ReproduceCancion);
           jSliderBalance.setEnabled(true);
           jSliderControlVolumen.setEnabled(true);
           jSliderProgresoMp3.setEnabled(true);
           
           CaratulaCancion(archivo.toString());
           try {
               
               Audio.open(archivo);
               Audio.play();
               Audio.setGain(volumen);
               Audio.setPan(balance);
               int pista = jListListaCanciones.getAnchorSelectionIndex()+1;
               new JLaTexto(fuente1, "Pista: "+ pista, jLabelPista, Color.WHITE, 15);
           
           } catch (BasicPlayerException ex) {
               JOptionPane.showMessageDialog(this, ex + "\n  la informacion de imagen de la cancion excede el pixelado admitido por la libreria..."
                                                      + "\n se recomienda editar la imagen interna del mp3 con el programa Mp3Tag... "
                                                      ,"Error en las Id3Tag",1);
               

               try {
                   
                   Audio.stop();
                   
                   String RutaCancion = datos.get(indice);
                   AudioFile file = AudioFileIO.read(new File(RutaCancion));
                   Tag tager = file.getTag();
                   tager.deleteField(FieldKey.COVER_ART);
                   tager.deleteArtworkField();
                   AudioFileIO.write(file);
                   InputStream inputStream = new FileInputStream(ruta);
                   OutputStream outputStream =  new FileOutputStream("Audio.mp3");
                   
                   byte[] buf = new byte[1024];
                   int len;

                   while ((len = inputStream.read(buf)) > 0) {
                     outputStream.write(buf, 0, len);
                    }
                   inputStream.close();
                   outputStream.close();
                   
              
                    System.out.println();
                    System.out.println("El contenido de caratula mp3 es de masiado grande....Borrela!!!"); 
                   
                } 
               catch (IOException ex1) {System.out.println(ex1);} 
               catch (TagException ex1) {System.out.println(ex1);} 
               catch (ReadOnlyFileException ex1) {System.out.println(ex1);} 
               catch (InvalidAudioFrameException ex1) {System.out.println(ex1);} 
               catch (CannotWriteException ex1) {System.out.println(ex1);} 
               catch (CannotReadException ex1) {System.out.println(ex1);} 
               catch (BasicPlayerException ex1) {System.out.println(ex1);}  
               
               
               
       }         
    }//GEN-LAST:event_jButtonReproducirActionPerformed
    
    }
    
    private void jButtonPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPararActionPerformed
        
        if (Audio.getStatus()==0){
            try {
                
                bloquear=true;
                Audio.stop();
                bloquear=false;
  
            } catch (BasicPlayerException ex) {           }
            
            Reducir();
        }
    }//GEN-LAST:event_jButtonPararActionPerformed

    private void jButtonPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPausarActionPerformed
        
        if (Audio.getStatus()==1){
            try {
                Audio.resume();
            } catch (BasicPlayerException ex) {
                Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
            }       
        }else  if (Audio.getStatus()==0){
            try {
                Audio.pause();
                Reducir();
            } catch (BasicPlayerException ex) {
                Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_jButtonPausarActionPerformed

    private void jButtonAvanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAvanceActionPerformed
   
        if (jListListaCanciones.getSelectedIndex()+1!=agregaCanciones.length){
            bloquear=true;
            Comprovacion(1);
            bloquear=false;
        }
          
    }//GEN-LAST:event_jButtonAvanceActionPerformed

    private void jButtonRetrocesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRetrocesoActionPerformed
         
        if (jListListaCanciones.getSelectedIndex()!=0){
            bloquear=true;
            Comprovacion(-1);
            bloquear=false;
        }
        
    }//GEN-LAST:event_jButtonRetrocesoActionPerformed

    private void jButtonAgregarCancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarCancionesActionPerformed

        
        
        if (ruta!=null & Audio.getStatus()!=1){
            archivo = new File(ruta);
            
           AgregarCanciones agre = new AgregarCanciones(agregaCanciones, archivo, datos, jListListaCanciones);         
           new JLaTexto(fuente1, "Total: "+datos.size(), jLabelTotal, Color.WHITE, 15);
           agregaCanciones= agre.agregaGet();
           if (archivo!=null){
              jListListaCanciones.setSelectedIndex(0);
              archivo = new File(datos.get(0));
                  
           }                    
        }  
         
    }//GEN-LAST:event_jButtonAgregarCancionesActionPerformed

    private void jButtonDirectorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDirectorioActionPerformed

        
        
        abrirFile.setDialogTitle("Ruta Absoluta Busqueda..");
        abrirFile.setFileFilter(filtrado);
        abrirFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        
        if (abrirFile.showOpenDialog(jPanel1)==0){
            File file = new File(""+abrirFile.getSelectedFile());        
            
            String argumento = file.getName();//Obtenemos el nombre del archivo...
            argumento=argumento.substring(argumento.length()-3,argumento.length());//Obtenemos los tres ultimos caracteres de la cadena de nombre...
            
            //Evaluamos la condicion de que el archivo al que hacemos referencia exista....
            //Evaluamos la condicion de que el archivo que hacemos referencia sea de extension mp3...
            if (file.exists()& argumento.equals("mp3")){
                
                archivo = abrirFile.getSelectedFile();//Obtenemos la ruta del archivo..
                                 
                try {
                    audiofile = AudioFileIO.read(archivo);
                    tag =  audiofile.getTag();
     
                } catch (CannotReadException | IOException | TagException | 
                        ReadOnlyFileException | InvalidAudioFrameException ex) {
                    System.out.println("Error no hay archivo el argumento es "+archivo);
                }                     
                ruta = abrirFile.getCurrentDirectory().toString();
                new JLaTexto(fuente1, "Ruta: "+ ruta,jLabelRuta , Color.WHITE, 15);
              
                datos.add(archivo.toString());
                
                agregaCanciones = new String[datos.size()];
              
                int x=0;
                for (String cancion:datos){
                    File introduce = new File(cancion);
                    agregaCanciones[x] = introduce.getName();
                    x++;
                }
                jListListaCanciones.setListData(agregaCanciones);
                JLaEtiquetas(archivo);
            }   
            
        }   
        
    }//GEN-LAST:event_jButtonDirectorioActionPerformed

    private void jButtonRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRutaActionPerformed
        
        
        abrirFile.setDialogTitle("Directorio Agregar Canciones");
        abrirFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//Accederemos solamente a directorios.
        if (abrirFile.showOpenDialog(jPanel1)==0){
            ruta = abrirFile.getSelectedFile().toString();
            new JLaTexto(fuente1, "Ruta: "+ ruta,jLabelRuta , Color.WHITE, 15);            
        }
    }//GEN-LAST:event_jButtonRutaActionPerformed

    private void jButtonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSiguienteActionPerformed
    
        if (!repitaCancion){
        
            if(siguiente){
                new BotonesEscucha(jButtonSiguiente, "Siguiente.png", "SiguienteP.png");
                jButtonSiguiente.setIcon(new ImageIcon(getClass().getResource("botones/Siguiente.png")));
                siguiente=false;
                System.out.println(siguiente);

            }else {
                new BotonesEscucha(jButtonSiguiente,"SiguientePulsando.png","SiguientePulsandoP.png");
                jButtonSiguiente.setIcon(new ImageIcon(getClass().getResource("botones/SiguientePulsando.png")));
                siguiente=true;
                System.out.println(siguiente);
            }
        }
    }//GEN-LAST:event_jButtonSiguienteActionPerformed

    private void jButtonRepetirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRepetirActionPerformed
        
        if (!siguiente){
            
            if(repitaCancion){
                new BotonesEscucha(jButtonRepetir, "Repetir.png", "RepetirP.png");
                jButtonRepetir.setIcon(new ImageIcon(getClass().getResource("botones/Repetir.png")));
                repitaCancion=false;
                System.out.println(repitaCancion);
            
             }else {
                new BotonesEscucha(jButtonRepetir,"RepetirPulsando.png","RepetirPulsandoP.png");
                jButtonRepetir.setIcon(new ImageIcon(getClass().getResource("botones/RepetirPulsando.png")));
                repitaCancion=true;
                System.out.println(repitaCancion);
            }
        }
        
        
    }//GEN-LAST:event_jButtonRepetirActionPerformed

    private void jButtonMuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMuteActionPerformed
        
        if (archivo!=null){
           if(mute){
               volumen = volumenM;
               jSliderControlVolumen.setValue((int)(volumen*100)); 
               new BotonesEscucha(jButtonMute, "Mute.png", "MuteP.png");
               jButtonMute.setIcon(new ImageIcon(getClass().getResource("botones/Mute.png")));            
               mute=false;
               System.out.println(mute);
            
             }else {
               volumenM=volumen;
               volumen=0f;
               jSliderControlVolumen.setValue(0); 
               new BotonesEscucha(jButtonMute,"MutePulsando.png","MutePulsandoP.png");
               jButtonMute.setIcon(new ImageIcon(getClass().getResource("botones/MutePulsando.png")));
               mute=true;
               System.out.println(mute);
            }
            
            
        }   
    }//GEN-LAST:event_jButtonMuteActionPerformed

    
    
    
    
    private void jMenuItemTtortugaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTtortugaActionPerformed
        // TODO add your handling code here:
        //Las imagenes deveran de tener un tamaño de 1020 * 820......  
        new TemasFondo("Tortuga.jpg", jLabelTemaFondo);
    }//GEN-LAST:event_jMenuItemTtortugaActionPerformed

    private void jMenuItemTnieveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTnieveActionPerformed
        // TODO add your handling code here:
        new TemasFondo("CoposNieve.jpg", jLabelTemaFondo);
    }//GEN-LAST:event_jMenuItemTnieveActionPerformed

    private void jMenuItemEstrellasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEstrellasActionPerformed
        // TODO add your handling code here:
        new TemasFondo("temaEstrellas.jpg", jLabelTemaFondo);
        
    }//GEN-LAST:event_jMenuItemEstrellasActionPerformed

    private void jMenuItemCuadrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCuadrosActionPerformed
        // TODO add your handling code here:
        new TemasFondo("Cuadrados.jpg", jLabelTemaFondo);
    }//GEN-LAST:event_jMenuItemCuadrosActionPerformed

    private void jMenuItemOjasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOjasActionPerformed
        // TODO add your handling code here:
        new TemasFondo("OjasColores.jpg", jLabelTemaFondo);
    }//GEN-LAST:event_jMenuItemOjasActionPerformed

    
    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        // TODO add your handling code here:
        if (Audio.getStatus()!=-1){
            System.out.println("OK");
            jSliderBalance.setValue(0);
            jLabelBalance.setText("Balance: 0");
        }
        
    }//GEN-LAST:event_jButtonResetActionPerformed

    
    private void jMenuItemMusicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMusicaActionPerformed
        // TODO add your handling code here:
        new TemasFondo("musica.png", jLabelTemaFondo);
        
    }//GEN-LAST:event_jMenuItemMusicaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        new Informacion();
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButtonLimpiarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarListaActionPerformed
        // TODO add your handling code here:
        datos.clear();
        agregaCanciones = new String[datos.size()];
        jListListaCanciones.setListData(agregaCanciones);
       
        
    }//GEN-LAST:event_jButtonLimpiarListaActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        int indice = jListListaCanciones.getSelectedIndex();
        
        if (agregaCanciones[0]!=null && indice!=-1 ){
            
            datos.remove(indice);
            agregaCanciones = new String[datos.size()];
            int x=0;
             for (String anade:datos){
                 File file = new File(anade);		
                 agregaCanciones[x]=file.getName();	
                 x++;      
             }      
             jListListaCanciones.setListData(agregaCanciones);
            
        }
     
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
       jButtonDirectorio.doClick();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        
        jButtonAvance.doClick();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButtonNoEfectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNoEfectoActionPerformed
        if (jSliderControlVolumen.isEnabled()){
            E0 = 0; E1 = 0;E2 = 0;E3 = 0;E4 = 0;E5 = 0;E6 = 0;E7 = 0; E8 = 0;E9 = 0;           
        }      
    }//GEN-LAST:event_jButtonNoEfectoActionPerformed

    private void jButtonEfecto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEfecto1ActionPerformed
        if (jSliderControlVolumen.isEnabled()){
            E0 = 70;E1 = 80;E2 = 80;E3 = 80;E4 = 80;E5 = 60;E6 = 60;E7 = 60;E8 = 60;E9 = 60;            
        }
    }//GEN-LAST:event_jButtonEfecto1ActionPerformed

    private void jButtonEfecto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEfecto2ActionPerformed
        if (jSliderControlVolumen.isEnabled()){
            E0 = -100;E1 = -100;E2 = 30;E3 = 30;E4 = 0;E5 = 0;E6 = 0;E7 = 0;E8 = 0;E9 = 0;            
        }
    }//GEN-LAST:event_jButtonEfecto2ActionPerformed

    
    //Metodos Para el programa-----------------------------------------------------------------
    
    public void jlistlistener(){
        
        jListListaCanciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                //System.out.println(jListListaCanciones.getAnchorSelectionIndex());
                
                int indice = jListListaCanciones.getSelectedIndex();

                 if (indice!=-1){
                     
                     String ReproduceCancion = datos.get(indice);
       
                    File file = new File(ReproduceCancion);
                    
                    try {
                        audiofile = AudioFileIO.read(file); 
                    
                        tag = audiofile.getTag();

                    } catch (IllegalAccessError | CannotReadException | IOException | TagException | NullPointerException |
                        ReadOnlyFileException | InvalidAudioFrameException ex) {System.out.println("Error Numero 1:  " + ex); }
                    
                    //Hace la llamada al metodo encargado de rellenar los JLbels....                   
                    JLaEtiquetas(file);
                    
                    CaratulaCancion(file.toString());
                    
                    if (e.getClickCount()==2){
                        try {
                            bloquear=true;
                            Audio.stop();
                            bloquear=false;
                        } catch (BasicPlayerException ex) {System.out.println("Error Numero 2:  " + ex);}
                        
                        jButtonReproducir.doClick();
                        
                        if (Audio.getStatus()==2){
                            
                            jButtonReproducir.doClick();
                        }
                    }
                 }
            } 
        });
    }
    
    public void CaratulaCancion(String rut){    //Este metodo se encarga de leer las ediquetas del mp3 y obtener la imagen de la cancion mp3.
        Image img = null;        
        try { 
            Mp3File Mp3A = new Mp3File(rut);
            if (Mp3A.hasId3v2Tag()){
                ID3v2 idTag = Mp3A.getId3v2Tag();
                //idTag.clearFrameSet(ID3v24Tag.ID_IMAGE);  
                byte[] datosImagen = idTag.getAlbumImage();               
                img = ImageIO.read(new ByteArrayInputStream(datosImagen));
            }
            else {  //En el caso de que el mp3 no contenga imagen (establecemos una imagen por defecto..)
                img = ImageIO.read(getClass().getResource("fondos/NoMusic.png"));					        
            }
        } 
        catch (IllegalArgumentException | IOException ex1){System.out.printf("No Expecificas ruta"); }
        catch (NullPointerException e1) {System.out.println("No hay caratula en el mp3");
         
        try {
            //En el caso de que el mp3 no contenga imagen y lance una excepcion...(establecemos una imagen por defecto..)
            img = ImageIO.read(getClass().getResource("fondos/NoMusic.png"));
        } 
        catch (IOException e) {}
        } 
        catch (UnsupportedTagException | InvalidDataException e) {}
        //Este codigo nos ayudara a reajustar la imagen a nuestro JLabel para que este se redimensione.....
        int ancho = img.getWidth(null);
        int alto = img.getHeight(null);       
        if (ancho>390){
            ancho=390;
        } 
        if (alto>270){
            alto=270;
        }
        
        img = img.getScaledInstance(ancho, alto,0);
        jLabelImagen.setIcon(new ImageIcon(img));
        

    }
    
    public void Reducir(){      //Este metodo se encarga de crear un efecto de cerrado de sonido (las barras del equalizador disminuyen progresivamente)
        Thread cerrar = new Thread(new Runnable() {
                @Override
                public void run() {
                    
                    for (int x=130;x>0;x--){
                    
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException ex) {    }
                        
                        E_G.setE1(x);                 
                        E_G.setE2(x);                   
                        E_G.setE3(x);  
                        E_G.setE4(x); 
                        E_G.setE5(x); 
                        E_G.setE6(x); 
                        E_G.setE7(x); 
                        E_G.setE8(x); 
                        E_G.setE9(x); 
                        E_G.setE10(x); 
                        E_G.setE11(x); 
                        E_G.setE12(x);
                        E_G.setE13(x); 
                        E_G.setE14(x); 
                        E_G.setE15(x);
                        E_G.setE16(x);
                        E_G.setE17(x);
                        E_G.setE18(x);
                        E_G.setE19(x);
                        E_G.setE20(x);
                        E_G.setE21(x);
                        E_G.setE22(x);
                        E_G.setE23(x);
                        E_G.setE24(x);
                        E_G.setE25(x);
                        E_G.setE26(x);
                        E_G.setE27(x);
                        E_G.setE28(x);
                        E_G.setE29(x);
                        E_G.setE30(x);
                        E_G.setE31(x);
                        E_G.setE32(x);
                        E_G.setE33(x);
                        E_G.setE34(x);
                        E_G.setE35(x);
                        E_G.setE36(x);
                        E_G.setE37(x);
                        E_G.setE38(x);
                        E_G.setE39(x);
                        E_G.setE40(x);
                        E_G.setE41(x);
                        E_G.setE42(x);
                        E_G.setE43(x);
                        //E_G.setE44(x);
                        //E_G.setE45(x);
                        //E_G.setE46(x);
                        //E_G.setE47(x);
                        //E_G.setE48(x);
                        //E_G.setE49(x);
                        //E_G.setE50(x);
                        
                        
                        E_G.Repintado();
                }
                
                }
            });
            
            cerrar.start();
    }
    
    public void CalculoSecundero(String milisegundos,String texto,JLabel label){
        try{
            float horas,mint;
            double milis = Double.parseDouble(milisegundos);
            double segundostotal =  milis/1000000;                   //Almacenamos y pasamos a segundos los microsegundos obtenenidos.

            horas = (int)segundostotal/3600;                        //Conversion de segundostotal a horas.
            mint = (int)segundostotal/60-horas *60;                 //Conversion de segundostotal a minutos.
            segundostotal= segundostotal-mint*60-horas*3600;        //Conversion de segundostotal a segundos.

            String secundero = (int)horas + ":" +(int) mint +":"+ (int)segundostotal;//Creamos una variable String para almacenar el tiempo total horas, minutos, segundos.              

            new JLaTexto(fuente1, texto+secundero, label, c2, 15);
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
    }
    
    public void basic_playerlistener(){
        Audio.addBasicPlayerListener(new BasicPlayerListener() {
            @Override//Este metodo se cumple cuando abrimos la cancion...
            public void opened(Object o, Map map) {
               
               
               //LLamamos al metodo para que nos imprima el tiempo de duracion de la cancion....
               CalculoSecundero(map.get("duration").toString(), "Duracion: ", jLabelTiempo);
               
               new JLaTexto(fuente1, "Tasa de bits: "+map.get("bitrate"), jLabelBitrate, c, 15);
               new JLaTexto(fuente1, "Velocidad Muestreo: "+map.get("mp3.frequency.hz"), jLabelFRate, c, 15);

               jSliderProgresoMp3.setMaximum(Integer.parseInt(map.get("mp3.length.bytes").toString()));
               jSliderProgresoMp3.setMinimum(0);
            }

            @Override//Este metodo se cumple cuando la cancion esta en progreso....
            public void progress(int i, long l, byte[] bytes, Map propiedades) {				
                 
                ElementosMap= propiedades;
                
                eculi = (float[]) ElementosMap.get("mp3.equalizer");
                
                eculi[0] = E0;
                eculi[1] = E1;
                eculi[2] = E2;
                eculi[3] = E3;
                eculi[4] = E4;
                eculi[5] = E5;
                eculi[6] = E6;
                eculi[7] = E7;
                eculi[8] = E8;
                eculi[9] = E9;
                
                //Hay que añadir estas lineas de codigo para el equalizador funcione
                //Aqui hacemos la llamada a la clase Ecualizador_Ghapics que instanciamos previamente
                try{
                    E_G.setE1(bytes[0]);
                    E_G.setE2(bytes[106]);
                    E_G.setE3(bytes[212]);
                    E_G.setE4(bytes[318]);
                    E_G.setE5(bytes[424]);
                    E_G.setE6(bytes[530]);
                    E_G.setE7(bytes[636]);
                    E_G.setE8(bytes[742]);
                    E_G.setE9(bytes[848]);
                    E_G.setE10(bytes[954]);
                    E_G.setE11(bytes[1060]);
                    E_G.setE12(bytes[1066]);
                    E_G.setE13(bytes[1272]);
                    E_G.setE14(bytes[1378]);
                    E_G.setE15(bytes[1484]);
                    E_G.setE16(bytes[1590]);
                    E_G.setE17(bytes[1696]);
                    E_G.setE18(bytes[1802]);
                    E_G.setE19(bytes[1908]);
                    E_G.setE20(bytes[2014]);
                    E_G.setE21(bytes[2120]);
                    E_G.setE22(bytes[2226]);
                    E_G.setE23(bytes[2332]);
                    E_G.setE24(bytes[2438]);
                    E_G.setE25(bytes[2544]);
                    E_G.setE26(bytes[2650]);
                    E_G.setE27(bytes[2756]);
                    E_G.setE28(bytes[2862]);
                    E_G.setE29(bytes[2968]);
                    E_G.setE30(bytes[3074]);
                    E_G.setE31(bytes[3180]);
                    E_G.setE32(bytes[3286]);
                    E_G.setE33(bytes[3392]);
                    E_G.setE34(bytes[3498]);
                    E_G.setE35(bytes[3604]);
                    E_G.setE36(bytes[3710]);
                    E_G.setE37(bytes[3816]);
                    E_G.setE38(bytes[3922]);
                    E_G.setE39(bytes[4028]);
                    E_G.setE40(bytes[4134]);
                    E_G.setE41(bytes[4240]);
                    E_G.setE42(bytes[4346]);
                    E_G.setE43(bytes[4600]);

                    E_G.Repintado();
                }catch(ArrayIndexOutOfBoundsException e){System.out.println("Error"+e);}
                
                //LLamamos al este metodo que nos calcula el tiempo trancurrido...
                CalculoSecundero(propiedades.get("mp3.position.microseconds").toString(), "Transcurrido: ", jLabelTranscurrido);

                Object bytesTranscurrido =  propiedades.get("mp3.position.byte");
                bytesTranscurrido= Integer.parseInt(bytesTranscurrido.toString());               
                jSliderProgresoMp3.setValue((int)bytesTranscurrido);                     
            }

            @Override
            public void stateUpdated(BasicPlayerEvent bpe) {
                    
                if (!bloquear){
                    if (Audio.getStatus()==2 & repitaCancion){
                        jButtonReproducir.doClick();
                    }
                    if (jListListaCanciones.getSelectedIndex()+1!=agregaCanciones.length){
                        if (Audio.getStatus()==2 & siguiente){
                            int pista = jListListaCanciones.getAnchorSelectionIndex();                            
                            jListListaCanciones.setSelectedIndex(pista+1);
                            repaint();
                            jButtonReproducir.doClick();
                        }
                    }
                }                
            }

            @Override
            public void setController(BasicController bc) {
                
            }
        });
        
    }
    
    public void SlidersChange(){
        
        jSliderProgresoMp3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    Audio.pause();
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    jSliderProgresoMp3.setValue(jSliderProgresoMp3.getValue());
                    Audio.resume();
                    Audio.seek(jSliderProgresoMp3.getValue());
                    Audio.setGain(volumen);
                    Audio.setPan(balance);
                    
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex); }
            }
        });
        
        jSliderControlVolumen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
                if (Audio.getStatus()!=-1){
                   try {
                       volumen = jSliderControlVolumen.getValue()/100f;
                       Audio.setGain(volumen);
                       new JLaTexto(fuente2, "Volum: " + volumen,jLabelVolumen , c1, 15);
                    
                   } catch (BasicPlayerException ex) {
                    Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex); }
                }
            } 
        });
        
        jSliderBalance.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
                if (Audio.getStatus()!=-1){
                    
                    try {
                        balance = jSliderBalance.getValue()/100f;
                        Audio.setPan(balance);
                        new JLaTexto(fuente2, "Balance: "+balance, jLabelBalance, c1, 15);
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);}
                }
            }
        });
    }
       
    public void Comprovacion(int opera){
        //Audio.getStatus() valor -1 al empezar... 
        //valor 0 cuando se reproduce..
        //valor 1 cuando esta pausado..
        //valor 2 cuando detienes la cancion...
        
        int indice = jListListaCanciones.getSelectedIndex();        
        if (archivo!=null & indice!=-1 & Audio.getStatus()==0){
            //El metodo getAnchotSelectionIndex obtenemos el numero de posicion en el que se encuentra el JList...
            int pista = jListListaCanciones.getAnchorSelectionIndex();
            jListListaCanciones.setSelectedIndex(pista+opera);
            try {
                Audio.stop();
            } catch (BasicPlayerException ex) {
                Logger.getLogger(InterfaceGrafica.class.getName()).log(Level.SEVERE, null, ex);
            }
            jButtonReproducir.doClick();
        }
    }
    
    public void JLaEtiquetas(File file){
        
            new JLaTexto(fuente1, "Total: "+datos.size(), jLabelTotal, Color.WHITE, 15);
            new JLaTexto(fuente1, "Cancion: "+file.getName(), jLabelCancion, c, 15);
            new JLaTexto(fuente1, "Peso: "+file.length()+" bits",jLabelTamano,c, 15);
          try {  
            new JLaTexto(fuente1, "Titulo: "+tag.getFirst(FieldKey.TITLE), jLabelTitulo, c, 15);     
            new JLaTexto(fuente1, "Genero: "+tag.getFirst(FieldKey.GENRE), jLabelGenero, c, 15);
            new JLaTexto(fuente1, "Grupo: "+tag.getFirst(FieldKey.ARTIST), jLabelGrupo, c, 15);
            new JLaTexto(fuente1, "Fecha: "+tag.getFirst(FieldKey.YEAR), jLabelFecha, c, 15);
            new JLaTexto(fuente1, "Album: "+tag.getFirst(FieldKey.ALBUM), jLabelAlbum, c, 15);
            new JLaTexto(fuente1, "Comentario: "+tag.getFirst(FieldKey.COMMENT), jLabelComentario, c, 15);
            
        } catch (NullPointerException e) { System.out.print("Error en las etiquetas, el tag es igual a "+ tag);
        c = new Color(58,68,72);
        new JLaTexto(fuente1, "Titulo: ", jLabelTitulo, c, 15);     
        new JLaTexto(fuente1, "Genero: ", jLabelGenero, c, 15);
        new JLaTexto(fuente1, "Grupo: ", jLabelGrupo, c, 15);
        new JLaTexto(fuente1, "Fecha: ", jLabelFecha, c, 15);
        new JLaTexto(fuente1, "Album: ", jLabelAlbum, c, 15);
        new JLaTexto(fuente1, "Comentario: ", jLabelComentario, c, 15);
        c = Color.BLACK;
        }
  
    }
    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfaceGrafica().setVisible(true);
            }
        });
    }
    
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu MenuEmergente;
    private javax.swing.JButton jButtonAgregarCanciones;
    private javax.swing.JButton jButtonAvance;
    private javax.swing.JButton jButtonDirectorio;
    private javax.swing.JButton jButtonEfecto1;
    private javax.swing.JButton jButtonEfecto2;
    private javax.swing.JButton jButtonLimpiarLista;
    private javax.swing.JButton jButtonMute;
    private javax.swing.JButton jButtonNoEfecto;
    private javax.swing.JButton jButtonParar;
    private javax.swing.JButton jButtonPausar;
    private javax.swing.JButton jButtonRepetir;
    private javax.swing.JButton jButtonReproducir;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonRetroceso;
    private javax.swing.JButton jButtonRuta;
    private javax.swing.JButton jButtonSiguiente;
    private javax.swing.JLabel jLabelAlbum;
    private javax.swing.JLabel jLabelBalance;
    private javax.swing.JLabel jLabelBitrate;
    private javax.swing.JLabel jLabelCancion;
    private javax.swing.JLabel jLabelComentario;
    private javax.swing.JLabel jLabelFRate;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelGenero;
    private javax.swing.JLabel jLabelGrupo;
    private javax.swing.JLabel jLabelImagen;
    private javax.swing.JLabel jLabelInterface;
    private javax.swing.JLabel jLabelPista;
    private javax.swing.JLabel jLabelRuta;
    private javax.swing.JLabel jLabelTamano;
    private javax.swing.JLabel jLabelTemaFondo;
    private javax.swing.JLabel jLabelTexto;
    private javax.swing.JLabel jLabelTiempo;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabelTranscurrido;
    private javax.swing.JLabel jLabelVolumen;
    private javax.swing.JList<String> jListListaCanciones;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItemCuadros;
    private javax.swing.JMenuItem jMenuItemEstrellas;
    private javax.swing.JMenuItem jMenuItemMusica;
    private javax.swing.JMenuItem jMenuItemOjas;
    private javax.swing.JMenuItem jMenuItemTnieve;
    private javax.swing.JMenuItem jMenuItemTtortuga;
    private javax.swing.JMenu jMenuOpciones;
    private javax.swing.JMenu jMenuTemas;
    private javax.swing.JMenu jMenuVersion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_Ecualizador;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSliderBalance;
    private javax.swing.JSlider jSliderControlVolumen;
    private javax.swing.JSlider jSliderProgresoMp3;
    // End of variables declaration//GEN-END:variables


}
