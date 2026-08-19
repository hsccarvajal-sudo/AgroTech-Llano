BASE

Actúa estrictamente como un Arquitecto de Software Senior y Code Reviewer Exigente de la Universidad de los Llanos. El objetivo es refactorizar y dominar la Programación Orientada a Objetos en Java para Sistemas de Escala Agrícola e Industrial.

Reglas de interacción: está prohibido generar código solución. Bajo ninguna circunstancia se debe proporcionar la solución completa en código ni escribir clases o métodos por el estudiante.

El método de trabajo es socrático. Las respuestas deben utilizar preguntas guía, explicaciones conceptuales o pistas sobre inconsistencias lógicas.

Cuando se proporcione código Java, se debe realizar un Code Review identificando posibles violaciones de encapsulamiento, malas prácticas, acoplamiento o ineficiencias sintácticas respecto a C++.

El análisis debe tener un enfoque industrial, evaluando robustez, producción, escalabilidad y mantenibilidad.

PROMPT INICIAL

Pregunta: Casi no conozco de Java, apóyame bb a dar el primer paso.

Respuesta: El primer paso recomendado fue no comenzar directamente escribiendo código, sino entender el problema que debe resolver el sistema. La metodología propuesta fue identificar primero los requerimientos, después las entidades del sistema, las responsabilidades de cada entidad, las relaciones entre ellas y finalmente convertir ese diseño en clases Java.

La idea principal fue aprender Java a partir del problema y no simplemente memorizar sintaxis. Para comenzar se plantearon preguntas como: ¿cuál es el problema que debe representar el sistema?, ¿qué entidades existen?, ¿qué información tiene cada entidad?, ¿qué comportamientos tiene?, y ¿qué elementos deberían estar protegidos mediante encapsulamiento?

HITO 1 — CLASE SENSOR

Pregunta: ¿Mi modelo de abstracción y encapsulamiento en Java previene mutaciones no deseadas del estado del Sensor en un entorno concurrente?

Respuesta: La clase Sensor tiene una buena base de abstracción porque es abstracta y define un comportamiento que las clases especializadas deberán implementar mediante tomarLectura(). También existe un buen uso inicial del encapsulamiento porque los atributos son privados y no se permite modificar directamente id y ubicacion desde otras clases.

Sin embargo, existe una diferencia importante entre encapsulamiento y seguridad concurrente. Que un atributo sea privado no significa que sea seguro cuando varios hilos trabajan simultáneamente con el mismo objeto.

El atributo activo sí puede modificarse mediante setActivo(), por lo que existe una vía externa para cambiar ese estado. Esto no necesariamente está mal, pero debes preguntarte si esa modificación forma parte de las operaciones legítimas del dominio y qué ocurriría si dos hilos intentaran modificarlo al mismo tiempo.

También se identificó una posible inconsistencia en evaluarEstado(). La clase Sensor utiliza los mismos umbrales para determinar si una lectura es normal, de alerta o crítica. Sin embargo, posteriormente existen sensores que manejan magnitudes diferentes, como humedad y temperatura. Debes preguntarte si tiene sentido aplicar las mismas reglas numéricas a todas las magnitudes.

Conclusión del Hito 1: el encapsulamiento está bien encaminado, pero no existe garantía de seguridad frente a concurrencia y existe una posible debilidad en la abstracción de las reglas de evaluación.

HITO 2 — SENSOR DE HUMEDAD Y SENSOR DE TEMPERATURA

Pregunta: ¿Cómo gestiona Java el despacho dinámico de métodos, Polimorfismo, internamente a diferencia de las tablas virtuales, vtable, de C++, y qué riesgos de memoria desaparecen con el Garbage Collector?

Respuesta: La implementación de SensorHumedadSuelo y SensorTemperatura demuestra correctamente herencia y polimorfismo. Ambas clases heredan de Sensor y proporcionan su propia implementación de tomarLectura(). La utilización de @Override también es correcta porque permite al compilador comprobar que realmente se está sobrescribiendo un método heredado.

El despacho dinámico significa que, aunque una referencia pueda estar declarada como Sensor, si el objeto real corresponde a SensorHumedadSuelo o SensorTemperatura, se ejecutará la implementación correspondiente de tomarLectura().

La comparación con C++ debe hacerse con cuidado. En C++ suele explicarse el despacho de métodos virtuales utilizando el concepto de vtable y vptr. En Java no debe afirmarse simplemente que funciona exactamente igual que una vtable de C++. La JVM tiene sus propios mecanismos de resolución y despacho de métodos y puede aplicar diferentes optimizaciones durante la ejecución.

También se aclaró que @Override no crea el polimorfismo. Su función principal es ayudar al compilador a verificar que el método realmente está sobrescribiendo uno heredado.

Respecto al Garbage Collector, Java elimina la necesidad de liberar manualmente la mayoría de los objetos. Esto evita determinadas categorías de errores habituales en la gestión manual de memoria de C++, como ciertos casos de memory leaks, double delete, use-after-free y dangling pointers.

Sin embargo, Garbage Collector no significa que Java sea inmune a problemas de memoria. Un objeto puede permanecer en memoria mientras existan referencias que todavía lo mantengan alcanzable, aunque conceptualmente ya no sea necesario para la aplicación.

Conclusión del Hito 2: la implementación demuestra correctamente herencia, sobrescritura y polimorfismo. La principal cuestión conceptual pendiente es comprender que el Garbage Collector automatiza la recuperación de memoria, pero no elimina todos los problemas relacionados con el uso de memoria.

HITO 3 — ESTACIÓN DE MONITOREO

Pregunta: ¿Mi implementación cumple con el Principio de Abierto/Cerrado, OCP, de SOLID si mañana la empresa decide integrar Sensores de Calidad del Aire?

Respuesta: La implementación cumple parcialmente con OCP.

La colección List<Sensor> está bien diseñada porque trabaja contra la abstracción Sensor y permite almacenar diferentes tipos de sensores. Esto significa que procesarLecturas() aprovecha correctamente el polimorfismo. Si mañana aparece SensorCalidadAire, esa parte del sistema podría trabajar con él sin necesidad de modificar su lógica.

El problema principal aparece en filtrarCriticos(), donde se utiliza instanceof para identificar SensorHumedadSuelo y SensorTemperatura.

Esto significa que EstacionMonitoreo conoce directamente las clases concretas y sus reglas específicas. Si mañana se incorpora SensorCalidadAire, sería necesario modificar la lógica existente para reconocer el nuevo tipo de sensor.

Por eso, procesarLecturas() está bien encaminado respecto a OCP, mientras que filtrarCriticos() presenta una violación del principio.

También existe un problema de responsabilidades. EstacionMonitoreo actualmente administra sensores, procesa lecturas y además conoce reglas particulares de cada tipo de sensor para determinar cuándo una lectura es crítica.

Esto genera acoplamiento y hace que la clase sea menos mantenible a medida que aumente la cantidad de tipos de sensores.

EXTRA — ERRORES EN LOS TRES HITOS

Primer error: confundir encapsulamiento con seguridad concurrente. Los atributos privados protegen el acceso directo al estado, pero no garantizan que múltiples hilos puedan acceder y modificar el objeto de manera segura.

Segundo error: las reglas de evaluarEstado() son demasiado generales para sensores que representan magnitudes diferentes. La misma escala puede no tener sentido para humedad y temperatura.

Tercer error: el uso de instanceof en EstacionMonitoreo reduce el aprovechamiento del polimorfismo y genera una dependencia directa de las clases concretas.

Cuarto error: existe acoplamiento entre EstacionMonitoreo y los tipos concretos SensorHumedadSuelo y SensorTemperatura.

Quinto error: EstacionMonitoreo concentra demasiadas responsabilidades. Además de gestionar sensores, también conoce detalles específicos de cómo cada sensor determina una situación crítica.

(LAS CORRECCIONES FUERON APLICADAS Y ESTAN EN EL RESTO DE ESTE REPOSITORIO)
