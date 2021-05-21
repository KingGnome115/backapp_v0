package backapp;

import clases.ManipulaBD;
import clases.HojaLibreta;
import clases.Horarios;
import clases.Materias;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author rosal
 */
public class Horario extends javax.swing.JFrame
{

    boolean in = false;

    SimpleDateFormat formato = new SimpleDateFormat("EEEE d MMMM");
    Date fecha = new Date();

    ArrayList<Horarios> horari = new ArrayList<>();
    private int tamHor;
    private int totalHor;

    ArrayList<Materias> mate = new ArrayList<>();
    private int tamMate;
    private int totalMate;

    ArrayList<Object> ids = new ArrayList<>();

    ArrayList<Horarios> lunes = new ArrayList<>();
    ArrayList<Horarios> martes = new ArrayList<>();
    ArrayList<Horarios> miercoles = new ArrayList<>();
    ArrayList<Horarios> jueves = new ArrayList<>();
    ArrayList<Horarios> viernes = new ArrayList<>();
    ArrayList<Horarios> sabado = new ArrayList<>();
    ArrayList<Horarios> domingo = new ArrayList<>();

    private int[] seleccionadosMaterias;
    private int[] seleccionadosHorarios;

    /**
     * Creates new form Horario
     */
    public Horario()
    {
        initComponents();
        btnSalir.setMnemonic(KeyEvent.VK_Z);
        
        jLFecha.setText(formato.format(fecha));
        ConsultarMaterias();

        ConsultarHorarios();
        btnSalir.setMnemonic(KeyEvent.VK_ESCAPE);
    }

    public void ConsultarMaterias()
    {
        mate = ManipulaBD.ConsultaMaterias("id!=", "-1");
        if (mate != null)
        {
            tamMate = mate.size();
            ActualizarTablaMaterias();
            try
            {
                if (!mate.isEmpty())
                {
                    totalMate = mate.get(mate.size() - 1).getId() + 1;
                    jComboMaterias.removeAll();
                    for (int i = 0; i < mate.size(); i++)
                    {
                        jComboMaterias.addItem(mate.get(i).getId() + ": " + mate.get(i).getNombreMateria());
                        ids.add(mate.get(i).getId());
                    }
                } else
                {
                    totalMate = 0;
                }
            } catch (Exception e)
            {
                totalMate = 0;
            }
        }
    }

    public void ConsultarHorarios()
    {
        horari = ManipulaBD.ConsultaHorarios("id!=", "-1");
        if (horari != null)
        {
            tamHor = horari.size();
            try
            {
                if (!horari.isEmpty())
                {
                    totalHor = horari.get(horari.size() - 1).getId() + 1;
                } else
                {
                    totalHor = 0;
                }
            } catch (Exception e)
            {
                totalMate = 0;
            }
        }
        lunes = ManipulaBD.ConsultaHorarios("dia=", "'Lunes'");
        if (lunes != null)
        {
            Collections.sort(lunes);
            ActualizarDias(lunes, PanelLunes);
        }
        martes = ManipulaBD.ConsultaHorarios("dia=", "'Martes'");
        if (martes != null)
        {
            Collections.sort(martes);
            ActualizarDias(martes, PanelMartes);
        }
        miercoles = ManipulaBD.ConsultaHorarios("dia=", "'Miercoles'");
        if (miercoles != null)
        {
            Collections.sort(miercoles);
            ActualizarDias(miercoles, PanelMiercoles);
        }
        jueves = ManipulaBD.ConsultaHorarios("dia=", "'Jueves'");
        if (jueves != null)
        {
            Collections.sort(jueves);
            ActualizarDias(jueves, PanelJueves);
        }
        viernes = ManipulaBD.ConsultaHorarios("dia=", "'Viernes'");
        if (viernes != null)
        {
            Collections.sort(viernes);
            ActualizarDias(viernes, PanelViernes);
        }
        sabado = ManipulaBD.ConsultaHorarios("dia=", "'Sabado'");
        if (sabado != null)
        {
            Collections.sort(sabado);
            ActualizarDias(sabado, PanelSabado);
        }
        domingo = ManipulaBD.ConsultaHorarios("dia=", "'Domingo'");
        if (domingo != null)
        {
            Collections.sort(domingo);
            ActualizarDias(domingo, PanelDomingo);
        }
        ActualizarTablaHorario();
    }

    public void ActualizarDias(ArrayList<Horarios> lista, JPanel dia)
    {
        dia.removeAll();

        for (int i = 0; i < lista.size(); i++)
        {
            int id = lista.get(i).getMateria();
            ArrayList<Materias> materia = ManipulaBD.ConsultaMaterias("id=", "" + id + "");
            String texto = lista.get(i).getHoraInicio() + " :" + materia.get(0).getNombreMateria();
            JTextField caja = new JTextField();
            caja.setSize(25, 25);
            caja.setText(texto);
            caja.setToolTipText(
                    "Profesor :" + materia.get(0).getNombreMaestro() + " \nSemestre:"
                    + +materia.get(0).getSemestre());
            caja.setEditable(false);
            caja.setBackground(materia.get(i).getColor());
            dia.add(caja);
        }
        dia.updateUI();
    }

    public void ActualizarTablaMaterias()
    {
        Object matriz[][] = new Object[tamMate][5];
        for (int i = 0; i < mate.size(); i++)
        {
            matriz[i][0] = mate.get(i).getId();
            matriz[i][1] = mate.get(i).getNombreMateria();
            matriz[i][2] = mate.get(i).getNombreMaestro();
            matriz[i][3] = mate.get(i).getSemestre();
            matriz[i][4] = mate.get(i).getGrupo();
        }

        /*
        Los titulos de las columnas
         */
        TablaMateria.setModel(new javax.swing.table.DefaultTableModel(matriz, new String[]
        {
            "Id", "Materia", "Maestro", "Semestre", "Grupo"
        })
        {
            /*
            Las clases de cada propiedad para tener un nivel de validación
             */
            Class[] types = new Class[]
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            /*
            Cuales columnas pueden ser modificadas
             */
            boolean[] canEdit = new boolean[]
            {
                false, true, true, true, true
            };

            @Override
            public Class getColumnClass(int columnIndex)
            {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit[columnIndex];
            }
        });

        TablaMateria.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                seleccionadosMaterias = TablaMateria.getSelectedRows();
            }
        });
    }

    public void ActualizarTablaHorario()
    {
        Object matriz[][] = new Object[tamHor][6];
        for (int i = 0; i < horari.size(); i++)
        {
            matriz[i][0] = horari.get(i).getId();
            ArrayList<Materias> ap = ManipulaBD.ConsultaMaterias("id=", "" + horari.get(i).getId() + "");
            matriz[i][1] = ap.get(0).getNombreMateria();
            matriz[i][2] = horari.get(i).getDia();
            matriz[i][3] = horari.get(i).getHoraInicio();
            matriz[i][4] = horari.get(i).getHoraFinal();
            matriz[i][5] = horari.get(i).isNotificar();
        }

        /*
        Los titulos de las columnas
         */
        TablaHorarios.setModel(new javax.swing.table.DefaultTableModel(matriz, new String[]
        {
            "Id", "Materia", "Dia", "horaInicio", "horaFinal", "Notificar"
        })
        {
            /*
            Las clases de cada propiedad para tener un nivel de validación
             */
            Class[] types = new Class[]
            {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            /*
            Cuales columnas pueden ser modificadas
             */
            boolean[] canEdit = new boolean[]
            {
                false, false, false, false, false, true
            };

            @Override
            public Class getColumnClass(int columnIndex)
            {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit[columnIndex];
            }
        });

        TablaHorarios.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                seleccionadosHorarios = TablaHorarios.getSelectedRows();
            }
        });
    }

    //C:\ProgramData\Microsoft\Windows\Start Menu\Programs\StartUp
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel4 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel_Mover = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PanelLunes = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        PanelMartes = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        PanelMiercoles = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        PanelJueves = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        PanelViernes = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        PanelSabado = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        PanelDomingo = new javax.swing.JPanel();
        jLFecha = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaMateria = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        txtSemestre = new javax.swing.JTextField();
        txtMaestro = new javax.swing.JTextField();
        txtMateria = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnAgregarMaterias = new javax.swing.JButton();
        btnLimpiarMaterias = new javax.swing.JButton();
        txtGrupo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel_Color = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnCancelar1 = new javax.swing.JButton();
        jLabel57 = new javax.swing.JLabel();
        btnEditarMaterias = new javax.swing.JButton();
        btnEliminarMateria = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        txtFinSemestre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jComboMaterias = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jCheckNotificar = new javax.swing.JCheckBox();
        ComboDias = new javax.swing.JComboBox<>();
        ComboHora = new javax.swing.JComboBox<>();
        ComboHora1 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        btnLimpiarHorario = new javax.swing.JButton();
        btnAgregarHorario = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        TablaHorarios = new javax.swing.JTable();
        jLabel59 = new javax.swing.JLabel();
        btnEliminarHorario = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel10.setBackground(new java.awt.Color(196, 170, 151));
        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel_Mover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/hold.png"))); // NOI18N
        jLabel_Mover.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseDragged(java.awt.event.MouseEvent evt)
            {
                jLabel_MoverMouseDragged(evt);
            }
        });
        jLabel_Mover.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                jLabel_MoverMouseReleased(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel_Mover)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel_Mover, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jTabbedPane1.setBackground(new java.awt.Color(196, 170, 151));
        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(600, 550));

        jPanel1.setBackground(new java.awt.Color(234, 239, 210));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 141));
        jPanel1.setRequestFocusEnabled(false);

        jPanel8.setBackground(new java.awt.Color(234, 239, 210));

        jPanel9.setBackground(new java.awt.Color(234, 239, 210));
        jPanel9.setPreferredSize(new java.awt.Dimension(30, 45));
        jPanel9.setLayout(new java.awt.GridLayout(1, 7, 5, 0));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Lunes");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.add(jLabel7);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Martes");
        jLabel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.add(jLabel13);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Miercoles");
        jLabel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.add(jLabel15);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Jueves");
        jLabel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.add(jLabel16);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Viernes");
        jLabel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.add(jLabel17);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Sabado");
        jLabel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.add(jLabel18);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Domingo");
        jLabel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.add(jLabel20);

        jPanel11.setBackground(new java.awt.Color(234, 239, 210));
        jPanel11.setLayout(new java.awt.GridLayout(1, 7, 5, 0));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        PanelLunes.setBackground(new java.awt.Color(234, 239, 210));
        PanelLunes.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane1.setViewportView(PanelLunes);

        jPanel11.add(jScrollPane1);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        PanelMartes.setBackground(new java.awt.Color(234, 239, 210));
        PanelMartes.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane3.setViewportView(PanelMartes);

        jPanel11.add(jScrollPane3);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        PanelMiercoles.setBackground(new java.awt.Color(234, 239, 210));
        PanelMiercoles.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane4.setViewportView(PanelMiercoles);

        jPanel11.add(jScrollPane4);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        PanelJueves.setBackground(new java.awt.Color(234, 239, 210));
        PanelJueves.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane5.setViewportView(PanelJueves);

        jPanel11.add(jScrollPane5);

        jScrollPane6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        PanelViernes.setBackground(new java.awt.Color(234, 239, 210));
        PanelViernes.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane6.setViewportView(PanelViernes);

        jPanel11.add(jScrollPane6);

        jScrollPane7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        PanelSabado.setBackground(new java.awt.Color(234, 239, 210));
        PanelSabado.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane7.setViewportView(PanelSabado);

        jPanel11.add(jScrollPane7);

        jScrollPane8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        PanelDomingo.setBackground(new java.awt.Color(234, 239, 210));
        PanelDomingo.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane8.setViewportView(PanelDomingo);

        jPanel11.add(jScrollPane8);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLFecha.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLFecha.setText("Hoy: Miercoles 6 de mayo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLFecha)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Vista de cuadricula", jPanel1);

        jPanel7.setBackground(new java.awt.Color(234, 239, 210));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("Lista de materias");

        TablaMateria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TablaMateria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        jScrollPane2.setViewportView(TablaMateria);

        jPanel16.setBackground(new java.awt.Color(234, 239, 210));

        txtSemestre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMaestro.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMateria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Semestre:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Nombre de Maestro:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Nombre de la Materia:");

        btnAgregarMaterias.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAgregarMaterias.setText("Agregar materia");
        btnAgregarMaterias.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAgregarMateriasActionPerformed(evt);
            }
        });

        btnLimpiarMaterias.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLimpiarMaterias.setText("Limpiar campos");
        btnLimpiarMaterias.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnLimpiarMateriasActionPerformed(evt);
            }
        });

        txtGrupo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Grupo:");

        jLabel_Color.setBackground(new java.awt.Color(255, 153, 0));
        jLabel_Color.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_Color.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel_Color.setToolTipText("");
        jLabel_Color.setOpaque(true);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Color:");

        btnCancelar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelar1.setText("seleccionar color");
        btnCancelar1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCancelar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaestro))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(btnLimpiarMaterias)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarMaterias))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel_Color, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar1)
                .addGap(50, 50, 50))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSemestre, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSemestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaestro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancelar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_Color, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarMaterias)
                    .addComponent(btnLimpiarMaterias)))
        );

        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("Datos de la materia:");

        btnEditarMaterias.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEditarMaterias.setText("Editar");
        btnEditarMaterias.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnEditarMateriasActionPerformed(evt);
            }
        });

        btnEliminarMateria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEliminarMateria.setText("Eliminar");
        btnEliminarMateria.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnEliminarMateriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(btnEditarMaterias)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminarMateria))
                    .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(200, 200, 200))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarMaterias)
                    .addComponent(btnEliminarMateria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Materias", jPanel7);

        jPanel3.setBackground(new java.awt.Color(234, 239, 210));

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("Horario");

        txtFinSemestre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFinSemestre.setText("17/07/2021");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Fecha de termino del semestre:");

        jPanel2.setBackground(new java.awt.Color(234, 239, 210));
        jPanel2.setToolTipText("");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Materia:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Dia:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Hora de inicio:");

        jCheckNotificar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jCheckNotificar.setText("Notificar");
        jCheckNotificar.setOpaque(false);

        ComboDias.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ComboDias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" }));

        ComboHora.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ComboHora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00" }));

        ComboHora1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ComboHora1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00" }));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Hora de termino:");

        btnLimpiarHorario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLimpiarHorario.setText("Limpiar campos");
        btnLimpiarHorario.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnLimpiarHorarioActionPerformed(evt);
            }
        });

        btnAgregarHorario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAgregarHorario.setText("Agregar al horario");
        btnAgregarHorario.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAgregarHorarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnLimpiarHorario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarHorario))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboHora1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboDias, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckNotificar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jComboMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(ComboDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckNotificar))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14)
                    .addComponent(ComboHora1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiarHorario)
                    .addComponent(btnAgregarHorario))
                .addGap(0, 0, 0))
        );

        TablaHorarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        jScrollPane9.setViewportView(TablaHorarios);

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("Datos Horario:");

        btnEliminarHorario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEliminarHorario.setText("Eliminar");
        btnEliminarHorario.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnEliminarHorarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEliminarHorario))
                    .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(150, 150, 150))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(txtFinSemestre)
                .addGap(238, 238, 238))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(210, 210, 210))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarHorario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtFinSemestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Horarios", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarHorarioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAgregarHorarioActionPerformed
    {//GEN-HEADEREND:event_btnAgregarHorarioActionPerformed

        int id = totalHor++;
        int materia = (int) ids.get(jComboMaterias.getSelectedIndex());
        String dia = (String) ComboDias.getSelectedItem();
        String horaInicio = (String) ComboHora.getSelectedItem();
        String horaFinal = (String) ComboHora1.getSelectedItem();
        boolean notificar = jCheckNotificar.isSelected();
        ManipulaBD.AltaHorarios(id, materia, dia, horaInicio, horaFinal, notificar);
        ConsultarHorarios();

    }//GEN-LAST:event_btnAgregarHorarioActionPerformed

    private void btnLimpiarMateriasActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLimpiarMateriasActionPerformed
    {//GEN-HEADEREND:event_btnLimpiarMateriasActionPerformed
        Limpiar();
    }//GEN-LAST:event_btnLimpiarMateriasActionPerformed

    private void btnAgregarMateriasActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAgregarMateriasActionPerformed
    {//GEN-HEADEREND:event_btnAgregarMateriasActionPerformed

        String nombreMateria = txtMateria.getText();
        String nombreMaestro = txtMaestro.getText();
        int semestre = Integer.parseInt(txtSemestre.getText());
        String grupo = txtGrupo.getText();
        String color = jLabel_Color.getBackground().toString();

        ManipulaBD.AltaMaterias(totalMate++, semestre, nombreMaestro, nombreMateria, grupo, color); // AntiguoManipulaBD.AltaMaterias(total++, libreta, semestre, nombreMaestro, nombreMateria);

        String directorio = Opciones.directorio + "\\" + nombreMateria;
        File libreta = new File(directorio);
        if (!libreta.exists())
        {
            libreta.mkdir();
            File carpeta = new File(Opciones.directorio);
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
                Opciones.lista = carpeta.listFiles(filtro);
            }

            directorio += "\\00";
            File hoja1 = new File(directorio);
            hoja1.mkdir();

            try
            {
                ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(directorio + "\\Text.dat"));
                HojaLibreta obj = new HojaLibreta();
                file.writeObject(obj);
                file.close();
            } catch (IOException ex)
            {
                System.out.println("No se encontro archivo");
            }
        }

        mate = ManipulaBD.ConsultaMaterias("id!=", "-1");
        tamMate = mate.size();
        ActualizarTablaMaterias();
        Limpiar();

    }//GEN-LAST:event_btnAgregarMateriasActionPerformed

    private void jLabel_MoverMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel_MoverMouseDragged
    {//GEN-HEADEREND:event_jLabel_MoverMouseDragged
        jLabel_Mover.setIcon(new ImageIcon(getClass().getResource("/iconos/drag.png")));
        this.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
    }//GEN-LAST:event_jLabel_MoverMouseDragged

    private void jLabel_MoverMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel_MoverMouseReleased
    {//GEN-HEADEREND:event_jLabel_MoverMouseReleased
        this.setLocation(this.getX() - 15, this.getY() - 15);
        jLabel_Mover.setIcon(new ImageIcon(getClass().getResource("/iconos/hold.png")));
    }//GEN-LAST:event_jLabel_MoverMouseReleased

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSalirActionPerformed
    {//GEN-HEADEREND:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelar1ActionPerformed
    {//GEN-HEADEREND:event_btnCancelar1ActionPerformed
        Color c0 = new JColorChooser().showDialog(null, "Seleccione un color", Color.gray);
        jLabel_Color.setBackground(c0);
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnEditarMateriasActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnEditarMateriasActionPerformed
    {//GEN-HEADEREND:event_btnEditarMateriasActionPerformed

        for (int i = 0; i < TablaMateria.getRowCount(); i++)
        {
            int id = (int) TablaMateria.getValueAt(i, 0);
            String materia = (String) TablaMateria.getValueAt(i, 1);
            String maestro = (String) TablaMateria.getValueAt(i, 2);
            int semestre = (int) TablaMateria.getValueAt(i, 3);
            String grupo = (String) TablaMateria.getValueAt(i, 4);

            if (id != mate.get(i).getId()
                    || (materia.compareTo(mate.get(i).getNombreMateria()) == 0)
                    || (maestro.compareTo(mate.get(i).getNombreMaestro()) == 0)
                    || semestre != mate.get(i).getSemestre()
                    || (grupo.compareTo(mate.get(i).getGrupo()) == 0))
            {
                ManipulaBD.ModificarMaterias(id, "nombreMateria,nombreMaestro,semestre,grupo",
                        "'" + materia + "','" + maestro + "'," + semestre + ",'" + grupo + "'");
            }
        }
        ConsultarMaterias();

    }//GEN-LAST:event_btnEditarMateriasActionPerformed

    private void btnEliminarMateriaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnEliminarMateriaActionPerformed
    {//GEN-HEADEREND:event_btnEliminarMateriaActionPerformed

        for (int i = 0; i < seleccionadosMaterias.length; i++)
        {
            ManipulaBD.BajasMaterias(mate.get(seleccionadosMaterias[i]).getId());
            ManipulaBD.BajasHorarios("materia", mate.get(seleccionadosMaterias[i]).getId());
        }
        ConsultarMaterias();
        ActualizarTablaMaterias();
        ConsultarHorarios();

    }//GEN-LAST:event_btnEliminarMateriaActionPerformed

    private void btnLimpiarHorarioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLimpiarHorarioActionPerformed
    {//GEN-HEADEREND:event_btnLimpiarHorarioActionPerformed
        txtFinSemestre.setText("DD/MM/AAAA");
    }//GEN-LAST:event_btnLimpiarHorarioActionPerformed

    private void btnEliminarHorarioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnEliminarHorarioActionPerformed
    {//GEN-HEADEREND:event_btnEliminarHorarioActionPerformed
        for (int i = 0; i < seleccionadosHorarios.length; i++)
        {
            ManipulaBD.BajasHorarios("id=", horari.get(seleccionadosHorarios[i]).getId());
        }
        ConsultarHorarios();
    }//GEN-LAST:event_btnEliminarHorarioActionPerformed

    public void Limpiar()
    {
        txtSemestre.setText("");
        txtGrupo.setText("");
        txtMaestro.setText("");
        txtMateria.setText("");
    }

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
            java.util.logging.Logger.getLogger(Horario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Horario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Horario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Horario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Horario().setVisible(true);
            }
        });
    }

//    private void jButtonEJEMPLOActionPerformed(java.awt.event.ActionEvent evt)                                         
//    {                                             
//        PanelJueves.add(new CuadroHorario(Color.pink, "Redes", "10:00", "12:00", "Juanín"));
//        PanelJueves.updateUI();
//        System.out.println("nuevo cuadro horario agregado");
//    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboDias;
    private javax.swing.JComboBox<String> ComboHora;
    private javax.swing.JComboBox<String> ComboHora1;
    private javax.swing.JPanel PanelDomingo;
    private javax.swing.JPanel PanelJueves;
    private javax.swing.JPanel PanelLunes;
    private javax.swing.JPanel PanelMartes;
    private javax.swing.JPanel PanelMiercoles;
    private javax.swing.JPanel PanelSabado;
    private javax.swing.JPanel PanelViernes;
    private javax.swing.JTable TablaHorarios;
    private javax.swing.JTable TablaMateria;
    private javax.swing.JButton btnAgregarHorario;
    private javax.swing.JButton btnAgregarMaterias;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnEditarMaterias;
    private javax.swing.JButton btnEliminarHorario;
    private javax.swing.JButton btnEliminarMateria;
    private javax.swing.JButton btnLimpiarHorario;
    private javax.swing.JButton btnLimpiarMaterias;
    private javax.swing.JButton btnSalir;
    private javax.swing.JCheckBox jCheckNotificar;
    private javax.swing.JComboBox<String> jComboMaterias;
    private javax.swing.JLabel jLFecha;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Color;
    private javax.swing.JLabel jLabel_Mover;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtFinSemestre;
    private javax.swing.JTextField txtGrupo;
    private javax.swing.JTextField txtMaestro;
    private javax.swing.JTextField txtMateria;
    private javax.swing.JTextField txtSemestre;
    // End of variables declaration//GEN-END:variables
}

class CuadroHorario extends javax.swing.JPanel
{

    private javax.swing.JLabel grupo;
    private javax.swing.JLabel horafin;
    private javax.swing.JLabel horainicio;
    private javax.swing.JLabel materia;
    private javax.swing.JLabel profe;
    private javax.swing.JLabel semestre;

    public CuadroHorario(Color color, String materia, String horainicio, String horafin, String profe, String semestre, String grupo)
    {
        initComponents();
        this.materia.setText(materia);
        this.horainicio.setText(horainicio);
        this.horafin.setText(horafin);
        this.profe.setText(profe);
        this.semestre.setText(semestre);
        this.grupo.setText(grupo);
        this.setBackground(color);
    }

    private void initComponents()
    {
        materia = new javax.swing.JLabel();
        horainicio = new javax.swing.JLabel();
        horafin = new javax.swing.JLabel();
        profe = new javax.swing.JLabel();
        semestre = new javax.swing.JLabel();
        grupo = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMinimumSize(new java.awt.Dimension(100, 150));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(100, 150));

        materia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        materia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        materia.setText("Materia");
        materia.setPreferredSize(new java.awt.Dimension(90, 20));
        add(materia);

        horainicio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        horainicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        horainicio.setText("Inicio");
        horainicio.setPreferredSize(new java.awt.Dimension(90, 20));
        add(horainicio);

        horafin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        horafin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        horafin.setText("Termino");
        horafin.setPreferredSize(new java.awt.Dimension(90, 20));
        add(horafin);

        profe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        profe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profe.setText("Profe");
        profe.setPreferredSize(new java.awt.Dimension(90, 20));
        add(profe);

        semestre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        semestre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        semestre.setText("Semestre");
        semestre.setPreferredSize(new java.awt.Dimension(90, 20));
        add(semestre);

        grupo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        grupo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        grupo.setText("Grupo");
        grupo.setPreferredSize(new java.awt.Dimension(90, 20));
        add(grupo);
    }
}
