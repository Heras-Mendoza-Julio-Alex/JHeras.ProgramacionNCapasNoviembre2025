package JHeras.ProgramacionNCapasNoviembre2025.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "USUARIO")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUSUARIO")
    private int IdUsuario;
    
    @Column(name = "NOMBRE", nullable = false)
    private String Nombre;
    
    @Column(name = "APELLIDOPATERNO", nullable = false)
    private String ApellidoPaterno;
    
    @Column(name = "APELLIDOMATERNO")
    private String ApellidoMaterno;

    @Column(name = "TELEFONO", nullable = false)
    private String Telefono;
    
    @Column(name = "FECHANACIMIENTO",nullable = false)
    private String fechanacimiento;
    
    @Column(name = "USERNAME",nullable = false)
    private String Username;
    
    @Column(name = "EMAIL",nullable = false)
    private String Email;
    
    @Column(name = "SEXO",nullable = false)
    private String Sexo;
    
    @Column(name = "CELULAR")
    private String Celular;
    
    @Column(name = "CURP")
    private String Curp;
    
    @Column(name = "PASSWORD")
    private String password;
    
    @ManyToOne
    @JoinColumn (name = "IDROL")
    public Rol Rol;
    
    @OneToMany(mappedBy = "Usuario",cascade=CascadeType.ALL,orphanRemoval = true)
    public List<Direccion> Direcciones = new ArrayList<>();
            

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = Username;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCurp() {
        return Curp;
    }

    public void setCurp(String Curp) {
        this.Curp = Curp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
  
}
