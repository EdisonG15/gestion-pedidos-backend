package com.portafolio.gestionpedidos.entity;

import com.portafolio.gestionpedidos.enums.EstadoUsuario;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;


    @Column(name = "username")
    private String username;

    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @Enumerated(EnumType.STRING)
    private EstadoUsuario estado;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;
}
