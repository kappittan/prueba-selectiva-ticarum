# Competiciones Deportivas UMU

Proyecto desarrollado por Alejandro Montoya Toro como prueba técnica para la empresa TICARUM.

Consiste en una aplicación desarrollada en Spring Boot para gestionar competiciones deportivas en la Universidad de Murcia. Permite registrar equipos en distintas competiciones y genera automáticamente la lista de partidos de la primera jornada, teniendo en cuenta el número de pistas disponibles y los días reservados. En caso de que no sea posible completar la jornada con todos los equipos, la aplicación devuelve tanto los partidos asignados como los equipos que no pueden competir.

## Tabla de contenidos

- [Instalación](#instalación)
- [Endpoints](#endpoints)
- [Tecnologías](#tecnologías)
- [Pruebas](#pruebas)
- [Notas](#notas)

## Instalación

1.  Clonar el repositorio:

```bash
 git clone https://github.com/usuario/nombre-del-proyecto.git
```

2. Acceder al directorio del proyecto

```bash
  cd nombre-del-proyecto
```

3.  Ejecutar la aplicación Spring Boot

```bash
./mvnw spring-boot:run
```

La aplicación se encontrará levantada en http://localhost:8080

## Endpoints

### Competiciones deportivas

| Método | Endpoint                                                 | Descripción                                                                                                                   |
| ------ | -------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------- |
| `POST` | `/competiciones_deportivas`                              | Crear una nueva competición deportiva.                                                                                        |
| `GET`  | `/competiciones_deportivas/{id}`                         | Obtener una competición deportiva.                                                                                            |
| `POST` | `/competiciones_deportivas/{id}/equipos`                 | Registrar un equipo en una competición deportiva.                                                                             |
| `GET`  | `/competiciones_deportivas/{id}/equipos`                 | Obtener la lista de equipos registrados en una competición.                                                                   |
| `POST` | `/competiciones_deportivas/{id}/generar_primera_jornada` | Generar automáticamente la lista de partidos pertenecientes a la primera jornada de una competición deportiva.                |
| `GET`  | `/competiciones_deportivas/{id}/jornadas/1`              | Obtener la lista de partidos de la primera jornada de una competición, junto con la lista de equipos no asignados a partidos. |

### Equipos

| Método | Endpoint   | Descripción        |
| ------ | ---------- | ------------------ |
| `POST` | `/equipos` | Registra un equipo |

## Tecnologías

- Java 17
- Spring Boot 3
- Maven
- H2
- JUnit / Mockito

## Pruebas

Bajo el directorio `/test`, en la carpeta raíz, se encuentran las pruebas unitarias implementadas. En concreto, se han creado tests para todas las clases que constituyen los servicios de la aplicación, los cuales contienen la lógica y reglas de negocio.

Estos han sido: `CompeticionDeportivaServiceImpl`, `EquipoServiceImpl`, `JornadaServiceImpl` y `PartidoServiceImpl`.

Para lanzar todos los tests, ejecuta el siguiente comando en una terminal:

```bash
./mvnw test
```

Por otro lado, ha sido necesario realizar el testing de la API REST, verificando que esta es capaz de recibir, procesar y devolver una respuesta adecuada. Para ello, se ha elaborado una colección Postman, la cual se encuentra en [`postman/competiciones_deportivas.json`](postman/competiciones_deportivas.json).

## Notas

- Existen clases relacionadas con el concepto `Pista`. Aunque no se hacen uso de ellas en esta versión de la aplicación, las he dejado reflejadas en el código para plantear una posible mejora, donde se puedan dar de altas pistas (con su información propia, como nombre, ubicación, etc.) y asociarlas a una competición deportiva. Esto permitiría asociar una pista a cada partido de cada jornada, lo que aumentaría el potencial de la aplicación.

- He separado el registro de un equipo, del registro de una competición, para que de esta manera un usuario pueda registrarse en el sistema y más tarde, cuando lo necesite, apuntarse a las competiciones deportivas que desee.
