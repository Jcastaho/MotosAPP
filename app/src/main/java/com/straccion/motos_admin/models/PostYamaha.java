package com.straccion.motos_admin.models;

import java.util.ArrayList;

public class PostYamaha {
    private String id;
    private int precio;
    private int precio2;
    private String nombreMoto;
    private String marcaMoto;
    private int prioridad;
    private ArrayList<String> imagenes;
    private ArrayList<String>colores;
    private ArrayList<String>imagenesColores1;
    private ArrayList<String>imagenesColores2;
    private ArrayList<String>imagenesColores3;
    private ArrayList<String>imagenesColores4;
    private ArrayList<String>imagenesColores5;
    private ArrayList<String>imagenesColores6;
    private String cilindraje;
    private String peso;
    private String potenciamaxima;
    private String torquemaximo;
    private String tipodemotor;
    private String largototal;
    private String anchototal;
    private String alturatotal;
    private String alturaalasiento;
    private String distanciaentreejes;
    private String distanciaminimadelpiso;
    private String tipodelubricacion;
    private String bateria;
    private String disposiciondeloscilindros;
    private String diametroporcarrera;
    private String relaciondecompresion;
    private String arranque;
    private String sistemadealimentacion;
    private String capacidaddecombustible;
    private String encendido;
    private String capacidadbateria;
    private String sistemadereduccionprimaria;
    private String relaciondereduccionprimaria;
    private String sistemadereduccionsecundaria;
    private String relaciondereduccionsecundaria;
    private String tipodeembrague;
    private String tipodetransmision;
    private String relaciondetransmisionen1ra;
    private String relaciondetransmisionen2da;
    private String relaciondetransmisionen3ra;
    private String relaciondetransmisionen4ta;
    private String relaciondetransmisionen5ta;
    private String relaciondetransmisionen6ta;
    private String relaciondeengranajes;
    private String tipodechasis;
    private String inclinacion;
    private String avance;
    private String ruedadelantera;
    private String ruedatrasera;
    private String frenodelantero;
    private String frenotrasero;
    private String tipodesuspensiondelantera;
    private String tipodesuspensiontrasera;
    private String luzprincipal;
    private String modelo;
    private String modelo2;
    private boolean descuento;
    private int nuevoValorDescuento;
    private String carpeta1;
    private String carpeta2;
    private String carpeta3;
    private ArrayList<String> manualesArchivos;
    private ArrayList<String> nombresArchivos;
    private ArrayList<String> caracteristicasTexto;
    private ArrayList<String> caracteristicasImagenes;
    private ArrayList<String> clasificacion;
    private double vistas;
    private double busquedas;
    private double velocidadMaxima;
    private int consumoPorGalon;
    private boolean visible;

    public PostYamaha(){

    }

    public PostYamaha(String id, int precio, int precio2, String nombreMoto, String marcaMoto, int prioridad, ArrayList<String> imagenes, ArrayList<String> colores, ArrayList<String> imagenesColores1, ArrayList<String> imagenesColores2, ArrayList<String> imagenesColores3, ArrayList<String> imagenesColores4, ArrayList<String> imagenesColores5, ArrayList<String> imagenesColores6, String cilindraje, String peso, String potenciamaxima, String torquemaximo, String tipodemotor, String largototal, String anchototal, String alturatotal, String alturaalasiento, String distanciaentreejes, String distanciaminimadelpiso, String tipodelubricacion, String bateria, String disposiciondeloscilindros, String diametroporcarrera, String relaciondecompresion, String arranque, String sistemadealimentacion, String capacidaddecombustible, String encendido, String capacidadbateria, String sistemadereduccionprimaria, String relaciondereduccionprimaria, String sistemadereduccionsecundaria, String relaciondereduccionsecundaria, String tipodeembrague, String tipodetransmision, String relaciondetransmisionen1ra, String relaciondetransmisionen2da, String relaciondetransmisionen3ra, String relaciondetransmisionen4ta, String relaciondetransmisionen5ta, String relaciondetransmisionen6ta, String relaciondeengranajes, String tipodechasis, String inclinacion, String avance, String ruedadelantera, String ruedatrasera, String frenodelantero, String frenotrasero, String tipodesuspensiondelantera, String tipodesuspensiontrasera, String luzprincipal, String modelo, String modelo2, boolean descuento, int nuevoValorDescuento, String carpeta1, String carpeta2, String carpeta3, ArrayList<String> manualesArchivos, ArrayList<String> nombresArchivos, ArrayList<String> caracteristicasTexto, ArrayList<String> caracteristicasImagenes, ArrayList<String> clasificacion, double vistas, double busquedas, double velocidadMaxima, int consumoPorGalon, boolean visible) {
        this.id = id;
        this.precio = precio;
        this.precio2 = precio2;
        this.nombreMoto = nombreMoto;
        this.marcaMoto = marcaMoto;
        this.prioridad = prioridad;
        this.imagenes = imagenes;
        this.colores = colores;
        this.imagenesColores1 = imagenesColores1;
        this.imagenesColores2 = imagenesColores2;
        this.imagenesColores3 = imagenesColores3;
        this.imagenesColores4 = imagenesColores4;
        this.imagenesColores5 = imagenesColores5;
        this.imagenesColores6 = imagenesColores6;
        this.cilindraje = cilindraje;
        this.peso = peso;
        this.potenciamaxima = potenciamaxima;
        this.torquemaximo = torquemaximo;
        this.tipodemotor = tipodemotor;
        this.largototal = largototal;
        this.anchototal = anchototal;
        this.alturatotal = alturatotal;
        this.alturaalasiento = alturaalasiento;
        this.distanciaentreejes = distanciaentreejes;
        this.distanciaminimadelpiso = distanciaminimadelpiso;
        this.tipodelubricacion = tipodelubricacion;
        this.bateria = bateria;
        this.disposiciondeloscilindros = disposiciondeloscilindros;
        this.diametroporcarrera = diametroporcarrera;
        this.relaciondecompresion = relaciondecompresion;
        this.arranque = arranque;
        this.sistemadealimentacion = sistemadealimentacion;
        this.capacidaddecombustible = capacidaddecombustible;
        this.encendido = encendido;
        this.capacidadbateria = capacidadbateria;
        this.sistemadereduccionprimaria = sistemadereduccionprimaria;
        this.relaciondereduccionprimaria = relaciondereduccionprimaria;
        this.sistemadereduccionsecundaria = sistemadereduccionsecundaria;
        this.relaciondereduccionsecundaria = relaciondereduccionsecundaria;
        this.tipodeembrague = tipodeembrague;
        this.tipodetransmision = tipodetransmision;
        this.relaciondetransmisionen1ra = relaciondetransmisionen1ra;
        this.relaciondetransmisionen2da = relaciondetransmisionen2da;
        this.relaciondetransmisionen3ra = relaciondetransmisionen3ra;
        this.relaciondetransmisionen4ta = relaciondetransmisionen4ta;
        this.relaciondetransmisionen5ta = relaciondetransmisionen5ta;
        this.relaciondetransmisionen6ta = relaciondetransmisionen6ta;
        this.relaciondeengranajes = relaciondeengranajes;
        this.tipodechasis = tipodechasis;
        this.inclinacion = inclinacion;
        this.avance = avance;
        this.ruedadelantera = ruedadelantera;
        this.ruedatrasera = ruedatrasera;
        this.frenodelantero = frenodelantero;
        this.frenotrasero = frenotrasero;
        this.tipodesuspensiondelantera = tipodesuspensiondelantera;
        this.tipodesuspensiontrasera = tipodesuspensiontrasera;
        this.luzprincipal = luzprincipal;
        this.modelo = modelo;
        this.modelo2 = modelo2;
        this.descuento = descuento;
        this.nuevoValorDescuento = nuevoValorDescuento;
        this.carpeta1 = carpeta1;
        this.carpeta2 = carpeta2;
        this.carpeta3 = carpeta3;
        this.manualesArchivos = manualesArchivos;
        this.nombresArchivos = nombresArchivos;
        this.caracteristicasTexto = caracteristicasTexto;
        this.caracteristicasImagenes = caracteristicasImagenes;
        this.clasificacion = clasificacion;
        this.vistas = vistas;
        this.busquedas = busquedas;
        this.velocidadMaxima = velocidadMaxima;
        this.consumoPorGalon = consumoPorGalon;
        this.visible = visible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getPrecio2() {
        return precio2;
    }

    public void setPrecio2(int precio2) {
        this.precio2 = precio2;
    }

    public String getNombreMoto() {
        return nombreMoto;
    }

    public void setNombreMoto(String nombreMoto) {
        this.nombreMoto = nombreMoto;
    }

    public String getMarcaMoto() {
        return marcaMoto;
    }

    public void setMarcaMoto(String marcaMoto) {
        this.marcaMoto = marcaMoto;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    public ArrayList<String> getColores() {
        return colores;
    }

    public void setColores(ArrayList<String> colores) {
        this.colores = colores;
    }

    public ArrayList<String> getImagenesColores1() {
        return imagenesColores1;
    }

    public void setImagenesColores1(ArrayList<String> imagenesColores1) {
        this.imagenesColores1 = imagenesColores1;
    }

    public ArrayList<String> getImagenesColores2() {
        return imagenesColores2;
    }

    public void setImagenesColores2(ArrayList<String> imagenesColores2) {
        this.imagenesColores2 = imagenesColores2;
    }

    public ArrayList<String> getImagenesColores3() {
        return imagenesColores3;
    }

    public void setImagenesColores3(ArrayList<String> imagenesColores3) {
        this.imagenesColores3 = imagenesColores3;
    }

    public ArrayList<String> getImagenesColores4() {
        return imagenesColores4;
    }

    public void setImagenesColores4(ArrayList<String> imagenesColores4) {
        this.imagenesColores4 = imagenesColores4;
    }

    public ArrayList<String> getImagenesColores5() {
        return imagenesColores5;
    }

    public void setImagenesColores5(ArrayList<String> imagenesColores5) {
        this.imagenesColores5 = imagenesColores5;
    }

    public ArrayList<String> getImagenesColores6() {
        return imagenesColores6;
    }

    public void setImagenesColores6(ArrayList<String> imagenesColores6) {
        this.imagenesColores6 = imagenesColores6;
    }

    public String getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(String cilindraje) {
        this.cilindraje = cilindraje;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getPotenciamaxima() {
        return potenciamaxima;
    }

    public void setPotenciamaxima(String potenciamaxima) {
        this.potenciamaxima = potenciamaxima;
    }

    public String getTorquemaximo() {
        return torquemaximo;
    }

    public void setTorquemaximo(String torquemaximo) {
        this.torquemaximo = torquemaximo;
    }

    public String getTipodemotor() {
        return tipodemotor;
    }

    public void setTipodemotor(String tipodemotor) {
        this.tipodemotor = tipodemotor;
    }

    public String getLargototal() {
        return largototal;
    }

    public void setLargototal(String largototal) {
        this.largototal = largototal;
    }

    public String getAnchototal() {
        return anchototal;
    }

    public void setAnchototal(String anchototal) {
        this.anchototal = anchototal;
    }

    public String getAlturatotal() {
        return alturatotal;
    }

    public void setAlturatotal(String alturatotal) {
        this.alturatotal = alturatotal;
    }

    public String getAlturaalasiento() {
        return alturaalasiento;
    }

    public void setAlturaalasiento(String alturaalasiento) {
        this.alturaalasiento = alturaalasiento;
    }

    public String getDistanciaentreejes() {
        return distanciaentreejes;
    }

    public void setDistanciaentreejes(String distanciaentreejes) {
        this.distanciaentreejes = distanciaentreejes;
    }

    public String getDistanciaminimadelpiso() {
        return distanciaminimadelpiso;
    }

    public void setDistanciaminimadelpiso(String distanciaminimadelpiso) {
        this.distanciaminimadelpiso = distanciaminimadelpiso;
    }

    public String getTipodelubricacion() {
        return tipodelubricacion;
    }

    public void setTipodelubricacion(String tipodelubricacion) {
        this.tipodelubricacion = tipodelubricacion;
    }

    public String getBateria() {
        return bateria;
    }

    public void setBateria(String bateria) {
        this.bateria = bateria;
    }

    public String getDisposiciondeloscilindros() {
        return disposiciondeloscilindros;
    }

    public void setDisposiciondeloscilindros(String disposiciondeloscilindros) {
        this.disposiciondeloscilindros = disposiciondeloscilindros;
    }

    public String getDiametroporcarrera() {
        return diametroporcarrera;
    }

    public void setDiametroporcarrera(String diametroporcarrera) {
        this.diametroporcarrera = diametroporcarrera;
    }

    public String getRelaciondecompresion() {
        return relaciondecompresion;
    }

    public void setRelaciondecompresion(String relaciondecompresion) {
        this.relaciondecompresion = relaciondecompresion;
    }

    public String getArranque() {
        return arranque;
    }

    public void setArranque(String arranque) {
        this.arranque = arranque;
    }

    public String getSistemadealimentacion() {
        return sistemadealimentacion;
    }

    public void setSistemadealimentacion(String sistemadealimentacion) {
        this.sistemadealimentacion = sistemadealimentacion;
    }

    public String getCapacidaddecombustible() {
        return capacidaddecombustible;
    }

    public void setCapacidaddecombustible(String capacidaddecombustible) {
        this.capacidaddecombustible = capacidaddecombustible;
    }

    public String getEncendido() {
        return encendido;
    }

    public void setEncendido(String encendido) {
        this.encendido = encendido;
    }

    public String getCapacidadbateria() {
        return capacidadbateria;
    }

    public void setCapacidadbateria(String capacidadbateria) {
        this.capacidadbateria = capacidadbateria;
    }

    public String getSistemadereduccionprimaria() {
        return sistemadereduccionprimaria;
    }

    public void setSistemadereduccionprimaria(String sistemadereduccionprimaria) {
        this.sistemadereduccionprimaria = sistemadereduccionprimaria;
    }

    public String getRelaciondereduccionprimaria() {
        return relaciondereduccionprimaria;
    }

    public void setRelaciondereduccionprimaria(String relaciondereduccionprimaria) {
        this.relaciondereduccionprimaria = relaciondereduccionprimaria;
    }

    public String getSistemadereduccionsecundaria() {
        return sistemadereduccionsecundaria;
    }

    public void setSistemadereduccionsecundaria(String sistemadereduccionsecundaria) {
        this.sistemadereduccionsecundaria = sistemadereduccionsecundaria;
    }

    public String getRelaciondereduccionsecundaria() {
        return relaciondereduccionsecundaria;
    }

    public void setRelaciondereduccionsecundaria(String relaciondereduccionsecundaria) {
        this.relaciondereduccionsecundaria = relaciondereduccionsecundaria;
    }

    public String getTipodeembrague() {
        return tipodeembrague;
    }

    public void setTipodeembrague(String tipodeembrague) {
        this.tipodeembrague = tipodeembrague;
    }

    public String getTipodetransmision() {
        return tipodetransmision;
    }

    public void setTipodetransmision(String tipodetransmision) {
        this.tipodetransmision = tipodetransmision;
    }

    public String getRelaciondetransmisionen1ra() {
        return relaciondetransmisionen1ra;
    }

    public void setRelaciondetransmisionen1ra(String relaciondetransmisionen1ra) {
        this.relaciondetransmisionen1ra = relaciondetransmisionen1ra;
    }

    public String getRelaciondetransmisionen2da() {
        return relaciondetransmisionen2da;
    }

    public void setRelaciondetransmisionen2da(String relaciondetransmisionen2da) {
        this.relaciondetransmisionen2da = relaciondetransmisionen2da;
    }

    public String getRelaciondetransmisionen3ra() {
        return relaciondetransmisionen3ra;
    }

    public void setRelaciondetransmisionen3ra(String relaciondetransmisionen3ra) {
        this.relaciondetransmisionen3ra = relaciondetransmisionen3ra;
    }

    public String getRelaciondetransmisionen4ta() {
        return relaciondetransmisionen4ta;
    }

    public void setRelaciondetransmisionen4ta(String relaciondetransmisionen4ta) {
        this.relaciondetransmisionen4ta = relaciondetransmisionen4ta;
    }

    public String getRelaciondetransmisionen5ta() {
        return relaciondetransmisionen5ta;
    }

    public void setRelaciondetransmisionen5ta(String relaciondetransmisionen5ta) {
        this.relaciondetransmisionen5ta = relaciondetransmisionen5ta;
    }

    public String getRelaciondetransmisionen6ta() {
        return relaciondetransmisionen6ta;
    }

    public void setRelaciondetransmisionen6ta(String relaciondetransmisionen6ta) {
        this.relaciondetransmisionen6ta = relaciondetransmisionen6ta;
    }

    public String getRelaciondeengranajes() {
        return relaciondeengranajes;
    }

    public void setRelaciondeengranajes(String relaciondeengranajes) {
        this.relaciondeengranajes = relaciondeengranajes;
    }

    public String getTipodechasis() {
        return tipodechasis;
    }

    public void setTipodechasis(String tipodechasis) {
        this.tipodechasis = tipodechasis;
    }

    public String getInclinacion() {
        return inclinacion;
    }

    public void setInclinacion(String inclinacion) {
        this.inclinacion = inclinacion;
    }

    public String getAvance() {
        return avance;
    }

    public void setAvance(String avance) {
        this.avance = avance;
    }

    public String getRuedadelantera() {
        return ruedadelantera;
    }

    public void setRuedadelantera(String ruedadelantera) {
        this.ruedadelantera = ruedadelantera;
    }

    public String getRuedatrasera() {
        return ruedatrasera;
    }

    public void setRuedatrasera(String ruedatrasera) {
        this.ruedatrasera = ruedatrasera;
    }

    public String getFrenodelantero() {
        return frenodelantero;
    }

    public void setFrenodelantero(String frenodelantero) {
        this.frenodelantero = frenodelantero;
    }

    public String getFrenotrasero() {
        return frenotrasero;
    }

    public void setFrenotrasero(String frenotrasero) {
        this.frenotrasero = frenotrasero;
    }

    public String getTipodesuspensiondelantera() {
        return tipodesuspensiondelantera;
    }

    public void setTipodesuspensiondelantera(String tipodesuspensiondelantera) {
        this.tipodesuspensiondelantera = tipodesuspensiondelantera;
    }

    public String getTipodesuspensiontrasera() {
        return tipodesuspensiontrasera;
    }

    public void setTipodesuspensiontrasera(String tipodesuspensiontrasera) {
        this.tipodesuspensiontrasera = tipodesuspensiontrasera;
    }

    public String getLuzprincipal() {
        return luzprincipal;
    }

    public void setLuzprincipal(String luzprincipal) {
        this.luzprincipal = luzprincipal;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getModelo2() {
        return modelo2;
    }

    public void setModelo2(String modelo2) {
        this.modelo2 = modelo2;
    }

    public boolean isDescuento() {
        return descuento;
    }

    public void setDescuento(boolean descuento) {
        this.descuento = descuento;
    }

    public int getNuevoValorDescuento() {
        return nuevoValorDescuento;
    }

    public void setNuevoValorDescuento(int nuevoValorDescuento) {
        this.nuevoValorDescuento = nuevoValorDescuento;
    }

    public String getCarpeta1() {
        return carpeta1;
    }

    public void setCarpeta1(String carpeta1) {
        this.carpeta1 = carpeta1;
    }

    public String getCarpeta2() {
        return carpeta2;
    }

    public void setCarpeta2(String carpeta2) {
        this.carpeta2 = carpeta2;
    }

    public String getCarpeta3() {
        return carpeta3;
    }

    public void setCarpeta3(String carpeta3) {
        this.carpeta3 = carpeta3;
    }

    public ArrayList<String> getManualesArchivos() {
        return manualesArchivos;
    }

    public void setManualesArchivos(ArrayList<String> manualesArchivos) {
        this.manualesArchivos = manualesArchivos;
    }

    public ArrayList<String> getNombresArchivos() {
        return nombresArchivos;
    }

    public void setNombresArchivos(ArrayList<String> nombresArchivos) {
        this.nombresArchivos = nombresArchivos;
    }

    public ArrayList<String> getCaracteristicasTexto() {
        return caracteristicasTexto;
    }

    public void setCaracteristicasTexto(ArrayList<String> caracteristicasTexto) {
        this.caracteristicasTexto = caracteristicasTexto;
    }

    public ArrayList<String> getCaracteristicasImagenes() {
        return caracteristicasImagenes;
    }

    public void setCaracteristicasImagenes(ArrayList<String> caracteristicasImagenes) {
        this.caracteristicasImagenes = caracteristicasImagenes;
    }

    public ArrayList<String> getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(ArrayList<String> clasificacion) {
        this.clasificacion = clasificacion;
    }

    public double getVistas() {
        return vistas;
    }

    public void setVistas(double vistas) {
        this.vistas = vistas;
    }

    public double getBusquedas() {
        return busquedas;
    }

    public void setBusquedas(double busquedas) {
        this.busquedas = busquedas;
    }

    public double getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public void setVelocidadMaxima(double velocidadMaxima) {
        this.velocidadMaxima = velocidadMaxima;
    }

    public int getConsumoPorGalon() {
        return consumoPorGalon;
    }

    public void setConsumoPorGalon(int consumoPorGalon) {
        this.consumoPorGalon = consumoPorGalon;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
