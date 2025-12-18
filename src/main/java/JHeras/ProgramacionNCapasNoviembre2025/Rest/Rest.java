package JHeras.ProgramacionNCapasNoviembre2025.Rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class Rest {

    @GetMapping("/hola")
    public String Hola() {
        return "hola";
    }

//    Calculadora PathVariable
    @GetMapping("/Calculadora/suma/{numeroA}/{numeroB}")
    public String suma(@PathVariable("numeroA") double numeroA, @PathVariable("numeroB") double numeroB) {

        double resultadoSuma = numeroA + numeroB;

        return "La suma de los valores: " + numeroA + " + " + numeroB + " es igual a: " + resultadoSuma;
    }

    @GetMapping("/Calculadora/resta/{numeroA}/{numeroB}")
    public String resta(@PathVariable("numeroA") double numeroA, @PathVariable("numeroB") double numeroB) {

        double resultadoResta = numeroA - numeroB;

        return "La resta de los valores: " + numeroA + " - " + numeroB + " es igual a: " + resultadoResta;
    }

    @GetMapping("/Calculadora/{numeroA}/{numeroB}")
    public String calculadora(@PathVariable("numeroA") double numeroA, @PathVariable("numeroB") double numeroB) {

        double resultadoResta = numeroA - numeroB;
        double resultadoSuma = numeroA + numeroB;
        double resultadoDivision = numeroA / numeroB;
        double resultadoMultiplicacion = numeroA * numeroB;

        return "Calculadora con PathVariable\nLa resta de los valores: " + numeroA + " - " + numeroB + " es igual a: " + resultadoResta
                + "\nEl resultado de La suma de los valores: " + numeroA + " + " + numeroB + " es igual a: " + resultadoSuma
                + "\nEl resultado de La division de los valores: " + numeroA + " / " + numeroB + " es igual a: " + resultadoDivision
                + "\nEl resultado de La multiplicacion de los valores: " + numeroA + " * " + numeroB + " es igual a: " + resultadoMultiplicacion;
    }

    //calculadora Request 
    @GetMapping("/calculadoraRequest")
    public String calculadoraRequest(@RequestParam("numeroUno") double numeroUno, @RequestParam("numeroDos") double numeroDos) {

        double resultadoResta = numeroUno - numeroDos;
        double resultadoSuma = numeroUno + numeroDos;
        double resultadoDivision = numeroUno / numeroDos;
        double resultadoMultiplicacion = numeroUno * numeroDos;

        return "Calculadora con RequestParam\nLa resta de los valores: " + numeroUno + " - " + numeroDos + " es igual a: " + resultadoResta
                + "\nEl resultado de La suma de los valores: " + numeroUno + " + " + numeroDos + " es igual a: " + resultadoSuma
                + "\nEl resultado de La division de los valores: " + numeroUno + " / " + numeroDos + " es igual a: " + resultadoDivision
                + "\nEl resultado de La multiplicacion de los valores: " + numeroUno + " * " + numeroDos + " es igual a: " + resultadoMultiplicacion;

    }
    //calculadora con body

    @PostMapping(value = "/calculadora/add", consumes = "application/json")
    public String calculadoraBody(@RequestBody CalculadoraDTO calculadora) {
        double numeroUno=calculadora.getNumeroUno();
        double numeroDos=calculadora.getNumeroDos();        
        
        double resultadoSuma = numeroUno + numeroDos;
        double resultadoResta = numeroUno - numeroDos;
        double resultadoDivision = numeroUno / numeroDos;
        double resultadoMultiplicacion = numeroUno * numeroDos;
        
        
        return "Calculadora con BodyPostman\nLa resta de los valores: " + numeroUno + " - " + numeroDos + " es igual a: " + resultadoResta
                + "\nEl resultado de La suma de los valores: " + numeroUno + " + " + numeroDos + " es igual a: " + resultadoSuma
                + "\nEl resultado de La division de los valores: " + numeroUno + " / " + numeroDos + " es igual a: " + resultadoDivision
                + "\nEl resultado de La multiplicacion de los valores: " + numeroUno + " * " + numeroDos + " es igual a: " + resultadoMultiplicacion;

    }

}
