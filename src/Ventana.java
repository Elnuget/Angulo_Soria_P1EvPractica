import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel principal;
    private JSpinner spiIdentificadorTarea;
    private JTextField txtNombreTarea;
    private JComboBox<String> cboCategoriaTarea;
    private JTextField txtPresupuestoTarea;
    private JComboBox<String> cboPrioridad;
    private JTextArea txtListarTarea;
    private JButton btnEcolarTarea;
    private JLabel lblPresupuestoCategoria;
    private JLabel lblPresupuestoAjustadoCategoria;
    private JComboBox<String> cboCategoriaBusqueda;
    
    // Instancia de ColaTareas
    private ColaTareas colaTareas;
    
    // Constructor
    public Ventana() {
        // Inicializar la cola de tareas
        colaTareas = new ColaTareas();
        
        // Configurar la ventana
        setTitle("Sistema de Gestión de Tareas - Angulo_Soria");
        setContentPane(principal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1020, 600);
        setLocationRelativeTo(null);
        
        // Configurar componentes iniciales
        configurarComponentes();
        
        // Agregar listeners
        agregarListeners();
        
        // Actualizar la lista de tareas
        actualizarListaTareas();
        
        // Inicializar presupuestos por categoría (R3)
        actualizarPresupuestosPorCategoria();
        
        setVisible(true);
    }
    
    // Configurar los componentes iniciales
    private void configurarComponentes() {
        // Configurar el spinner de identificador (valores de 6 a 9999, iniciando en 6)
        SpinnerNumberModel modeloSpinner = new SpinnerNumberModel(6, 1, 9999, 1);
        spiIdentificadorTarea.setModel(modeloSpinner);
        
        // Configurar el área de texto como solo lectura
        txtListarTarea.setEditable(false);
        txtListarTarea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
    }
    
    // Agregar listeners a los componentes
    private void agregarListeners() {
        // Listener para el botón de crear tarea
        btnEcolarTarea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearTarea();
            }
        });
        
        // Listener para el combo de categoría de búsqueda
        cboCategoriaBusqueda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPresupuestosPorCategoria();
            }
        });
    }
    
    // R1: Crear una nueva tarea
    private void crearTarea() {
        try {
            // Obtener valores de los campos
            int identificador = (Integer) spiIdentificadorTarea.getValue();
            String nombre = txtNombreTarea.getText().trim();
            String categoria = (String) cboCategoriaTarea.getSelectedItem();
            String presupuestoTexto = txtPresupuestoTarea.getText().trim();
            int prioridad = Integer.parseInt((String) cboPrioridad.getSelectedItem());
            
            // Validaciones
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "El nombre de la tarea no puede estar vacío", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (presupuestoTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "El presupuesto no puede estar vacío", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar que el identificador no exista
            if (colaTareas.existeIdentificador(identificador)) {
                JOptionPane.showMessageDialog(this, 
                    "El identificador " + identificador + " ya existe. Por favor, use otro.", 
                    "Identificador duplicado", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Parsear presupuesto
            float presupuesto = Float.parseFloat(presupuestoTexto);
            
            if (presupuesto <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "El presupuesto debe ser mayor a cero", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear la tarea
            Tarea nuevaTarea = new Tarea(identificador, nombre, categoria, presupuesto, prioridad);
            
            // Agregar la tarea a la cola
            boolean agregada = colaTareas.agregarTarea(nuevaTarea);
            
            if (agregada) {
                JOptionPane.showMessageDialog(this, 
                    "Tarea creada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Limpiar campos
                limpiarCampos();
                
                // Actualizar la lista
                actualizarListaTareas();
                
                // Incrementar el spinner para el siguiente ID
                spiIdentificadorTarea.setValue(identificador + 1);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo agregar la tarea", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El presupuesto debe ser un número válido", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Limpiar los campos del formulario
    private void limpiarCampos() {
        txtNombreTarea.setText("");
        txtPresupuestoTarea.setText("");
        cboCategoriaTarea.setSelectedIndex(0);
        cboPrioridad.setSelectedIndex(0);
    }
    
    // R2: Actualizar la lista de tareas con presupuesto ajustado
    private void actualizarListaTareas() {
        String listado = colaTareas.obtenerTareasConPresupuestoAjustado();
        txtListarTarea.setText(listado);
        txtListarTarea.setCaretPosition(0); // Scroll al inicio
    }
    
    // R3: Actualizar presupuestos por categoría
    private void actualizarPresupuestosPorCategoria() {
        String categoriaSeleccionada = (String) cboCategoriaBusqueda.getSelectedItem();
        
        float presupuestoTotal = colaTareas.calcularTotalPresupuesto(categoriaSeleccionada);
        float presupuestoAjustado = colaTareas.calcularTotalPresupuestoAjustado(categoriaSeleccionada);
        
        lblPresupuestoCategoria.setText(String.format("$%.2f", presupuestoTotal));
        lblPresupuestoAjustadoCategoria.setText(String.format("$%.2f", presupuestoAjustado));
    }
    
    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        // Usar el look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crear y mostrar la ventana en el EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Ventana();
            }
        });
    }
}
