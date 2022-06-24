
class DisparadorSugerenciaDiaria {

    List<Usuario> usuarios;

    void enviarSugerenciaDiaria(){
        usuarios.forEach(usuario->{
            usuario.actualizarSugerencia();
        })
    }
}

class Usuario {
    Atuendo sugerenciaAtuendo;
    List<Guardarropa> guardarropas;
    AsesorDeAtuendos asesorDeAtuendos;
    List<String> mensaje;
    List<ObvservarAlerta> observadoresDeAlertas;

    Atuendo sugerir(){
        asesorDeAtuendos.sugerirAtuendo(guardarropas);
    }

    void actualizarSugerencia(){
        sugerenciaAtuendo = this.sugerir();
    }

    void alertaDeTormenta(){
        this.observadoresDeAlertas.accionPorAlertaTormenta();
    }

    void alertaDeGranizo(){
        this.observadoresDeAlertas.accionPorAlertaTormenta();
    }

    void llegaronAlertasNuevas(alertas){
        alertas.forEach(alerta =>{
            alerta.notificar(this)
        })
    }

    void notificarMensaje(mensaje,alerta){
        this.mensaje.add("Alerta: "+ alerta + ":" +mensaje);
    }
}

class Granizo implements Alerta{
    notificar(usuario){
        usuario.alertaDeGranizo();
    }
}

class Tormenta implements Alerta{
    notificar(usuario){
        usuario.alertaDeTormenta();
    }
}



class RegistroAlertas{
    List<Alerta> alertas;
    ProveedorClima proveedorClima;

    void actualizarAlertas(){
        this.alertas = proveedorClima.getAlertas();
    }
}



class SugierePorAlerta implements ObservarAlerta{
    accionPorAlertaTormenta(usuario, alerta){
        usuario.actualizarSugerencia(alerta);
    }

    accionPorAlertaGranizo(usuario, alerta){
        usuario.actualizarSugerencia(alerta);
    }
}


class NotificadorAlerta implements ObservarAlerta{

    accionPorAlertaTormenta(usuario, alerta){
        usuario.notificarMensaje(alerta, "Hay posibilidad de tormenta, lleva paraguas");
    }


    accionPorAlertaGranizo(usuario, alerta){
        usuario.actualizarSugerencia(alerta, "Hay posibilidad de granizo, no salgas con el auto");
    }

}

class Mailer implements ObservarAlerta{
    MailerSender mailSender;

    accionPorAlertaTormenta(usuario, alerta){
        mailSender.send(usuario.getAdress(), alerta + ": Hay posibilidad de tormenta, lleva paraguas");
    }


    accionPorAlertaGranizo(usuario, alerta){
        mailSender.send(usuario.getAdress(), alerta + ": Hay posibilidad de granizo, no salgas con el auto");
    }

}


//------Interfaces------

interface AsesorDeAtuendos {
    Sugerencia sugerirAtuendo ( List<Guardarropa> guardarropas)
}

interface ProveedorClima{
    List<Alerta> getAlertas();
}

interface ObservarAlerta{
    accionPorAlertaTormenta(usuario,alerta);
    accionPorAlertaGranizo(usuario,alerta);
}


interface Alerta{
    notificar(Usuario);
}