import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class parcialprogramacion {

    
    public static class Materia {
        private String nombre;
        private String codigo;
        private int creditos;
        private List<String> horariosDisponibles;

        public Materia(String nombre, String codigo, int creditos) {
            this.nombre = nombre;
            this.codigo = codigo;
            this.creditos = creditos;
            this.horariosDisponibles = new ArrayList<>();
        }

        public void agregarHorario(String horario) {
            horariosDisponibles.add(horario);
        }

        public void mostrarHorariosDisponibles() {
            System.out.println("Horarios disponibles para la materia " + nombre + ":");
            for (String horario : horariosDisponibles) {
                System.out.println(horario);
            }
        }

        public List<String> getHorariosDisponibles() {
            return horariosDisponibles;
        }

        public String getNombre() {
            return nombre;
        }

        public String getCodigo() {
            return codigo;
        }

        public int getCreditos() {
            return creditos;
        }
    }

    
    public static class Estudiante {
        private String nombre;
        private String id;
        private Map<String, String> materiasInscritas; 

        public Estudiante(String nombre, String id) {
            this.nombre = nombre;
            this.id = id;
            this.materiasInscritas = new HashMap<>();
        }

        public void inscribirMateria(Materia materia, String horario) throws Exception {
            
            for (Map.Entry<String, String> entry : materiasInscritas.entrySet()) {
                String horarioExistente = entry.getValue();
                if (horario.equals(horarioExistente)) {
                    throw new Exception("El horario " + horario + " ya está ocupado con la materia " + entry.getKey());
                }
            }
            
            materiasInscritas.put(materia.getNombre(), horario);
            System.out.println("Materia " + materia.getNombre() + " inscrita en el horario " + horario);
        }

        public void mostrarHorarioCompleto() {
            System.out.println("Horario completo de " + nombre + " (ID: " + id + "):");
            for (Map.Entry<String, String> entry : materiasInscritas.entrySet()) {
                System.out.println("Materia: " + entry.getKey() + ", Horario: " + entry.getValue());
            }
        }
    }

    
    public static class GestionHorarios {
        private Map<String, Materia> materiasRegistradas;
        private Map<String, Estudiante> estudiantesRegistrados;

        public GestionHorarios() {
            materiasRegistradas = new HashMap<>();
            estudiantesRegistrados = new HashMap<>();
        }

        
        public void registrarMateria(Materia materia) {
            materiasRegistradas.put(materia.getCodigo(), materia);
            System.out.println("Materia " + materia.getNombre() + " registrada.");
        }

        
        public void registrarEstudiante(Estudiante estudiante) {
            estudiantesRegistrados.put(estudiante.id, estudiante);
            System.out.println("Estudiante " + estudiante.nombre + " registrado.");
        }

        
        public void inscribirEstudianteEnMateria(String idEstudiante, String codigoMateria, String horario) {
            Estudiante estudiante = estudiantesRegistrados.get(idEstudiante);
            Materia materia = materiasRegistradas.get(codigoMateria);
            if (estudiante != null && materia != null) {
                try {
                    
                    if (materia.getHorariosDisponibles().contains(horario)) {
                        estudiante.inscribirMateria(materia, horario);
                    } else {
                        System.out.println("Horario " + horario + " no disponible para la materia " + materia.getNombre());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Estudiante o materia no encontrados.");
            }
        }

        
        public void mostrarHorariosEstudiantes() {
            for (Estudiante estudiante : estudiantesRegistrados.values()) {
                estudiante.mostrarHorarioCompleto();
            }
        }
    }

    public static void main(String[] args) {
       
        GestionHorarios gestionHorarios = new GestionHorarios();

        
        Materia matematica = new Materia("Matematica", "MAT101", 4);
        matematica.agregarHorario("Lunes 8:00 AM");
        matematica.agregarHorario("Miércoles 10:00 AM");
        gestionHorarios.registrarMateria(matematica);

        Materia fisica = new Materia("Fisica", "FIS101", 3);
        fisica.agregarHorario("Martes 9:00 AM");
        fisica.agregarHorario("Jueves 11:00 AM");
        gestionHorarios.registrarMateria(fisica);

        
        Estudiante juan = new Estudiante("Juan Perez", "12345");
        gestionHorarios.registrarEstudiante(juan);

       
        gestionHorarios.inscribirEstudianteEnMateria("12345", "MAT101", "Lunes 8:00 AM");
        gestionHorarios.inscribirEstudianteEnMateria("12345", "FIS101", "Martes 9:00 AM");

        
        gestionHorarios.inscribirEstudianteEnMateria("12345", "MAT101", "Lunes 8:00 AM"); // Conflicto de horario

    
        gestionHorarios.mostrarHorariosEstudiantes();
    }
}
