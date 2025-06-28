# Sistema de Alquiler de Vehículos - Proyecto Java

## Visión General

Esta aplicación Java es un sistema completo de alquiler de vehículos que permite a los usuarios alquilar diversos tipos de vehículos (bicicletas, patinetes, motocicletas), mientras proporciona a administradores y trabajadores de mantenimiento herramientas para gestionar el sistema. El proyecto incluye diferentes roles de usuario, tipos de vehículos, sistemas de reserva y flujos de trabajo de mantenimiento.

---

## Características Principales

### Roles de Usuario

- **Usuarios Estándar**: Alquilar vehículos, reportar problemas, ver historial de viajes  
- **Usuarios Premium**: Funciones exclusivas como reservas anticipadas (20 minutos)  
- **Administradores**: Gestionar usuarios, vehículos, tarifas y reparaciones  
- **Trabajadores de Mantenimiento**: Manejar reparaciones de vehículos y mantenimiento de bases  
- **Mecánicos**: Funciones de reparación especializadas y facturación  

### Tipos de Vehículos

- Bicicletas  
- Patinetes  
- Motocicletas Pequeñas  
- Motocicletas Grandes  

### Funcionalidades Principales

- Registro y autenticación de usuarios  
- Sistema de búsqueda y alquiler de vehículos  
- Sistema de reservas anticipadas (usuarios Premium)  
- Flujos de trabajo de mantenimiento para vehículos y bases  
- Sistema de gestión de tarifas  
- Seguimiento del historial de viajes  
- Transacciones financieras y gestión de saldo  

---

## Estructura de Clases (Resumen)

### Clases Principales

- `movilidad.java`: Punto de entrada principal de la aplicación  
- `Persona.java`: Clase base para todos los actores del sistema  
- `Usuario.java`: Implementación de usuario estándar  
- `UsuarioPremium.java`: Usuario premium con capacidades de reserva  
- `Administrador.java`: Funciones de administrador del sistema  
- `Trabajadores.java`: Trabajadores de mantenimiento y mecánicos  
- `Vehiculo.java`: Representación de vehículos con tipos y estados  
- `Base.java`: Estaciones/ubicaciones de almacenamiento de vehículos  
- `SistemaVehiculos.java`: Sistema central de gestión de vehículos  
- `Viajes.java`: Gestión y seguimiento de viajes  
- `Reserva.java`: Sistema de reserva de vehículos  
- `Tarifas.java`: Gestión de tarifas  
- `Ciudad.java`: Límites de la ciudad y validación de coordenadas  

---

## Cómo Ejecutar

1. Asegúrate de tener Java JDK 11+ instalado  
2. Clona el repositorio:

```bash
git clone https://github.com/CheleMoreno/AlquilerVehiculos.git
```

3. Navega al directorio del proyecto  
4. Compila todos los archivos Java:

```bash
javac *.java
```

5. Ejecuta la aplicación:

```bash
java movilidad
```

---

## Ejemplos de Uso

### Como Usuario

- Inicia sesión con las credenciales proporcionadas (ej. `Isa`, DNI: `123`)  
- Busca vehículos disponibles  
- Alquila un vehículo y completa tu viaje  
- Reporta problemas con vehículos o bases  
- Consulta tu historial de viajes y saldo  

### Como Administrador

- Inicia sesión con credenciales de administrador (ej. `admin`, DNI: `1`)  
- Gestiona usuarios (crear, modificar, promover a premium)  
- Maneja asignaciones de mantenimiento de vehículos  
- Ajusta las tarifas de alquiler  
- Monitorea el estado del sistema  

---

## Aspectos Técnicos Destacados

- Diseño orientado a objetos con herencia y polimorfismo  
- Sistema de control de acceso basado en roles  
- Sistema de reservas basado en tiempo con expiración  
- Validación de coordenadas para devolución de vehículos  
- Manejo exhaustivo de excepciones  
- Gestión de estados de vehículos (disponible, alquilado, en reparación)  

---

## Contribuidores

Sebastian Moreno

---

## Licencia

Este proyecto está licenciado bajo la Licencia MIT.
