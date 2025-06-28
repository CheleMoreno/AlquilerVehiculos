import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class movilidad {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        //Creamos lista de todos los usuarios (premium y/o estandard) y trabajadores
        List<Usuario> listaUsuarios = new ArrayList<>();
        List<Trabajadores> listaTrabajadores = new ArrayList<>();

        //Agregar un nuevo usuario (estandard por defecto)
        listaUsuarios.add(new Usuario("Isa", "Riguero", "isariguero@uned.es", 123, 656231356, 125.00));
        listaUsuarios.add(new Usuario("Sebastian", "Moreno", "sebasmore@uned.es", 54849102, 623792345, 422.00));
        listaUsuarios.add(new Usuario("Lucia", "Fernandez", "luciafern@uned.es", 54832910, 611223344, 200.50));
        listaUsuarios.add(new Usuario("Carlos", "Gomez", "cargomez@uned.es", 54782111, 622334455, 180.00));
        listaUsuarios.add(new Usuario("Marta", "Lopez", "martalo@uned.es", 54679222, 633445566, 95.75));
        listaUsuarios.add(new Usuario("Javier", "Alonso", "javalonso@uned.es", 54568333, 644556677, 310.00));
        listaUsuarios.add(new Usuario("Elena", "Ruiz", "elenaruiz@uned.es", 54457444, 655667788, 250.25));
        listaUsuarios.add(new Usuario("Diego", "Serrano", "diegoser@uned.es", 54346555, 666778899, 135.00));
        listaUsuarios.add(new Usuario("Sara", "Navarro", "saranava@uned.es", 54235666, 677889900, 400.00));
        listaUsuarios.add(new Usuario("Pablo", "Iglesias", "pabigle@uned.es", 54124777, 688990011, 220.00));
        listaUsuarios.add(new Usuario("Ana", "Martinez", "anamart@uned.es", 54013888, 699101112, 175.00));
        listaUsuarios.add(new Usuario("Hugo", "Castro", "hugocas@uned.es", 53902999, 611212131, 290.00));
        listaUsuarios.add(new Usuario("Claudia", "Diaz", "claudiaz@uned.es", 53892010, 622323242, 199.99));
        listaUsuarios.add(new Usuario("Mario", "Sanchez", "mariosan@uned.es", 53781121, 633434353, 320.00));
        listaUsuarios.add(new Usuario("Laura", "Ramirez", "lauraram@uned.es", 53670232, 644545464, 105.60));

        //Agregar usuarios premium directamente
        listaUsuarios.add(new UsuarioPremium("Melissa", "Santos", "melisan@uned.es", 54986545, 324798923, 750.00));
        listaUsuarios.add(new UsuarioPremium("Alejandro", "Torres", "aletorres@uned.es", 53559343, 655123456, 1200.00));
        listaUsuarios.add(new UsuarioPremium("Natalia", "Reyes", "natreyes@uned.es", 53448454, 644234567, 980.00));
        listaUsuarios.add(new UsuarioPremium("Andres", "Molina", "andmolina@uned.es", 53337565, 633345678, 1105.00));
        listaUsuarios.add(new UsuarioPremium("Carmen", "Gil", "cargil@uned.es", 53226676, 622456789, 890.90));
        listaUsuarios.add(new UsuarioPremium("Lucas", "Herrera", "lucherrera@uned.es", 53115787, 611567890, 1300.50));
        listaUsuarios.add(new UsuarioPremium("Patricia", "Campos", "patcampos@uned.es", 53004898, 600678901, 1025.75));

        // Trabajadores - Mecánicos
        listaTrabajadores.add(new Trabajadores("Carlos", "Herrera", "carlosh@uned.es", 48291374, 612345678, "mecanico"));
        listaTrabajadores.add(new Trabajadores("Lucia", "Ramirez", "luciar@uned.es", 39182746, 678912345, "mecanico"));
        listaTrabajadores.add(new Trabajadores("Jorge", "Fernandez", "jorgef@uned.es", 58392017, 699876543, "mecanico"));
        listaTrabajadores.add(new Trabajadores("Sofia", "Martínez", "sofiam@uned.es", 47281935, 634567890, "mecanico"));

        // Trabajadores - Mantenimiento
        listaTrabajadores.add(new Trabajadores("Alberto", "Sanz", "albersanz@uned.es", 39847261, 612389456, "mantenimiento"));
        listaTrabajadores.add(new Trabajadores("Nuria", "Lopez", "nurial@uned.es", 50938274, 687654321, "mantenimiento"));
        listaTrabajadores.add(new Trabajadores("Pedro", "Gomez", "pedrog@uned.es", 43981725, 699112233, "mantenimiento"));
        listaTrabajadores.add(new Trabajadores("Raquel", "Morales", "raqmor@uned.es", 57483920, 645789321, "mantenimiento"));
        listaTrabajadores.add(new Trabajadores("Daniel", "Vega", "danvega@uned.es", 48291038, 677889900, "mantenimiento"));


        //Creamos lista de administradores
        List<Administrador> listaAdmins = new ArrayList<>();

        //Agregar admins a la lisa
        listaAdmins.add(new Administrador("admin", "root", "admin", 1, 0));
        listaAdmins.add(new Administrador("Miguel", "Jardin", "miguejar@uned.admin.es", 57979410, 626794502));
        listaAdmins.add(new Administrador("Andrea", "Salas", "andresalas@uned.admin.es", 48291375, 611234567));
        listaAdmins.add(new Administrador("Fernando", "Delgado", "ferdelgado@uned.admin.es", 58392018, 678912346));
        listaAdmins.add(new Administrador("Claudia", "Nuñez", "claununez@uned.admin.es", 39182747, 699876544));
        listaAdmins.add(new Administrador("Roberto", "Iglesias", "robiglesias@uned.admin.es", 47281936, 634567891));


        //Creamos los vehiculos
        SistemaVehiculos sistemaVehiculos = new SistemaVehiculos();

        //Hacemos un loop para el menu
        while (true) {
            //Login
            System.out.println("--- Bienvenido! ---\nIngrese su nombre: ");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese su DNI: ");
            String dniInput = scanner.nextLine();

            //Verificar si el DNI contiene solo numeros
            if (!dniInput.matches("\\d+")){
                System.out.println("Error: el DNI debe contener solamente numeros. Intente nuevamente. \n");
                continue;
            }
            int codigo = Integer.parseInt(dniInput);
            boolean loginExitoso = false;

            //Verificacion admin buscando nombre y DNI
            for (Administrador a : listaAdmins){
                if (a.getNombre().equalsIgnoreCase(nombre) && codigo == a.getDni()){
                    a.mostrarMenu(listaUsuarios, listaTrabajadores, sistemaVehiculos, scanner);
                    loginExitoso = true;
                    break;
                }
            }

            //Verificacion trabajador buscando nombre y DNI
            if (!loginExitoso) {
                for (Trabajadores t : listaTrabajadores) {
                    if (t.getNombre().equalsIgnoreCase(nombre) && codigo == t.getDni()) {
                        t.mostrarMenuTrabajadores(scanner);
                        loginExitoso = true;
                        break;
                    }
                }
            }

            //Verificacion usuario buscando nombre y DNI
            if (!loginExitoso) {
                for (Usuario u : listaUsuarios) {
                    if (u.getNombre().equalsIgnoreCase(nombre) && (codigo == u.getDni())) {
                        u.mostrarMenu(scanner, sistemaVehiculos);
                        loginExitoso = true;
                        break;
                    }
                }
            }

            if (!loginExitoso){
                System.out.println("\n------------------------------\nNombre o DNI incorrectos. Intente nuevamente\n");
            }

        }

    }
}

