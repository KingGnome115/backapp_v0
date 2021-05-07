package backapp;

import backapp.configuracion.Companieros;
import backapp.libretas.Compartir;
import backapp.libretas.Creacion;
import backapp.libretas.Eliminacion;
import backapp.libretas.Exportar;
import backapp.configuracion.Horario;
import backapp.libretas.Importar;
import backapp.libretas.Libreta;
import basededatos.ManipulaBD;
import clases.HojaLibreta;
import clases.Horarios;
import clases.Materias;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author rosal
 */
public class Opciones extends javax.swing.JFrame implements Runnable
{

    private String hora, minuto;
    private Thread hilo;

    private String user = System.getProperty("user.name");
    private String dir = "C:\\Users\\" + user + "\\Documents\\Mochila";
    public static String directorio;
    public String directorioLibreta;

    public static File[] lista = null;

    ArrayList<Horarios> horarioHoy = new ArrayList<>();

    SimpleDateFormat formato = new SimpleDateFormat("EEEE");
    SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
    Date fecha = new Date();

    int cont = 0;

    /**
     * Creates new form Opciones
     */
    public Opciones()
    {
        this.setLocation(100, 30);
        initComponents();

        hilo = new Thread(this);
        hilo.start();
        File carpeta = new File(dir);
        if (!carpeta.exists())
        {
            carpeta.mkdir();
        } else
        {
            FileFilter filtro = new FileFilter()
            {
                @Override
                public boolean accept(File fil)
                {
                    return fil.isDirectory();
                }
            };
            lista = carpeta.listFiles(filtro);
            MostrarLibretas();
        }
        directorio = carpeta.getAbsolutePath();

        String diaActual = quitarAcentos(formato.format(fecha));
        horarioHoy = ManipulaBD.ConsultaHorarios("dia=", "'" + diaActual + "'");
        if (horarioHoy != null)
        {
            Collections.sort(horarioHoy);
            try
            {
                for (int i = 0; i < horarioHoy.size(); i++)
                {
                    ArrayList<Materias> materia = ManipulaBD.ConsultaMaterias("id=", "" + horarioHoy.get(i).getMateria() + "");
                    ComboLibretasHoy.addItem(materia.get(0).getNombreMateria());
                }
            } catch (Exception e)
            {
                System.err.println("No se cargaron materias en el ComboBox");
            }
        } else
        {
            // cuando no haya clases hoy:
            jLabel_Info.setText("no hay clases hoy");
            jButton3.setVisible(false);
            ComboLibretasHoy.setVisible(false);
        }
    }

    public String quitarAcentos(String texto)
    {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }

    public void MostrarLibretas()
    {
        for (int i = 0; i < lista.length; i++)
        {
            JMenuItem jLibreta = new JMenuItem();
            jLibreta.setFont(new java.awt.Font("Segoe UI", 0, 16));
            jLibreta.setText(lista[i].getName());

            String dir = lista[i].getAbsolutePath();

            jLibreta.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(java.awt.event.ActionEvent evt)
                {
                    Libreta.libreta = dir;
                    new Libreta().setVisible(true);
                }
            });

            jMenuLibretas.add(jLibreta);

        }
    }

    @Override
    public Image getIconImage()
    {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("iconos/mochila.png"));
        return retValue;
    }

    @Override
    public void run()
    {
        Thread current = Thread.currentThread();
        while (current == hilo)
        {
            String horaActual = hora + ":" + minuto;
            try
            {
                if (horarioHoy != null)
                {
                    Date horaOC = formatoHora.parse(horaActual);
                    for (int i = 0; i < horarioHoy.size(); i++)
                    {
                        if (cont == 0)
                        {
                            cont = 1;
                            Date hor;
                            hor = formatoHora.parse(horarioHoy.get(i).getHoraInicio());
                            if (horaOC.equals(hor) || horaOC.after(hor))
                            {
                                ArrayList<Materias> materia = ManipulaBD.ConsultaMaterias("id=", "" + horarioHoy.get(i).getMateria() + "");
                                if (horaOC.equals(hor))
                                {
                                    Notificaciones("Nueva Clase", materia.get(0).getNombreMateria());
                                    cont = 0;
                                }
                                break;
                            }
                        }
                    }
                }
            } catch (ParseException ex)
            {
            }

        }
    }

    protected void Notificaciones(String titulo, String mensaje)
    {
        try
        {
            SystemTray tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");

            TrayIcon trayicon = new TrayIcon(image, "Java AWT Tray Demo");

            trayicon.setImageAutoSize(true);

            trayicon.setToolTip("System tray icon demo");

            tray.add(trayicon);

            trayicon.displayMessage(titulo, mensaje, TrayIcon.MessageType.INFO);
        } catch (AWTException ex)
        {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void Renombrar(File[] obj)
    {
        String s = "";
        for (int i = 0; i < obj.length; i++)
        {
            File tmp;
            s = obj[i].getParent() + "\\";
            String tam = String.valueOf(obj.length);
            String ii = String.valueOf(i);
            String ceros = "";
            int t = tam.length() - ii.length();
            if (t == 0 && ii.length() == 1)
            {
                t = 1;
            }
            for (int j = 0; j < t; j++)
            {
                ceros += "0";
            }
            s += ceros + i;
            String extencion = FilenameUtils.getExtension(obj[i].getName());
            s += "." + extencion;
            tmp = new File(s);
            obj[i].renameTo(tmp);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jMenu3 = new javax.swing.JMenu();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel_Info = new javax.swing.JLabel();
        ComboLibretasHoy = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu_Mover = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMSalir = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenuLibretas = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();

        jMenu3.setText("jMenu3");

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BackApp v0");
        setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(450, 85));
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(234, 239, 210));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMinimumSize(new java.awt.Dimension(450, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(450, 50));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel_Info.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel_Info.setText("Clases de hoy:");
        jPanel1.add(jLabel_Info);

        ComboLibretasHoy.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        ComboLibretasHoy.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                ComboLibretasHoyItemStateChanged(evt);
            }
        });
        jPanel1.add(ComboLibretasHoy);

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton3.setText("nueva hoja");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jMenuBar2.setBackground(new java.awt.Color(240, 234, 210));
        jMenuBar2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenuBar2.setMaximumSize(new java.awt.Dimension(450, 32769));
        jMenuBar2.setMinimumSize(new java.awt.Dimension(450, 35));
        jMenuBar2.setPreferredSize(new java.awt.Dimension(450, 35));

        jMenu_Mover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/hold.png"))); // NOI18N
        jMenu_Mover.setToolTipText("Mover Ventana");
        jMenu_Mover.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenu_Mover.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                jMenu_MoverMouseDragged(evt);
            }
        });
        jMenu_Mover.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                jMenu_MoverMouseReleased(evt);
            }
        });
        jMenuBar2.add(jMenu_Mover);

        jMenu1.setText("Ayuda");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuBar2.add(jMenu1);

        jMSalir.setText("Salir");
        jMSalir.setToolTipText("Salir de la aplicación (Escape)");
        jMSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMSalir.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMSalir.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMSalirActionPerformed(evt);
            }
        });
        jMenuBar2.add(jMSalir);

        jMenu5.setText("Configuración");
        jMenu5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem2.setText("Horario y Materias");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem2);

        jMenuItem15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem15.setText("Compañeros");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem15);
        jMenu5.add(jSeparator7);

        jCheckBoxMenuItem2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jCheckBoxMenuItem2.setText("Silenciar notificaciones");
        jCheckBoxMenuItem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu5.add(jCheckBoxMenuItem2);

        jMenuBar2.add(jMenu5);

        jMenuLibretas.setText("Libretas");
        jMenuLibretas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuLibretas.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jMenuItem5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem5.setText("Compartir ");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenuLibretas.add(jMenuItem5);

        jMenuItem8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem8.setText("Crear ");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenuLibretas.add(jMenuItem8);

        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem9.setText("Eliminar ");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenuLibretas.add(jMenuItem9);
        jMenuLibretas.add(jSeparator3);

        jMenuItem3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem3.setText("Importar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenuLibretas.add(jMenuItem3);

        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem4.setText("Exportar");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenuLibretas.add(jMenuItem4);
        jMenuLibretas.add(jSeparator1);

        jMenuBar2.add(jMenuLibretas);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem2ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem2ActionPerformed
        new Horario().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem9ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem9ActionPerformed
        new Eliminacion().setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem15ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem15ActionPerformed
        new Companieros().setVisible(true);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem8ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem8ActionPerformed
        new Creacion().setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem3ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem3ActionPerformed
        new Importar().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem4ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem4ActionPerformed
        new Exportar().setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem5ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem5ActionPerformed
        new Compartir().setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMSalirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMSalirActionPerformed
    {//GEN-HEADEREND:event_jMSalirActionPerformed
        dispose();
        System.exit(0);
    }//GEN-LAST:event_jMSalirActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed

        File libreta = new File(directorioLibreta);
        File[] hojasL = libreta.listFiles();
        directorioLibreta += "\\Z";

        File hoja1 = new File(directorioLibreta);
        hoja1.mkdir();
        try
        {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(directorioLibreta + "\\Text.dat"));
            HojaLibreta obj = new HojaLibreta();
            file.writeObject(obj);
            file.close();
        } catch (IOException ex)
        {
            System.out.println("No se encontro archivo");
        }
        hojasL = libreta.listFiles();
        Renombrar(hojasL);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenu_MoverMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jMenu_MoverMouseDragged
    {//GEN-HEADEREND:event_jMenu_MoverMouseDragged
        jMenu_Mover.setIcon(new ImageIcon(getClass().getResource("/iconos/drag.png")));
        this.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
    }//GEN-LAST:event_jMenu_MoverMouseDragged

    private void jMenu_MoverMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jMenu_MoverMouseReleased
    {//GEN-HEADEREND:event_jMenu_MoverMouseReleased
        this.setLocation(this.getX() - 15, this.getY() - 15);
        jMenu_Mover.setIcon(new ImageIcon(getClass().getResource("/iconos/hold.png")));
    }//GEN-LAST:event_jMenu_MoverMouseReleased

    private void ComboLibretasHoyItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_ComboLibretasHoyItemStateChanged
    {//GEN-HEADEREND:event_ComboLibretasHoyItemStateChanged

        directorioLibreta = Opciones.directorio + "\\" + ComboLibretasHoy.getSelectedItem();
        System.out.println(directorioLibreta);

    }//GEN-LAST:event_ComboLibretasHoyItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Opciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboLibretasHoy;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel_Info;
    private javax.swing.JMenu jMSalir;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenu jMenuLibretas;
    private javax.swing.JMenu jMenu_Mover;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    // End of variables declaration//GEN-END:variables
}
