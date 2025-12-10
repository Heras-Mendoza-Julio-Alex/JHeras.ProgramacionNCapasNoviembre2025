package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Usuario;
import java.util.List;

public interface IUsuario {
      public Result GetAll();
      
      public Result Add(Usuario usuario);
      
      public Result getById(int IdUsuario);
      
      public Result GetByIDU(int IdUsuario);
      
      public Result AddCargaMasiva(List<Usuario> usuarios);
      
      public Result UpdateUsuarioid(int IdUsuario);
      
      public Result DeleteById(int IdUsuario);
      
      public Result BusquedaUsuarioDireccionAll(Usuario usuario);
}
