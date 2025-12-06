package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.ML.Result;


public interface IDireccion {
    public Result getbyIDD(int idDireccion);
    
    public Result GetByIDUD(int idDireccion, int idUsario);
    
    
}
