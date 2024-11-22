package com.taskflow.demo.config;

import com.taskflow.demo.entities.*;
import com.taskflow.demo.repositories.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


@Configuration
public class DataInitializer {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserStoryRepository userStoryRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeRoles() {
        return args -> {
            if (!roleRepository.existsByName("ROLE_USER")) {
                roleRepository.save(new Role("ROLE_USER"));
            }

            if (!roleRepository.existsByName("ROLE_ADMIN")) {
                var roleAdm = new Role("ROLE_ADMIN");
                roleRepository.save(roleAdm);
            }

            if (companyRepository.count() == 0) {

                List<Company> companies = Arrays.asList(
                        new Company(null, "TechCorp", "900123456", "+57 123456789", "Calle 123 #45-67, Bogotá", "contacto@techcorp.com"),
                        new Company(null, "InnovaSoft", "901234567", "+57 987654321", "Carrera 10 #20-30, Medellín", "info@innovasoft.com"),
                        new Company(null, "BuildIt", "902345678", "+57 456789012", "Avenida 15 #40-50, Cali", "support@buildit.com"),
                        new Company(null, "GreenTech", "903456789", "+57 321654987", "Calle 80 #70-10, Bogotá", "hello@greentech.com"),
                        new Company(null, "SmartSolutions", "904567890", "+57 987321654", "Carrera 7 #11-12, Barranquilla", "contact@smartsolutions.com"),
                        new Company(null, "ITSolutions", "9000056558", "+57 96545111", "Carrera 38 #22-12, Barranquilla", "contact@itsolutions.com"),
                        new Company(null, "TechDynamics", "900123456", "+57 3156789012", "Calle 45 #21-34, Medellín", "info@techdynamics.com"),
                        new Company(null, "Innovatech", "901234567", "+57 3167890123", "Avenida 68 #15-45, Bogotá", "hello@innovatech.com")
                );

                // Guardar compañías
                companyRepository.saveAll(companies);

                var user = new User();
                user.setUsername("Admin");
                user.setEmail("mmm@gg.com");
                user.setCompanyId(companies.get(0).getId());
                user.setPassword(passwordEncoder.encode("1234"));
                user.setRoles(Set.of(roleRepository.findByName("ROLE_ADMIN").orElseThrow()));

                userRepository.save(user);

                companies.forEach(company -> {
                    List<Project> projects = createProjects(company);
                    projectRepository.saveAll(projects);

                    projects.forEach(project -> {
                        List<UserStory> userStories = createUserStories(project, new ObjectId(user.getId()));
                        userStoryRepository.saveAll(userStories);

                        userStories.forEach(userStory -> {
                            List<Ticket> tickets = createTickets(userStory, new ObjectId(user.getId()));
                            ticketRepository.saveAll(tickets);
                        });
                    });
                });

                System.out.println("Datos inicializados con éxito.");
            }
        };


    }
    private List<Project> createProjects(Company company) {
        return Arrays.asList(
                new Project(null, "E-Commerce Platform", "Proyecto para crear una plataforma de comercio electrónico.", company.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString()),
                new Project(null, "CRM System", "Sistema de gestión de relaciones con clientes.", company.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString()),
                new Project(null, "ERP System", "Sistema de planificación de recursos empresariales.", company.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString()),
                new Project(null, "Mobile App", "Aplicación móvil para clientes.", company.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString())
        );
    }

    private List<UserStory> createUserStories(Project project, ObjectId userId) {
        return Arrays.asList(
                new UserStory(null, "Carrito de compras", "Como usuario, quiero un carrito de compras para agregar productos.", new ObjectId(project.getId()), LocalDateTime.now().toString(), LocalDateTime.now().toString(), userId),
                new UserStory(null, "Gestión de usuarios", "Como administrador, quiero gestionar usuarios registrados.", new ObjectId(project.getId()), LocalDateTime.now().toString(), LocalDateTime.now().toString(), userId),
                new UserStory(null, "Pasarela de pagos", "Como usuario, quiero pagar con tarjeta de crédito.", new ObjectId(project.getId()), LocalDateTime.now().toString(), LocalDateTime.now().toString(), userId),
                new UserStory(null, "Notificaciones", "Como usuario, quiero recibir notificaciones de mis pedidos.", new ObjectId(project.getId()), LocalDateTime.now().toString(), LocalDateTime.now().toString(), userId)
        );
    }

    private List<Ticket> createTickets(UserStory userStory, ObjectId userId) {
        return Arrays.asList(
                new Ticket(null, "Diseñar la interfaz de usuario", "Crear la UI para el carrito de compras.", new ObjectId(userStory.getId()), "En Proceso", new ArrayList<>(), Instant.now(), LocalDateTime.now().toString(), userId),
                new Ticket(null, "Implementar lógica backend", "Implementar la lógica de negocio para el carrito.", new ObjectId(userStory.getId()), "En Proceso", new ArrayList<>(), Instant.now(), LocalDateTime.now().toString(), userId),
                new Ticket(null, "Integrar API de pagos", "Conectar con la API de la pasarela de pagos.", new ObjectId(userStory.getId()), "Finalizado", new ArrayList<>(), Instant.now(), LocalDateTime.now().toString(), userId),
                new Ticket(null, "Pruebas de integración", "Realizar pruebas end-to-end.", new ObjectId(userStory.getId()), "Finalizado", new ArrayList<>(), Instant.now(), LocalDateTime.now().toString(), userId)
        );
    }
}