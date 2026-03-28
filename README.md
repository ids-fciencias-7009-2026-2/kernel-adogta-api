# Plataforma de Adopción de Perros y Gatos: Adogta.
El proyecto consiste en el desarrollo de una plataforma web diseñada para facilitar la adopción responsable de perros y gatos, conectando a personas que desean dar en adopción animales con aquellas interesadas en adoptar.


## Práctica 1.

Durante esta práctica se desarrollaron los primeros endpoints, DTOs y la creación de un modelo de dominio siguiendo las buenas prácticas de arquitectura de software. Se realizó la simulación de la lógica de negocio con datos simulados, sin utilizar una base de datos ni seguridad real. Lo presentado está más enfocado en la estructura y peticiones HTTP.

Vease el [video de las pruebas en Postman](https://drive.google.com/file/d/1TSaAoty12fsXyJfyKjwly2t6WDPIjRic/view?usp=sharing)

## Práctica 2.

Durante esta práctica se desarrollaron los ajustes al archivo domain para que concuerde con la estructura de la base de datos además de crear la conexión con la misma.

Para ejecutar la base de datos se tienen los siguientes comandos:

```bash
   docker run --name Nombre -e POSTGRES_USER=Usuario -e POSTGRES_PASSWORD=badpassword -p 5432:5432 -d postgres
```

Además agregar al .env local:

```bash
   URL_DB=localhost:5432/Nombre
   USER_DB=Usuario
   PASSWORD_DB=badpassword
```

## Práctica 3.
 
Durante esta práctica se entrega el modelo de base de datos completo que se desarrolló, incluyendo el diagrama entidad-relación (ER) y los scripts SQL necesarios para construir y poblar la base de datos.
 
### Construcción de la Base de Datos
 
Para construir la base de datos desde cero, sigue estos pasos en orden:
 
#### 1. Conectarse a la Base de Datos
 
Primero, asegúrate de que tu contenedor de PostgreSQL esté en ejecución y conéctate a la base de datos:
 
```bash
docker exec -it Nombre psql -U Usuario -d postgres
```
 
O si prefieres usar un cliente gráfico como pgAdmin o DBeaver, utiliza las credenciales configuradas previamente.
 
#### 2. Ejecutar el DDL (Data Definition Language)
 
El archivo `DDL.sql` contiene la definición de la estructura de la base de datos (tablas, relaciones, constraints, índices).
 
**Desde psql (dentro del contenedor):**
```sql
\i /ruta/al/archivo/DDL.sql
```
 
**Desde línea de comandos (fuera del contenedor):**
```bash
docker exec -i Nombre psql -U Usuario -d postgres < DDL.sql
```

#### 3. Cargar Datos de Prueba con el DML (Data Manipulation Language)
 
El archivo `DML.sql` contiene datos de prueba, incluyendo un usuario.
 
**Desde psql (dentro del contenedor):**
```sql
\i /ruta/al/archivo/DML.sql
```
 
**Desde línea de comandos (fuera del contenedor):**
```bash
docker exec -i Nombre psql -U Usuario -d postgres < DML.sql
```

#### 4. Verificar la Instalación con Queries de Prueba
 
El archivo `Querys.sql` contiene consultas de verificación para asegurar que la base de datos se haya construido correctamente.
 
**Desde psql (dentro del contenedor):**
```sql
\i /ruta/al/archivo/Querys.sql
```
 
**Desde línea de comandos (fuera del contenedor):**
```bash
docker exec -i Nombre psql -U Usuario -d postgres < Querys.sql
```
 
### Credenciales del Usuario de Prueba
 
Una vez ejecutado el DML, tendrás disponible el siguiente usuario para pruebas:
 
- **Email:** admin@adogta.com
- **Contraseña:** 1234
- **Roles:** Adoptante y Donante
 
### Diagrama Entidad-Relación
 
El diagrama ER completo del sistema se encuentra en los archivos:
- `AdogtaER.svg` - Versión vectorial (recomendada)
- `AdogtaER.png` - Versión imagen
 
