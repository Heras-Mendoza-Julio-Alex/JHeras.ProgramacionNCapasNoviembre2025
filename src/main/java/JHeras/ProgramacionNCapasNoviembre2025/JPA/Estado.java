package JHeras.ProgramacionNCapasNoviembre2025.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ESTADO")
public class Estado {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDESTADO")
    private int idEstado;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "idpais_fk")
    public Pais Pais;

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }        
    
}
