package backapp;

import clases.HojaLibreta;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author rosal
 */
public class Hoja extends javax.swing.JPanel
{

    public HojaLibreta texto;
    private File[] imagenes;
    public String path;
    private File hojaA;
    private Libreta libretaM;
    public int tamanio = 14;
    public String tipo = "Segoe UI";
    public String Cadena;

    protected ArrayList<String> label = new ArrayList<>();
    protected ArrayList<File> ImagenesNuevas = new ArrayList<>();

    /**
     * Creates new form Hoja
     */
    public Hoja(File hoja, javax.swing.JFrame libretaM)
    {
        initComponents();
        btnNuevaImagen.setMnemonic(KeyEvent.VK_S);
        btnGuardar.setMnemonic(KeyEvent.VK_D);
        this.hojaA = hoja;
        this.libretaM = (Libreta) libretaM;
        path = hoja.getAbsolutePath() + "\\Text.dat";
        try
        {
            ObjectInputStream file = new ObjectInputStream(new FileInputStream(path));
            texto = (HojaLibreta) file.readObject();
            file.close();
        } catch (ClassNotFoundException ex)
        {
            System.out.println("La clase no existe o diferente");
        } catch (IOException e)
        {
        }
        txtTitulo.setText(texto.getTitulo());
        txtArcTexto.setText(texto.getTexto());

        imagenes = hoja.listFiles();
        SepararFormatos();
        Actualizar();
    }

    /**
     * Método que permite renombras las imagenes incluidas en las hojas desde 00
     * a 0n permitiendo su mejor manejo y representación de las mismas
     */
    protected void RenombrarImagenes()
    {
        String s = "";
        for (int i = 0; i < imagenes.length; i++)
        {

            if (!imagenes[i].isDirectory())
            {
                File tmp;
                s = imagenes[i].getParent() + "\\";
                String tam = String.valueOf(imagenes.length);
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
                String extencion = FilenameUtils.getExtension(imagenes[i].getName());
                s += "." + extencion;
                tmp = new File(s);
                imagenes[i].renameTo(tmp);
            }
        }
        imagenes = hojaA.listFiles();
        SepararFormatos();
    }

    /**
     * Método que separa los distintos ficheros de la carpeta libreta y los
     * separa entre las imagenes, binarios etc.
     */
    private void SepararFormatos()
    {
        ArrayList<File> tmp = new ArrayList<>();
        for (int i = 0; i < imagenes.length; i++)
        {
            String extencion = FilenameUtils.getExtension(imagenes[i].getName());
            if ((extencion.compareTo("jpg") == 0) || (extencion.compareTo("jpeg") == 0) || (extencion.compareTo("png") == 0))
            {
                tmp.add(imagenes[i]);
            }
        }

        imagenes = new File[tmp.size()];
        for (int i = 0; i < tmp.size(); i++)
        {
            imagenes[i] = tmp.get(i);
        }
    }

    /**
     * Método que representa las imagenes en el panel de la hoja
     */
    public void Actualizar()
    {
        jPanelImagenes.removeAll();
        label.clear();
        if (imagenes != null)
        {
            for (int i = 0; i < imagenes.length; i++)
            {
                if (!imagenes[i].isDirectory())
                {
                    String tex = RecortarNombre(imagenes[i].getName());
                    ImageIcon icono = new ImageIcon(imagenes[i].getAbsolutePath());
                    JLabel imagen = new JLabel();
                    imagen.setIcon(new ImageIcon(icono.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                    imagen.setText(tex);
                    imagen.setHorizontalTextPosition(JLabel.CENTER);
                    imagen.setVerticalTextPosition(JLabel.BOTTOM);
                    jPanelImagenes.add(imagen);
                }
            }
            jPanelImagenes.updateUI();
        }
    }

    /**
     * Cuando se selecciona una imagen de la computadora la representamos de
     * inmediato y por eso no podemos renombrarla, entonces usamos este método
     * para acortar el nombre
     *
     * @param nombre como esta guardada la imagen en el archivo
     * @return una cadena que inicia con los primeros 4 digitos seguido de 3
     * puntos y finalizando con los ultimos 3 digitos del nombre original
     */
    private String RecortarNombre(String nombre)
    {
        String s = "";

        if (nombre.length() > 10)
        {
            int n = nombre.length() - FilenameUtils.getExtension(nombre).length() - 1;
            s = nombre.substring(0, 4) + "..." + nombre.substring(n - 2, n) + "." + FilenameUtils.getExtension(nombre);
        } else
        {
            s = nombre;
        }

        return s;
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

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArcTexto = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanelImagenes = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ComboLetra = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        ComboTamanio = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnNuevaImagen = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setBackground(new java.awt.Color(234, 239, 210));

        jSplitPane1.setBackground(new java.awt.Color(234, 239, 210));
        jSplitPane1.setDividerLocation(225);

        txtArcTexto.setColumns(20);
        txtArcTexto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtArcTexto.setRows(5);
        jScrollPane1.setViewportView(txtArcTexto);

        jSplitPane1.setRightComponent(jScrollPane1);

        jPanelImagenes.setBackground(new java.awt.Color(234, 239, 210));
        jPanelImagenes.setLayout(new java.awt.GridLayout(0, 1, 50, 30));
        jScrollPane2.setViewportView(jPanelImagenes);

        jSplitPane1.setLeftComponent(jScrollPane2);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Titulo:");

        txtTitulo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTitulo.setText("Hoja 1");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tipo de letra");

        ComboLetra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ComboLetra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Segoe UI", "Arial", "Sans", "Ink Free" }));
        ComboLetra.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                ComboLetraItemStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tamaño de letra");

        ComboTamanio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ComboTamanio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "14", "16", "18" }));
        ComboTamanio.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                ComboTamanioItemStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Agregar imagen:");

        btnNuevaImagen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNuevaImagen.setText("seleccionar archivo");
        btnNuevaImagen.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnNuevaImagenActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnGuardarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 344, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboLetra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboTamanio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNuevaImagen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar)
                        .addGap(26, 26, 26)
                        .addComponent(btnGuardar)))
                .addContainerGap())
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(ComboLetra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(ComboTamanio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btnNuevaImagen)
                    .addComponent(btnGuardar)
                    .addComponent(btnEliminar))
                .addGap(15, 15, 15))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método que mueve las imagenes a la carpeta hoja de la libreta, las
     * renombra, guarda el titulo y el texto de la hoja.
     *
     * @param evt evento cuando se llama al boton
     */
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnGuardarActionPerformed
    {//GEN-HEADEREND:event_btnGuardarActionPerformed

        texto.setTitulo(txtTitulo.getText());
        texto.setTexto(txtArcTexto.getText());

        for (int i = 0; i < ImagenesNuevas.size(); i++)
        {
            try
            {
                //Definimos el destino del archivo, que será la carpeta Imágenes
                String dest = hojaA.getAbsolutePath() + "\\" + ImagenesNuevas.get(i).getName();
                Path destino = Paths.get(dest);
                //Definimos el origen, que será el archivo seleccionado
                String orig = ImagenesNuevas.get(i).getPath();
                Path origen = Paths.get(orig);
                //Copiamos el nuevo archivo con la clase Files, reemplazamos si ya existe uno igual.
                Files.copy(origen, destino, REPLACE_EXISTING);
                //Mostramos mensaje de confirmación de la copia
                System.out.println("si");
            } catch (IOException ex)
            {
                System.out.println("no");
            }
        }

        imagenes = hojaA.listFiles();
        SepararFormatos();
        RenombrarImagenes();

        try
        {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(path));
            file.writeObject(texto);
            file.close();
        } catch (IOException ex)
        {
            System.out.println("No se encontro archivo");
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * Método que permite elegir las imagenes de la computadora y los añade a la
     * hoja
     *
     * @param evt evento que se llama al dar clic al boton
     */
    private void btnNuevaImagenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNuevaImagenActionPerformed
    {//GEN-HEADEREND:event_btnNuevaImagenActionPerformed

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo de imagen", "png", "jpg", "jpeg");
        JFileChooser elegir = new JFileChooser();
        elegir.setDialogTitle("Seleccione una imagen");
        elegir.setFileFilter(filtro);
        elegir.showOpenDialog(this);
        File imagenNueva = elegir.getSelectedFile();

        if (imagenNueva != null)
        {
            ImagenesNuevas.add(imagenNueva);
            ImageIcon icono = new ImageIcon(imagenNueva.getAbsolutePath());
            JLabel imagen = new JLabel();
            imagen.setIcon(new ImageIcon(icono.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
            imagen.setText(imagenNueva.getName());
            imagen.setHorizontalTextPosition(JLabel.CENTER);
            imagen.setVerticalTextPosition(JLabel.BOTTOM);
            jPanelImagenes.add(imagen);
            jPanelImagenes.updateUI();
        } else
        {
            JOptionPane.showMessageDialog(null, "Por favor eliga una imagen");
        }

    }//GEN-LAST:event_btnNuevaImagenActionPerformed

    /**
     * Elimina una hoja de la libreta
     *
     * @param evt
     */
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnEliminarActionPerformed
    {//GEN-HEADEREND:event_btnEliminarActionPerformed

        libretaM.Eliminar(hojaA);

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void ComboTamanioItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_ComboTamanioItemStateChanged
    {//GEN-HEADEREND:event_ComboTamanioItemStateChanged

        cambioLF();

    }//GEN-LAST:event_ComboTamanioItemStateChanged

    private void ComboLetraItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_ComboLetraItemStateChanged
    {//GEN-HEADEREND:event_ComboLetraItemStateChanged

        cambioLF();

    }//GEN-LAST:event_ComboLetraItemStateChanged

    public void cambioLF()
    {
        Cadena = txtArcTexto.getText();
        tamanio = Integer.parseInt((String) ComboTamanio.getSelectedItem());
        String tipo = (String) ComboLetra.getSelectedItem();
        Font fuente = new Font(tipo, Font.PLAIN, tamanio);
        txtArcTexto.setText("");
        txtArcTexto.setFont(fuente);
        txtArcTexto.setText(Cadena);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboLetra;
    private javax.swing.JComboBox<String> ComboTamanio;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevaImagen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanelImagenes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextArea txtArcTexto;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
