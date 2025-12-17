package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;

public interface IDireccionJPA {

    public Result getById(int IdDireccion);        
    
    public Result add(Direccion direccionjpa,int IdUsuario);
    
    public Result edit(JHeras.ProgramacionNCapasNoviembre2025.ML.Direccion direccionML,int IdUsuario);
    
}
