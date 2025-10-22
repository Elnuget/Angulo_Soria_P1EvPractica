import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class ColaTareas {
    private PriorityQueue<Tarea> colaPrioridad;
    private HashSet<Integer> identificadoresUsados;

    public ColaTareas() {
        // PriorityQueue usa el compareTo de Tarea (prioridad descendente)
        colaPrioridad = new PriorityQueue<>();
        identificadoresUsados = new HashSet<>();
        
        // Inicializar con 5 objetos predefinidos
        inicializarTareasPredefinidas();
    }

    // Inicializar 5 tareas predefinidas
    private void inicializarTareasPredefinidas() {
        agregarTarea(new Tarea(1, "Actualizar sistema de nómina", Tarea.CATEGORIA_ADMINISTRATIVA, 5000.00f, 8));
        agregarTarea(new Tarea(2, "Reunión con directivos", Tarea.CATEGORIA_DIRECTIVA, 3000.00f, 12));
        agregarTarea(new Tarea(3, "Mantenimiento de equipos", Tarea.CATEGORIA_OPERATIVA, 2500.00f, 5));
        agregarTarea(new Tarea(4, "Elaborar presupuesto anual", Tarea.CATEGORIA_DIRECTIVA, 8000.00f, 11));
        agregarTarea(new Tarea(5, "Capacitación de personal", Tarea.CATEGORIA_ADMINISTRATIVA, 4500.00f, 7));
    }

    // R1: Agregar nueva tarea con validación de identificador no repetido
    public boolean agregarTarea(Tarea tarea) {
        if (identificadoresUsados.contains(tarea.getIdentificador())) {
            return false; // Identificador repetido
        }
        
        colaPrioridad.offer(tarea);
        identificadoresUsados.add(tarea.getIdentificador());
        return true;
    }

    // Validar si un identificador ya existe
    public boolean existeIdentificador(int identificador) {
        return identificadoresUsados.contains(identificador);
    }

    // Obtener todas las tareas ordenadas por prioridad (mayor a menor)
    public List<Tarea> obtenerTareasOrdenadas() {
        List<Tarea> listaTareas = new ArrayList<>(colaPrioridad);
        listaTareas.sort(null); // Usa el compareTo de Tarea
        return listaTareas;
    }

    // R2: Obtener todas las tareas con presupuesto ajustado
    public String obtenerTareasConPresupuestoAjustado() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== TAREAS CON PRESUPUESTO AJUSTADO ===\n\n");
        
        List<Tarea> tareas = obtenerTareasOrdenadas();
        for (Tarea tarea : tareas) {
            sb.append(tarea.toStringConPresupuestoAjustado()).append("\n");
        }
        
        return sb.toString();
    }

    // R3: Calcular total de presupuesto por categoría
    public float calcularTotalPresupuesto(String categoria) {
        float total = 0.0f;
        
        for (Tarea tarea : colaPrioridad) {
            if (tarea.getCategoria().equals(categoria)) {
                total += tarea.getPresupuestoNecesario();
            }
        }
        
        return total;
    }

    // R3: Calcular total de presupuesto ajustado por categoría
    public float calcularTotalPresupuestoAjustado(String categoria) {
        float total = 0.0f;
        
        for (Tarea tarea : colaPrioridad) {
            if (tarea.getCategoria().equals(categoria)) {
                total += tarea.calcularPresupuestoAjustado();
            }
        }
        
        return total;
    }

    // Obtener estadísticas completas por categoría (para R3)
    public String obtenerEstadisticasPorCategoria(String categoria) {
        float totalPresupuesto = calcularTotalPresupuesto(categoria);
        float totalPresupuestoAjustado = calcularTotalPresupuestoAjustado(categoria);
        
        return String.format("Categoría: %s\nPresupuesto Total: $%.2f\nPresupuesto Ajustado Total: $%.2f",
                categoria, totalPresupuesto, totalPresupuestoAjustado);
    }

    // Obtener cantidad de tareas
    public int cantidadTareas() {
        return colaPrioridad.size();
    }

    // Verificar si está vacía
    public boolean estaVacia() {
        return colaPrioridad.isEmpty();
    }

    // Limpiar todas las tareas
    public void limpiar() {
        colaPrioridad.clear();
        identificadoresUsados.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTA DE TAREAS (Ordenadas por Prioridad) ===\n\n");
        
        List<Tarea> tareas = obtenerTareasOrdenadas();
        for (Tarea tarea : tareas) {
            sb.append(tarea.toString()).append("\n");
        }
        
        return sb.toString();
    }
}
