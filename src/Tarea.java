public class Tarea implements Comparable<Tarea> {
    private int identificador;
    private String nombre;
    private String categoria;
    private float presupuestoNecesario;
    private int prioridad;

    // Constantes para categorías
    public static final String CATEGORIA_ADMINISTRATIVA = "Administrativa";
    public static final String CATEGORIA_DIRECTIVA = "Directiva";
    public static final String CATEGORIA_OPERATIVA = "Operativa";

    // Constructor por defecto con valores predefinidos
    public Tarea() {
        this.identificador = 0;
        this.nombre = "Tarea sin nombre";
        this.categoria = CATEGORIA_ADMINISTRATIVA;
        this.presupuestoNecesario = 0.0f;
        this.prioridad = 1;
    }

    // Constructor con parámetros
    public Tarea(int identificador, String nombre, String categoria, float presupuestoNecesario, int prioridad) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.categoria = categoria;
        this.presupuestoNecesario = presupuestoNecesario;
        setPrioridad(prioridad); // Validación de prioridad
    }

    // Getters
    public int getIdentificador() {
        return identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public float getPresupuestoNecesario() {
        return presupuestoNecesario;
    }

    public int getPrioridad() {
        return prioridad;
    }

    // Setters
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPresupuestoNecesario(float presupuestoNecesario) {
        this.presupuestoNecesario = presupuestoNecesario;
    }

    public void setPrioridad(int prioridad) {
        if (prioridad >= 1 && prioridad <= 12) {
            this.prioridad = prioridad;
        } else {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 12");
        }
    }

    // Método para calcular presupuesto ajustado según categoría
    public float calcularPresupuestoAjustado() {
        switch (categoria) {
            case CATEGORIA_ADMINISTRATIVA:
                return presupuestoNecesario * 0.90f; // Reducción del 10%
            case CATEGORIA_DIRECTIVA:
                return presupuestoNecesario * 0.80f; // Reducción del 20%
            case CATEGORIA_OPERATIVA:
                return presupuestoNecesario * 0.95f; // Reducción del 5%
            default:
                return presupuestoNecesario;
        }
    }

    // Implementación de Comparable para ordenar por prioridad (12 más prioritario, 1 menos)
    @Override
    public int compareTo(Tarea otra) {
        // Orden descendente: prioridad mayor primero
        return Integer.compare(otra.prioridad, this.prioridad);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | Categoría: %s | Prioridad: %d | Presupuesto: $%.2f",
                identificador, nombre, categoria, prioridad, presupuestoNecesario);
    }

    // Método para mostrar con presupuesto ajustado
    public String toStringConPresupuestoAjustado() {
        float presupuestoAjustado = calcularPresupuestoAjustado();
        return String.format("ID: %d | %s | Categoría: %s | Prioridad: %d | Presupuesto: $%.2f | Presupuesto Ajustado: $%.2f",
                identificador, nombre, categoria, prioridad, presupuestoNecesario, presupuestoAjustado);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tarea tarea = (Tarea) obj;
        return identificador == tarea.identificador;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(identificador);
    }
}
