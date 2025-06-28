import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administrador extends Persona{

    Administrador(String nombre, String apellido, String email, int dni, int telefono) {
        super(nombre, apellido, email, dni, telefono);
    }

    //Menu administrador
    public void mostrarMenu(List<Usuario> listaUsuarios, List<Trabajadores> listaTrabajadores, SistemaVehiculos sistema, Scanner scanner){
        //Hacemos un loop para el menu
        boolean menuAdmin = true;

        while (menuAdmin){
            System.out.println("\n-----MENU ADMINISTRADOR-----");
            System.out.println("1. Lista y gestion de usuarios registrados");
            System.out.println("2. Registrar usuario");
            System.out.println("3. Gestion de baja/alta a usuarios y trabajadores");
            System.out.println("4. Gestion de averias");
            System.out.println("5. Gestion de vehiculos y tarifas");
            System.out.println("0. Salir");
            System.out.println("\nSeleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            //Abrimos menu o accion segun la opcion elegida
            switch (opcion){
                case 1:
                    //Mostramos lista de usuario y las opciones de modificar sus datos o promoverlo a premium
                    mostrarUsuarios(listaUsuarios);
                    System.out.println("---------------------");
                    System.out.println("1. Modificar usuario");
                    System.out.println("2. Promover usuario a premium");
                    System.out.println("3. Volver\n");
                    int op1 = scanner.nextInt();
                    scanner.nextLine();

                    switch (op1){
                        case 1:
                            //Pedimos el DNI y llamamos al metodo de modificarDatos, pasandole la lista de todos los usuarios, el DNI y un scanner
                            System.out.println("\nIndique el DNI del usuario a modificar: ");
                            int idModificar = scanner.nextInt();
                            scanner.nextLine();
                            modificarDatos(listaUsuarios, idModificar, scanner);
                            break;

                        case 2:
                            //Pedimos el DNI y llamamos al metodo de promoverUsuario, pasandole la lista de todos los usuarios, el DNI y un scanner
                            System.out.println("\nIndique el DNI del usuario a promover: ");
                            int idPromover = scanner.nextInt();
                            scanner.nextLine();
                            promoverUsuario(listaUsuarios, idPromover);
                            break;

                        case 3:
                            //Vuelve al loop
                            break;

                        default:
                            System.out.println("\nOpcion invalida. Intente de nuevo");
                    }
                    break;

                case 2:
                    //Llama al metodo para anadir un usuario
                    anadirUsuario(listaUsuarios, scanner);
                    break;

                case 3:
                    //Llama el metodo para gestionar altas y bajas de usuarios
                    gestionAltaBaja(listaUsuarios, listaTrabajadores, scanner);
                    break;

                case 4:
                    //Creamos un loop para un menu donde se muestran vehiulos y bases averiadas, donde se pueden asignar a trabajadores para reparacion
                    boolean regresarMenu = false;
                    while (!regresarMenu) {
                        System.out.println("\n--- VEHICULOS Y BASES AVERIADAS---");
                        System.out.println("1. Ver el listado completo de vehiculos y bases averiadas");
                        System.out.println("2. Asignar reparacion de vehiculo");
                        System.out.println("3. Asignar reparacion de base");
                        System.out.println("0. Regresar\n");
                        int opcionVehiculo = scanner.nextInt();
                        scanner.nextLine();
                        switch (opcionVehiculo) {
                            case 1:
                                verElementosAveriados(sistema);
                                System.out.println("\nPresiona ENTER para continuar");
                                scanner.nextLine();
                                break;
                            case 2:
                                asignarReparacionVehiculo(sistema, listaTrabajadores, scanner);
                                break;
                            case 3:
                                asignarReparacionBase(sistema, listaTrabajadores, scanner);
                                break;
                            case 0:
                                regresarMenu = true;
                                break;
                        }
                    }
                        break;


                case 5:
                    //Menu donde se llaman 3 metodos. Donde uno anade un vehiculo, el otro modifica datos existentes de un vehiculo y el otro edita las tarifas
                    System.out.println("\n--- GESTION DE VEHICULOS ---");
                    System.out.println("1. Registrar un nuevo vehiculo");
                    System.out.println("2. Ver y modificar datos de vehiculos");
                    System.out.println("3. Ver y modificar tarifas de vehiculos");
                    System.out.println("0. Volver");
                    int opcionGestion = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcionGestion){
                        case 1:
                            anadirVehiculo(sistema, scanner);
                            break;
                        case 2:
                            modificarDatosVehiculos(sistema, scanner);
                            break;

                        case 3:
                            editarTarifas(scanner);
                            break;

                        case 0:
                            break;

                        default:
                            System.out.println("\nOpcion invalida");
                    }
                    break;

                case 0:
                    System.out.println("Cerrando sesion de administrador...\n");
                    return;

                default:
                    System.out.println("Opcion invalida. Intente de nuevo");

            }

        }

    }

    //Muestra la lista de usuarios
    public void mostrarUsuarios(List<Usuario> listaUsuarios){
        System.out.println("\nLista de usuarios:");
        for (Usuario usuario : listaUsuarios) {
            System.out.println("\nDNI: " + usuario.getDni());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Apellido: " + usuario.getApellido());
            System.out.println("Correo: " + usuario.getEmail());
            System.out.println("Tipo: " + usuario.getTipoUsuario());


        }
    }

    //Modificar datos de usuarios
       public void modificarDatos(List<Usuario> listaUsuarios, int id, Scanner scanner) {
        Usuario usuario = buscarUsuarioPorId(listaUsuarios, id);
        if (usuario != null){
            System.out.println("--------------\nModificando datos de: "+ usuario.getNombre());
            System.out.println("Nuevo nombre: ");
            usuario.nombre = scanner.nextLine();
            System.out.println("Nuevo apellido: ");
            usuario.apellido = scanner.nextLine();
            System.out.println("Nuevo email: ");
            usuario.email = scanner.nextLine();
            System.out.println("Datos modificados con exito");
        } else {
            System.out.println("Usuario no encontrado");
        }
    }

    //Muestra una lista de todos los vehiculos para elegir, y luego  modifica los datos del vehiculo seleccionado
    public void modificarDatosVehiculos(SistemaVehiculos sistema, Scanner scanner){
        List<Vehiculo> todosLosVehiculos = new ArrayList<>();

        System.out.println("\n--- LISTA DE TODOS LOS VEHICULOS ---");

        int index = 1;

        //Motos
        for (Vehiculo v : sistema.getMotos()){
            System.out.println(index + ". " + v.getNombre() + " | [" + v.getTipo() + "] | Bateria: " + v.getBateria() + "% | Disponible: " +(v.isDisponible() ? "Si" : "No") + " | Averiado: " + (v.isAveriado() ? "Si" : "No") + " | Viajes: " + v.getViajesRealizados());
            todosLosVehiculos.add(v);
            index++;
        }

        //Vehiculos en bases
        for (Base b : sistema.getBases()){
            for (Vehiculo v : b.getVehiculos()){
                System.out.println(index + ". " + v.getNombre() + " | [" + v.getTipo() + "] [" + b.getNombre() + "] | Bateria: " + v.getBateria() + "% | Disponible: " + (v.isDisponible() ? "Si" : "No") + " | Averiado: " + (v.isAveriado() ? "Si" : "No") + " | Viajes: " + v.getViajesRealizados());
                todosLosVehiculos.add(v);
                index++;
            }
        }

        //Verifica que la lista tenga vehiculos
        if (todosLosVehiculos.isEmpty()){
            System.out.println("\nNo hay vehiculos registrados.");
            return;
        }

        System.out.println("\nSeleccione el numero del vehiculo a modificar: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        if (seleccion < 1 || seleccion > todosLosVehiculos.size()){
            System.out.println("Opcion invalida");
            return;
        }

        //Apuntando al vehiculo seleccionado, se cambian sus datos uno por uno
        Vehiculo v = todosLosVehiculos.get(seleccion - 1);

        System.out.println("\n--- MODIFICANDO: " + v.getNombre() + " ---");
        System.out.println("Nuevo nombre (o presione ENTER para no cambiar)");
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.isEmpty()){
            v.setNombre(nuevoNombre);
        }

        System.out.println("Nueva bateria (0-100), actual: " + v.getBateria() +"%");
        float nuevaBateria = scanner.nextFloat();
        scanner.nextLine();
        if (nuevaBateria < 0 || nuevaBateria > 100){
            System.out.println("Opcion invalida");
            return;
        } else {
            v.setBateria(nuevaBateria);
        }

        boolean disponible = leerBoolean("Esta disponible? ", scanner);
        v.setDisponible(disponible);

        boolean averiado = leerBoolean("Esta averiado? ", scanner);
        v.setAveriado(averiado);
    }

    //Metodo para asegurar que el usuario introduzca un input correcto al pedir un boolean
    private boolean leerBoolean(String mensaje, Scanner scanner){
        while (true){
            System.out.print(mensaje + " (true/false): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("t") || input.equals("si")) {
                return true;
            }
            if (input.equals("false") || input.equals("f") || input.equals("no")) {
                return false;
            }
            if (!input.isEmpty()) {
                System.out.println("Entrada invalida. Escriba true / t / si o false / f / no.");
            }
        }
    }
    //Buscar usuario por ID
    private Usuario buscarUsuarioPorId(List<Usuario> listaUsuario, int id){
        for (Usuario usuario : listaUsuario){
            if (usuario.getDni() == id){
                return usuario;
            }
        }
        return null;
    }

    //Buscar trabajadores por ID
    private Trabajadores buscarTrabajadorPorId(List<Trabajadores> listaTrabajadores, int id){
        for (Trabajadores t : listaTrabajadores){
            if (t.getDni() == id){
                return t;
            }
        }
        return null;
    }

    //Promover usuario a premium
    public void promoverUsuario(List<Usuario> listaUsuarios, int id){
        Usuario usuario = buscarUsuarioPorId(listaUsuarios, id);
        if (usuario != null && usuario.getTipoUsuario() == Usuario.TipoUsuario.STANDARD){
            //Si el usuario elegido es STANDARD, crea un nuevo Objeto UsuarioPremium con los mismos datos del usuario elegido
            UsuarioPremium usuarioPremium = new UsuarioPremium(
                    usuario.nombre,
                    usuario.apellido,
                    usuario.email,
                    usuario.dni,
                    usuario.telefono,
                    usuario.saldo
            );
            //Apunta a la posicion original del usuario, y con .set reemplazo el usuario que habia antes, con el premium creado (con los mismos datos)
            int index = listaUsuarios.indexOf(usuario);
            listaUsuarios.set(index, usuarioPremium);
            System.out.println(usuario.getNombre() + " ha sido promovido a PREMIUM");
        } else if (usuario == null) {
            System.out.println("Usuario no encontrado");
        } else {
            System.out.println("El usuario ya es PREMIUM");
        }
    }

    //Agrega un nuevo usuario a la lista de usuarios
    private void anadirUsuario(List<Usuario> listaUsuarios, Scanner scanner){
        System.out.println("\n--- AGREGAR USUARIO ---");
        System.out.println("Nombre:");
        String nombre = scanner.nextLine();
        System.out.println("Apellido:");
        String apellido = scanner.nextLine();
        System.out.println("Email:");
        String email = scanner.nextLine();

        int dni;
        while(true){
            //Verifica que el DNI no exista y que sea solo numeros
            System.out.println("DNI (solo numeros):");
            String dniInput = scanner.nextLine();
            if (dniInput.matches("\\d+")){
                dni = Integer.parseInt(dniInput);
                boolean dniDuplicado = false;
                for (Usuario u : listaUsuarios){
                    if (u.getDni() == dni){
                        dniDuplicado = true;
                        break;
                    }
                }
                if (dniDuplicado){
                    System.out.println("Ya existe usuario con ese DNI");
                } else {
                    break;
                }

            } else {
                System.out.println("DNI invalido. Intente nuevamente");
            }
        }

        System.out.println("Telefono:");
        int telefono = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Saldo inicial:");
        double saldo = scanner.nextDouble();
        scanner.nextLine();

        //Opcion para agregar al usuario como PREMIUM desde la creacion
        boolean esPremium = leerBoolean("Desea que este usuario sea PREMIUM?", scanner);

        Usuario nuevo;
        if (esPremium){
            nuevo = new UsuarioPremium(nombre, apellido, email, dni, telefono, saldo);
            System.out.println("Usuario PREMIUM creado exitosamente!");
        } else {
            nuevo = new Usuario(nombre, apellido, email, dni, telefono, saldo);
            System.out.println("Usuario STANDARD creado exitosamente!");
        }

        listaUsuarios.add(nuevo);
    }

    //Agrega un nuevo vehiculo al sistema
    private void anadirVehiculo(SistemaVehiculos sistema, Scanner scanner){
        System.out.println("\n--- ANADIR NUEVO VEHICULO ---");

        //Elegir tipo
        Vehiculo.Tipo tipo = null;
        while (tipo == null){
            System.out.println("Seleccione el tipo de vehiculo:");
            System.out.println("1. BICICLETA");
            System.out.println("2. PATINETA");
            System.out.println("3. MOTO PEQUENA");
            System.out.println("4. MOTO GRANDE");

            int opTipo = scanner.nextInt();
            scanner.nextLine();

            switch (opTipo){
                case 1: tipo = Vehiculo.Tipo.BICICLETA; break;
                case 2: tipo = Vehiculo.Tipo.PATINETE; break;
                case 3: tipo = Vehiculo.Tipo.MOTO; break;
                case 4: tipo = Vehiculo.Tipo.MOTO_GRANDE; break;
                default:
                    System.out.println("Opcion invalida");
            }
        }

        //Datos del vehiculo y validaciones
        System.out.println("Nombre del vehiculo:");
        String nombre = scanner.nextLine();

        float bateria = 0f;
        while (true) {
            System.out.println("Nivel de bateria (0-100)");
            try {
                bateria = scanner.nextFloat();
                scanner.nextLine();
                if (bateria < 1 || bateria > 100){
                    System.out.println("\nOpcion invalida. Intente de nuevo\n");
                } else {
                    break;
                }
            } catch (Exception e){
                System.out.println("Entrada invalida. Debe ingresar un numero \n");
                scanner.nextLine();
            }

        }

        //Estados de vehiculo
        boolean disponible = leerBoolean("Esta disponible?", scanner);
        boolean averiado = leerBoolean("Esta averiado?", scanner);

        //Coordenadas
        System.out.println("Coordenada X:");
        int x = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Coordenadas Y");
        int y = scanner.nextInt();
        scanner.nextLine();

        //Viajes
        System.out.println("Viajes realizados:");
        int viajes = scanner.nextInt();
        scanner.nextLine();

        Vehiculo nuevo = new Vehiculo(tipo, nombre, bateria , disponible, averiado, x, y, viajes);

        if (requiereBase(nuevo.getTipo())){
            List<Base> bases = sistema.getBases();
            if (bases.isEmpty()){
                System.out.println("No hay bases registradas. El vehiculo no se puede anadir");
                return;
            }
            System.out.println("\nSelecciona una base:");
            for (int i = 0; i < bases.size(); i++){
                System.out.println((i + 1) + ". " + bases.get(i).getNombre());
            }

            int seleccionBase = scanner.nextInt();
            scanner.nextLine();

            if (seleccionBase >= 1 && seleccionBase <= bases.size()){
                bases.get(seleccionBase - 1).agregarVehiculo(nuevo);
                System.out.println("\nVehiculo agregado con exito!");
            } else {
                System.out.println("Seleccion invalida");
            }


        } else {
            sistema.getMotos().add(nuevo);
            System.out.println("\nVehiculo agregado con exito!");
        }

    }

    //Metodo que verifica si el vehiculo requiere una base, osea si es tipo BICICLETA o tipo PATINETE
    private boolean requiereBase(Vehiculo.Tipo tipo){
        return (tipo == Vehiculo.Tipo.BICICLETA) || (tipo ==Vehiculo.Tipo.PATINETE);
    }

    //Metodo para editar las tarifas de los vehiculos, para ambos tipos de usuarios
    private void editarTarifas(Scanner scanner){
        boolean menuTarifas = true;

        while (menuTarifas){
            //Se muestran las tarifas actuales
            Tarifas.mostrarTarifas();

            System.out.println("\n\n--- MODIFICAR TARIFAS ---");
            System.out.println("1. Bicicleta");
            System.out.println("2. Patinete");
            System.out.println("3. Moto pequena");
            System.out.println("4. Moto grande");
            System.out.println("0. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            //Se cambia el valor de las tarifas del vehiculo seleccionado
            switch (opcion){
                case 1:
                    System.out.println("\nNueva tarifa para Bicicleta STANDARD: ");
                    Tarifas.biciStandard = scanner.nextDouble();
                    System.out.println("\nNueva tarifa para Bicicleta PREMIUM: ");
                    Tarifas.biciPremium = scanner.nextDouble();
                    scanner.nextLine();
                    break;

                case 2:
                    System.out.println("\nNueva tarifa para Patinete STANDARD: ");
                    Tarifas.patinStandard = scanner.nextDouble();
                    System.out.println("\nNueva tarifa para Patinete PREMIUM: ");
                    Tarifas.patinPremium = scanner.nextDouble();
                    scanner.nextLine();
                    break;

                case 3:
                    System.out.println("\nNueva tarifa para Moto Pequena STANDARD: ");
                    Tarifas.motoStandard = scanner.nextDouble();
                    System.out.println("\nNueva tarifa para Moto Pequena PREMIUM: ");
                    Tarifas.motoPremium = scanner.nextDouble();
                    scanner.nextLine();
                    break;

                case 4:
                    System.out.println("\nNueva tarifa para Moto Grande STANDARD: ");
                    Tarifas.motoGrandeStandard = scanner.nextDouble();
                    System.out.println("\nNueva tarifa para Moto Grande PREMIUM: ");
                    Tarifas.motoGrandePremium = scanner.nextDouble();
                    scanner.nextLine();
                    break;

                case 0:
                    menuTarifas = false;
                    break;

                default:
                    System.out.println("\nOpcion invalida...");
            }
        }
    }

    //Menu donde se pueden ver usuarios y trabajadores dados de alta o de baja, y cambiar su estado si es necesario
    private void gestionAltaBaja(List<Usuario> listaUsuarios, List<Trabajadores> listaTrabajadores, Scanner scanner){
        boolean subMenu = true;

        while (subMenu){
            System.out.println("\n--- GESTION DE ALTAS Y BAJAS DE USUARIOS ---");
            System.out.println("1. Ver usuarios activos");
            System.out.println("2. Ver usuarios inactivos");
            System.out.println("3. Cambiar estado de usuario");
            System.out.println("4. Ver trabajadores activos");
            System.out.println("5. Ver trabajadores inactivos");
            System.out.println("6. Cambiar estado de trabajador");
            System.out.println("0. Volver");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                //En los casos 1 y 2 se filtran usuarios por activos o no y se muestran en pantalla
                case 1:
                    System.out.println("\n--- USUARIOS ACTIVOS ---");
                    for (Usuario u : listaUsuarios){
                        if (u.isActivo()){
                            System.out.println(u.getNombre() + " " + u.getApellido() + " -- DNI: " + u.getDni());
                        }
                    }
                    break;

                case 2:
                    System.out.println("\n--- USUARIOS DE BAJA ---");
                    for (Usuario u : listaUsuarios){
                        if (!u.isActivo()){
                            System.out.println(u.getNombre() + " " + u.getApellido() + " -- DNI: " + u.getDni());
                        }
                    }
                    break;

                case 3:
                    System.out.println("\nIngrese el DNI del usuario para dar de alta o baja:");
                    int dniUsuario = scanner.nextInt();
                    scanner.nextLine();
                    Usuario usuario = buscarUsuarioPorId(listaUsuarios, dniUsuario);
                    if (usuario != null){
                        //Se busca al usuario, se valida y cambia su estado por el contrario del actual
                        usuario.setActivo(!usuario.isActivo());
                        String estado = usuario.isActivo() ? "activo" : "inactivo";
                        System.out.println("\nUsuario actualizado. Estado actual: " + estado);
                    } else {
                        System.out.println("Usuario no encontrado.");
                    }
                    break;

                    //En los casos 4 y 5 se hace lo mismo que en el 1 y 2 pero con la lista de trabajadores
                case 4:
                    System.out.println("\n--- TRABAJADORES ACTIVOS ---");
                    for (Trabajadores t : listaTrabajadores){
                        if(t.isActivo()){
                            System.out.println(t.getNombre() + " " + t.getApellido() + " -- DNI: " + t.getDni());
                        }
                    }
                    break;

                case 5:
                    System.out.println("\n--- TRABAJADORES DE BAJA ---");
                    for (Trabajadores t : listaTrabajadores){
                        if (!t.isActivo()){
                            System.out.println(t.getNombre() + " " + t.getApellido() + " -- DNI: " + t.getDni());
                        }
                    }
                    break;

                case 6:
                    System.out.println("\nIngrese el DNI del trabajador a modificar su estado: ");
                    int dniTrabajador = scanner.nextInt();
                    scanner.nextLine();
                    Trabajadores trabajador = buscarTrabajadorPorId(listaTrabajadores, dniTrabajador);
                    if (trabajador != null){
                        //Se busca al trabajador, se valida y cambia su estado por el contrario del actual
                        trabajador.setActivo(!trabajador.isActivo());
                        String estado = trabajador.isActivo() ? "activo" : "inactivo";
                        System.out.println("\nTrabajador actualizado. Estado actual: " + estado);
                    } else {
                        System.out.println("Trabajador no encontrado");
                    }
                    break;

                case 0:
                    subMenu = false;
                    break;

                default:
                    System.out.println("\nOpcion invalida");
            }
        }
    }
    //Listar vehiculos y bases averiadas
    //Se filtra por averiado o no y se muestra en pantalla
    private void verElementosAveriados(SistemaVehiculos sistema){
        System.out.println("\n--- VEHICULOS AVERIADOS ---");
        for (Vehiculo v : sistema.getMotos()){
            if (v.isAveriado()) {
                System.out.println("- " + v.getNombre() + "( " + v.getTipo() + ")");
            }
        }

        for (Base b : sistema.getBases()){
            for (Vehiculo v : b.getVehiculos()){
                if (v.isAveriado()) {
                    System.out.println("- " + v.getNombre() + "( " + v.getTipo() + ") en base: " + b.getNombre());
                }
            }
        }

        System.out.println("\n--- BASES AVERIADAS ---");

        for (Base b : sistema.getBases()){
            if (b.isAveriada()) {
                System.out.println("- " + b.getNombre());
            }
        }
    }

    //Muestra los vehiculos averiados y da la opcion de asignar su reparacion a los distintos trabajadores activos
    private void asignarReparacionVehiculo(SistemaVehiculos sistema, List<Trabajadores> trabajadores, Scanner scanner){
        List<Vehiculo> averiados = new ArrayList<>();

        for (Vehiculo v : sistema.getMotos()){
            if (v.isAveriado()){
                averiados.add(v);
            }
        }

        for (Base b : sistema.getBases()){
            for (Vehiculo v : b.getVehiculos()){
                if (v.isAveriado()){
                    averiados.add(v);
                }
            }
        }

        if (averiados.isEmpty()){
            System.out.println("NO HAY VEHICULOS AVERIADOS.\n");
            return;
        }

        System.out.println("Vehiculos averiados: ");
        for (int i = 0; i < averiados.size(); i++){
            System.out.println((i + 1) + ". " + averiados.get(i).getNombre());
        }

        System.out.println("\nSeleccione un vehiculo: ");
        int idx = scanner.nextInt();
        scanner.nextLine();

        if (idx < 1 || idx > averiados.size()){
            System.out.println("Opcion invalida");
            return;
        }

        Vehiculo seleccionado = averiados.get(idx - 1);

        //Mostrar trabajadores de mantenimiento para asignar
        System.out.println("\n --- Trbajadores de MANTENIMIENTO:");
        List<Trabajadores> mantenimiento = new ArrayList<>();
        for (Trabajadores t : trabajadores){
            if (t.getTipo().equalsIgnoreCase("mantenimiento")){
                mantenimiento.add(t);
            }
        }

        if (mantenimiento.isEmpty()){
            System.out.println("No hay trabajadores de mantenimiento disponibles.");
            return;
        }

        for (int i = 0; i < mantenimiento.size(); i++){
            System.out.println((i + 1) + ". " + mantenimiento.get(i).getNombre());
        }

        System.out.println("------------------------\nSelecciona el trabajador para recoger un vehiculo: ");
        int idxTrabajador = scanner.nextInt();
        scanner.nextLine();

        if (idxTrabajador < 1 || idxTrabajador > mantenimiento.size()){
            System.out.println("Opcion invalida");
            return;
        }

        Trabajadores recogedor = mantenimiento.get(idxTrabajador - 1);
        recogedor.asignarVehiculo(seleccionado);
        System.out.println("Vehiculo " + seleccionado.getNombre() + " asignado a " + recogedor.getNombre() + " para recogerla");
        System.out.println("Presione ENTER para continuar...");
        scanner.nextLine();

        //Y asignar al mecanico, ya que se le asigna a ambos si es un vehiculo con averia
        System.out.println("\n--- Trabajadores MECANICOS");
        List<Trabajadores> mecanicos = new ArrayList<>();
        for (Trabajadores t : trabajadores){
            if (t.getTipo().equalsIgnoreCase("mecanico")){
                mecanicos.add(t);
            }
        }

        if (mecanicos.isEmpty()){
            System.out.println("\nNo hay mecanicos disponibles\n");
            System.out.println("Presione ENTER para continuar...");
            scanner.nextLine();
            return;
        }

        for (int i = 0; i < mecanicos.size(); i++){
            System.out.println((i + 1) + ". " + mecanicos.get(i).getNombre());
        }

        System.out.println("------------------------\nSeleccione un mecanico para su reparacion");
        int idxMecanico = scanner.nextInt();
        scanner.nextLine();

        if (idxMecanico < 1 || idxMecanico > mecanicos.size()){
            System.out.println("\nOpcion invalida.");
            return;
        }

        Trabajadores reparador = mecanicos.get(idxMecanico - 1);
        reparador.asignarVehiculo(seleccionado);
        System.out.println("Vehiculo " + seleccionado.getNombre() + " asignado a " + recogedor.getNombre() + " para su reparacion.");
        System.out.println("Presione ENTER para continuar...");
        scanner.nextLine();

    }

    //Parecido a asignar reparacion de vehiculos, pero esta solo se asigna a mecanicos
    private void asignarReparacionBase(SistemaVehiculos sistema, List<Trabajadores> trabajadores, Scanner scanner){
        List<Base> basesAveriadas = new ArrayList<>();
        for (Base b : sistema.getBases()){
            if (b.isAveriada()) {
                basesAveriadas.add(b);
            }
        }

        if (basesAveriadas.isEmpty()){
            System.out.println("\nNo hay bases averiadas\n");
            return;
        }

        System.out.println("\nBases averiadas: ");
        for (int i = 0; i < basesAveriadas.size(); i++){
            System.out.println((i + 1) + ". " + basesAveriadas.get(i).getNombre());
        }

        System.out.println("\n------------------------\nSeleccione una base: ");
        int idx = scanner.nextInt();
        scanner.nextLine();

        if (idx < 1 || idx > basesAveriadas.size()){
            System.out.println("Opcion invalida\n");
            return;
        }

        Base base = basesAveriadas.get(idx - 1);


        //Asignar unicamente a mecanico
        System.out.println("\nMecanicos disponibles: ");
        List<Trabajadores> mecanicos = new ArrayList<>();
        for (Trabajadores t : trabajadores){
            if (t.getTipo().equalsIgnoreCase("mecanico")){
                mecanicos.add(t);
            }
        }

        if (mecanicos.isEmpty()){
            System.out.println("No hay mecanicos disponibles");
            return;
        }

        for (int i = 0; i < mecanicos.size(); i++ ){
            System.out.println((i + 1) + ". " + mecanicos.get(i).getNombre());
        }

        System.out.println("\n------------------------\nSeleccione el mecanico para reparar la base:");
        int idxMecanico = scanner.nextInt();
        scanner.nextLine();

        if (idxMecanico < 1 || idxMecanico > mecanicos.size()){
            System.out.println("Opcion invalida\n");
            return;
        }

        Trabajadores t = mecanicos.get(idxMecanico - 1);
        t.asignarBase(base);
        System.out.println("Base " + base.getNombre() + " asignada a " + t.getNombre() + " para reparacion");
        System.out.println("Presione ENTER para continuar...");
        scanner.nextLine();

    }
}
