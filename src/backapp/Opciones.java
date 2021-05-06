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
import clases.Horarios;
import clases.Materias;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileFilter;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;

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

    public static File[] lista = null;

    ArrayList<Horarios> horarioHoy = new ArrayList<>();

    SimpleDateFormat formato = new SimpleDateFormat("EEEE");
    SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
    Date fecha = new Date();

    /**
     * Creates new form Opciones
     */
    public Opciones()
    {
        this.setLocation(100, 30);
        initComponents();

        String diaActual = quitarAcentos(formato.format(fecha));
        horarioHoy = ManipulaBD.ConsultaHorarios("dia=", "'" + diaActual + "'");
        if (horarioHoy != null)
        {
            Collections.sort(horarioHoy);
//            ArrayList<Materias> materia = ManipulaBD.ConsultaMaterias("id=", "" + horarioHoy.get(0).getMateria() + "");
//            jLabelClase.setText(materia.get(0).getNombreMateria());
        } else
        {
            jLabelClase.setText("no hay clases hoy");
        }

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
            hora();
            String horaActual = hora + ":" + minuto;
            jLHora.setText(horaActual);

            try
            {
                Date horaOC = formatoHora.parse(horaActual);
                for (int i = 0; i < horarioHoy.size(); i++)
                {
                    Date hor;
                    hor = formatoHora.parse(horarioHoy.get(i).getHoraInicio());
                    if (horaOC.equals(hor) || horaOC.after(hor))
                    {
                        ArrayList<Materias> materia = ManipulaBD.ConsultaMaterias("id=", "" + horarioHoy.get(i).getMateria() + "");
                        jLabelClase.setText(materia.get(0).getNombreMateria());
                        break;
                    }
                }
            } catch (ParseException ex)
            {
            }
        }
    }

    public void hora()
    {
        Calendar calendario = new GregorianCalendar();
        Date horaactual = new Date();
        calendario.setTime(horaactual);
        hora = calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
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
        jLabel1 = new javax.swing.JLabel();
        jLHora = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabelClase = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuLibretas = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMSalir = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

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
        jPanel1.setMinimumSize(new java.awt.Dimension(450, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(450, 50));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/tiempo.png"))); // NOI18N
        jPanel1.add(jLabel1);

        jLHora.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLHora.setText("10:45 ");
        jPanel1.add(jLHora);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setMinimumSize(new java.awt.Dimension(5, 10));
        jSeparator2.setPreferredSize(new java.awt.Dimension(5, 40));
        jPanel1.add(jSeparator2);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Clase actual:");
        jPanel1.add(jLabel7);

        jLabelClase.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabelClase.setText("Administracion");
        jPanel1.add(jLabelClase);

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton3.setText("nueva hoja");
        jPanel1.add(jButton3);

        jMenuBar2.setBackground(new java.awt.Color(240, 234, 210));
        jMenuBar2.setMaximumSize(new java.awt.Dimension(450, 32769));
        jMenuBar2.setMinimumSize(new java.awt.Dimension(450, 35));
        jMenuBar2.setPreferredSize(new java.awt.Dimension(450, 35));

        jMenu1.setText("Ayuda");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuBar2.add(jMenu1);

        jMenu7.setText("Notificaciones");
        jMenu7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jCheckBoxMenuItem1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Notificar siguiente clase");
        jMenu7.add(jCheckBoxMenuItem1);

        jCheckBoxMenuItem2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jCheckBoxMenuItem2.setText("Silenciar notificaciones");
        jMenu7.add(jCheckBoxMenuItem2);

        jMenuBar2.add(jMenu7);

        jMenu5.setText("Configuración");
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

        jMenuItem18.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem18.setText("Shortcuts");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem18);

        jMenuBar2.add(jMenu5);

        jMenuLibretas.setText("Libretas");
        jMenuLibretas.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuLibretas.add(jSeparator1);

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

        jMenuBar2.add(jMenuLibretas);

        jMSalir.setText("Salir");
        jMSalir.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMSalir.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMSalirActionPerformed(evt);
            }
        });

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem1.setText("Salir de la aplicación");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMSalir.add(jMenuItem1);

        jMenuBar2.add(jMSalir);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
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

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem18ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMSalirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMSalirActionPerformed
    {//GEN-HEADEREND:event_jMSalirActionPerformed

    }//GEN-LAST:event_jMSalirActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
        dispose();
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JLabel jLHora;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelClase;
    private javax.swing.JMenu jMSalir;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenu jMenuLibretas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    // End of variables declaration//GEN-END:variables
}
