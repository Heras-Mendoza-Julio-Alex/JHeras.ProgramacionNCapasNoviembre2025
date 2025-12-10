package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result getall() {
        TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
        List<Usuario> usuarios = queryUsuario.getResultList();
        
        Result result=new Result();
        
        result.Objects=new ArrayList<>();
        
        for (Usuario usuario : usuarios) {
            
   
            JHeras.ProgramacionNCapasNoviembre2025.ML.Usuario usuarioML = modelMapper.map(usuario, JHeras.ProgramacionNCapasNoviembre2025.ML.Usuario.class);

            result.Objects.add(usuarioML);
            
            
        }
      
        result.Correct=true;
        return result;
    }
    

}
