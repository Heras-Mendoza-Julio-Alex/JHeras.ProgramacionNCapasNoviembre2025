package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result getall() {
        TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
        List<Usuario> usuarios = queryUsuario.getResultList();

        Result result = new Result();

        result.Objects = new ArrayList<>();

        for (Usuario usuario : usuarios) {

            JHeras.ProgramacionNCapasNoviembre2025.ML.Usuario usuarioML = modelMapper.map(usuario, JHeras.ProgramacionNCapasNoviembre2025.ML.Usuario.class);

            result.Objects.add(usuarioML);

        }

        result.Correct = true;
        return result;
    }

    @Transactional
    @Override
    public Result add(Usuario usuario) {
        Result result = new Result();

        try {
            entityManager.persist(usuario);
            usuario.Direcciones.get(0).Usuario = new Usuario();
            usuario.Direcciones.get(0).Usuario.setIdUsuario(usuario.getIdUsuario());
            entityManager.persist(usuario.Direcciones.get(0));
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result edit(JHeras.ProgramacionNCapasNoviembre2025.ML.Usuario usuarioML) {

        Result result = new Result();

        try {
            
            Usuario usuarioBD = entityManager.find(Usuario.class, usuarioML.getIdUsuario());

           if (usuarioBD != null) {
                ModelMapper modelMapper = new ModelMapper();
                Usuario usuarioJPA = modelMapper.map(usuarioML, Usuario.class);
                // Copiar direcciones si es necesario
                usuarioJPA.Direcciones = usuarioBD.Direcciones;
                
                //actualizas usuariojpa
                 entityManager.merge(usuarioJPA);
            } 
            
            result.Correct = true;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }


}
