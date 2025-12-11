package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;


public interface IUsuarioJPA {
    
    public Result getall();
    
    public Result add(Usuario usuario);
    
    public Result edit(JHeras.ProgramacionNCapasNoviembre2025.ML.Usuario usuarioML);
    

}
