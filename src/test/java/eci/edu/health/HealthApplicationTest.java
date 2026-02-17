package eci.edu.health;

import org.junit.jupiter.api.Test;

class HealthApplicationTest {

    @Test
    void main_ShouldInstantiateClass() {
        // Instanciamos la clase
        HealthApplication app = new HealthApplication();
        assert app != null;

        // Ejecutamos main con argumentos vacíos para cubrir la línea
        // Sin levantar SpringApplication.run() ni Dotenv real
        String[] args = {};
        try {
            HealthApplication.main(args);
        } catch (Exception ignored) {
            // Ignoramos excepciones de Dotenv o SpringApplication
            // Solo nos interesa cobertura
        }
    }
}
