package com.straccion.motos_admin.models;

import java.util.ArrayList;

public class PostYamaha {
    private String id;
    private int precio;
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
    private String potenciaMaxima;
    private String torqueMaximo;
    private String tipoMotor;
    private String largoTotal;
    private String anchoTotal;
    private String alturaTotal;
    private String alturaAsiento;
    private String distanciaEntreEjes;
    private String distanciaMinimaPiso;
    private String tipoLubricacion;
    private String bateria;
    private String disposiciondeCilindros;
    private String diametroPorCarrera;
    private String relacionCompresion;
    private String arranque;
    private String sistemaAlimentacion;
    private String capacidadCombustible;
    private String encendido;
    private String capacidadBateria;
    private String sistemadeReduccionPrimaria;
    private String relaciondeReduccionPrimaria;
    private String sistemadeReduccionSecundaria;
    private String relaciondeReduccionSecundaria;
    private String tipoEmbrague;
    private String tipoTransmision;
    private String relacionTransmision1ra;
    private String relacionTransmision2da;
    private String relacionTransmision3ra;
    private String relacionTransmision4ta;
    private String relacionTransmision5ta;
    private String relacionTransmision6ta;
    private String relacionEngranajes;
    private String tipoChasis;
    private String inclinacion;
    private String avance;
    private String ruedaDelantera;
    private String ruedaTrasera;
    private String frenoDelantero;
    private String frenoTrasero;
    private String tipoSuspensionDelantera;
    private String tipoSuspensionTrasera;
    private String luzPrincipal;
    private String descripcion;
    private String modelo;
    private boolean descuento;
    private String nuevoValorDescuento;
    private String carpeta1;
    private String carpeta2;
    private String carpeta3;
    private ArrayList<String> manualesArchivos;
    private ArrayList<String> nombresArchivos;
    private ArrayList<String> caracteristicasTexto;
    private ArrayList<String> caracteristicasImagenes;
    private ArrayList<String> clasificacion;
    private double calificacion;
    private double vistas;
    private double busquedas;

    public PostYamaha(){

    }

    public PostYamaha(String id, int precio, String nombreMoto, String marcaMoto, int prioridad, ArrayList<String> imagenes, ArrayList<String> colores, ArrayList<String> imagenesColores1, ArrayList<String> imagenesColores2, ArrayList<String> imagenesColores3, ArrayList<String> imagenesColores4, ArrayList<String> imagenesColores5, ArrayList<String> imagenesColores6, String cilindraje, String peso, String potenciaMaxima, String torqueMaximo, String tipoMotor, String largoTotal, String anchoTotal, String alturaTotal, String alturaAsiento, String distanciaEntreEjes, String distanciaMinimaPiso, String tipoLubricacion, String bateria, String disposiciondeCilindros, String diametroPorCarrera, String relacionCompresion, String arranque, String sistemaAlimentacion, String capacidadCombustible, String encendido, String capacidadBateria, String sistemadeReduccionPrimaria, String relaciondeReduccionPrimaria, String sistemadeReduccionSecundaria, String relaciondeReduccionSecundaria, String tipoEmbrague, String tipoTransmision, String relacionTransmision1ra, String relacionTransmision2da, String relacionTransmision3ra, String relacionTransmision4ta, String relacionTransmision5ta, String relacionTransmision6ta, String relacionEngranajes, String tipoChasis, String inclinacion, String avance, String ruedaDelantera, String ruedaTrasera, String frenoDelantero, String frenoTrasero, String tipoSuspensionDelantera, String tipoSuspensionTrasera, String luzPrincipal, String descripcion, String modelo, boolean descuento, String nuevoValorDescuento, String carpeta1, String carpeta2, String carpeta3, ArrayList<String> manualesArchivos, ArrayList<String> nombresArchivos, ArrayList<String> caracteristicasTexto, ArrayList<String> caracteristicasImagenes, ArrayList<String> clasificacion, double calificacion, double vistas, double busquedas) {
        this.id = id;
        this.precio = precio;
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
        this.potenciaMaxima = potenciaMaxima;
        this.torqueMaximo = torqueMaximo;
        this.tipoMotor = tipoMotor;
        this.largoTotal = largoTotal;
        this.anchoTotal = anchoTotal;
        this.alturaTotal = alturaTotal;
        this.alturaAsiento = alturaAsiento;
        this.distanciaEntreEjes = distanciaEntreEjes;
        this.distanciaMinimaPiso = distanciaMinimaPiso;
        this.tipoLubricacion = tipoLubricacion;
        this.bateria = bateria;
        this.disposiciondeCilindros = disposiciondeCilindros;
        this.diametroPorCarrera = diametroPorCarrera;
        this.relacionCompresion = relacionCompresion;
        this.arranque = arranque;
        this.sistemaAlimentacion = sistemaAlimentacion;
        this.capacidadCombustible = capacidadCombustible;
        this.encendido = encendido;
        this.capacidadBateria = capacidadBateria;
        this.sistemadeReduccionPrimaria = sistemadeReduccionPrimaria;
        this.relaciondeReduccionPrimaria = relaciondeReduccionPrimaria;
        this.sistemadeReduccionSecundaria = sistemadeReduccionSecundaria;
        this.relaciondeReduccionSecundaria = relaciondeReduccionSecundaria;
        this.tipoEmbrague = tipoEmbrague;
        this.tipoTransmision = tipoTransmision;
        this.relacionTransmision1ra = relacionTransmision1ra;
        this.relacionTransmision2da = relacionTransmision2da;
        this.relacionTransmision3ra = relacionTransmision3ra;
        this.relacionTransmision4ta = relacionTransmision4ta;
        this.relacionTransmision5ta = relacionTransmision5ta;
        this.relacionTransmision6ta = relacionTransmision6ta;
        this.relacionEngranajes = relacionEngranajes;
        this.tipoChasis = tipoChasis;
        this.inclinacion = inclinacion;
        this.avance = avance;
        this.ruedaDelantera = ruedaDelantera;
        this.ruedaTrasera = ruedaTrasera;
        this.frenoDelantero = frenoDelantero;
        this.frenoTrasero = frenoTrasero;
        this.tipoSuspensionDelantera = tipoSuspensionDelantera;
        this.tipoSuspensionTrasera = tipoSuspensionTrasera;
        this.luzPrincipal = luzPrincipal;
        this.descripcion = descripcion;
        this.modelo = modelo;
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
        this.calificacion = calificacion;
        this.vistas = vistas;
        this.busquedas = busquedas;
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

    public String getPotenciaMaxima() {
        return potenciaMaxima;
    }

    public void setPotenciaMaxima(String potenciaMaxima) {
        this.potenciaMaxima = potenciaMaxima;
    }

    public String getTorqueMaximo() {
        return torqueMaximo;
    }

    public void setTorqueMaximo(String torqueMaximo) {
        this.torqueMaximo = torqueMaximo;
    }

    public String getTipoMotor() {
        return tipoMotor;
    }

    public void setTipoMotor(String tipoMotor) {
        this.tipoMotor = tipoMotor;
    }

    public String getLargoTotal() {
        return largoTotal;
    }

    public void setLargoTotal(String largoTotal) {
        this.largoTotal = largoTotal;
    }

    public String getAnchoTotal() {
        return anchoTotal;
    }

    public void setAnchoTotal(String anchoTotal) {
        this.anchoTotal = anchoTotal;
    }

    public String getAlturaTotal() {
        return alturaTotal;
    }

    public void setAlturaTotal(String alturaTotal) {
        this.alturaTotal = alturaTotal;
    }

    public String getAlturaAsiento() {
        return alturaAsiento;
    }

    public void setAlturaAsiento(String alturaAsiento) {
        this.alturaAsiento = alturaAsiento;
    }

    public String getDistanciaEntreEjes() {
        return distanciaEntreEjes;
    }

    public void setDistanciaEntreEjes(String distanciaEntreEjes) {
        this.distanciaEntreEjes = distanciaEntreEjes;
    }

    public String getDistanciaMinimaPiso() {
        return distanciaMinimaPiso;
    }

    public void setDistanciaMinimaPiso(String distanciaMinimaPiso) {
        this.distanciaMinimaPiso = distanciaMinimaPiso;
    }

    public String getTipoLubricacion() {
        return tipoLubricacion;
    }

    public void setTipoLubricacion(String tipoLubricacion) {
        this.tipoLubricacion = tipoLubricacion;
    }

    public String getBateria() {
        return bateria;
    }

    public void setBateria(String bateria) {
        this.bateria = bateria;
    }

    public String getDisposiciondeCilindros() {
        return disposiciondeCilindros;
    }

    public void setDisposiciondeCilindros(String disposiciondeCilindros) {
        this.disposiciondeCilindros = disposiciondeCilindros;
    }

    public String getDiametroPorCarrera() {
        return diametroPorCarrera;
    }

    public void setDiametroPorCarrera(String diametroPorCarrera) {
        this.diametroPorCarrera = diametroPorCarrera;
    }

    public String getRelacionCompresion() {
        return relacionCompresion;
    }

    public void setRelacionCompresion(String relacionCompresion) {
        this.relacionCompresion = relacionCompresion;
    }

    public String getArranque() {
        return arranque;
    }

    public void setArranque(String arranque) {
        this.arranque = arranque;
    }

    public String getSistemaAlimentacion() {
        return sistemaAlimentacion;
    }

    public void setSistemaAlimentacion(String sistemaAlimentacion) {
        this.sistemaAlimentacion = sistemaAlimentacion;
    }

    public String getCapacidadCombustible() {
        return capacidadCombustible;
    }

    public void setCapacidadCombustible(String capacidadCombustible) {
        this.capacidadCombustible = capacidadCombustible;
    }

    public String getEncendido() {
        return encendido;
    }

    public void setEncendido(String encendido) {
        this.encendido = encendido;
    }

    public String getCapacidadBateria() {
        return capacidadBateria;
    }

    public void setCapacidadBateria(String capacidadBateria) {
        this.capacidadBateria = capacidadBateria;
    }

    public String getSistemadeReduccionPrimaria() {
        return sistemadeReduccionPrimaria;
    }

    public void setSistemadeReduccionPrimaria(String sistemadeReduccionPrimaria) {
        this.sistemadeReduccionPrimaria = sistemadeReduccionPrimaria;
    }

    public String getRelaciondeReduccionPrimaria() {
        return relaciondeReduccionPrimaria;
    }

    public void setRelaciondeReduccionPrimaria(String relaciondeReduccionPrimaria) {
        this.relaciondeReduccionPrimaria = relaciondeReduccionPrimaria;
    }

    public String getSistemadeReduccionSecundaria() {
        return sistemadeReduccionSecundaria;
    }

    public void setSistemadeReduccionSecundaria(String sistemadeReduccionSecundaria) {
        this.sistemadeReduccionSecundaria = sistemadeReduccionSecundaria;
    }

    public String getRelaciondeReduccionSecundaria() {
        return relaciondeReduccionSecundaria;
    }

    public void setRelaciondeReduccionSecundaria(String relaciondeReduccionSecundaria) {
        this.relaciondeReduccionSecundaria = relaciondeReduccionSecundaria;
    }

    public String getTipoEmbrague() {
        return tipoEmbrague;
    }

    public void setTipoEmbrague(String tipoEmbrague) {
        this.tipoEmbrague = tipoEmbrague;
    }

    public String getTipoTransmision() {
        return tipoTransmision;
    }

    public void setTipoTransmision(String tipoTransmision) {
        this.tipoTransmision = tipoTransmision;
    }

    public String getRelacionTransmision1ra() {
        return relacionTransmision1ra;
    }

    public void setRelacionTransmision1ra(String relacionTransmision1ra) {
        this.relacionTransmision1ra = relacionTransmision1ra;
    }

    public String getRelacionTransmision2da() {
        return relacionTransmision2da;
    }

    public void setRelacionTransmision2da(String relacionTransmision2da) {
        this.relacionTransmision2da = relacionTransmision2da;
    }

    public String getRelacionTransmision3ra() {
        return relacionTransmision3ra;
    }

    public void setRelacionTransmision3ra(String relacionTransmision3ra) {
        this.relacionTransmision3ra = relacionTransmision3ra;
    }

    public String getRelacionTransmision4ta() {
        return relacionTransmision4ta;
    }

    public void setRelacionTransmision4ta(String relacionTransmision4ta) {
        this.relacionTransmision4ta = relacionTransmision4ta;
    }

    public String getRelacionTransmision5ta() {
        return relacionTransmision5ta;
    }

    public void setRelacionTransmision5ta(String relacionTransmision5ta) {
        this.relacionTransmision5ta = relacionTransmision5ta;
    }

    public String getRelacionTransmision6ta() {
        return relacionTransmision6ta;
    }

    public void setRelacionTransmision6ta(String relacionTransmision6ta) {
        this.relacionTransmision6ta = relacionTransmision6ta;
    }

    public String getRelacionEngranajes() {
        return relacionEngranajes;
    }

    public void setRelacionEngranajes(String relacionEngranajes) {
        this.relacionEngranajes = relacionEngranajes;
    }

    public String getTipoChasis() {
        return tipoChasis;
    }

    public void setTipoChasis(String tipoChasis) {
        this.tipoChasis = tipoChasis;
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

    public String getRuedaDelantera() {
        return ruedaDelantera;
    }

    public void setRuedaDelantera(String ruedaDelantera) {
        this.ruedaDelantera = ruedaDelantera;
    }

    public String getRuedaTrasera() {
        return ruedaTrasera;
    }

    public void setRuedaTrasera(String ruedaTrasera) {
        this.ruedaTrasera = ruedaTrasera;
    }

    public String getFrenoDelantero() {
        return frenoDelantero;
    }

    public void setFrenoDelantero(String frenoDelantero) {
        this.frenoDelantero = frenoDelantero;
    }

    public String getFrenoTrasero() {
        return frenoTrasero;
    }

    public void setFrenoTrasero(String frenoTrasero) {
        this.frenoTrasero = frenoTrasero;
    }

    public String getTipoSuspensionDelantera() {
        return tipoSuspensionDelantera;
    }

    public void setTipoSuspensionDelantera(String tipoSuspensionDelantera) {
        this.tipoSuspensionDelantera = tipoSuspensionDelantera;
    }

    public String getTipoSuspensionTrasera() {
        return tipoSuspensionTrasera;
    }

    public void setTipoSuspensionTrasera(String tipoSuspensionTrasera) {
        this.tipoSuspensionTrasera = tipoSuspensionTrasera;
    }

    public String getLuzPrincipal() {
        return luzPrincipal;
    }

    public void setLuzPrincipal(String luzPrincipal) {
        this.luzPrincipal = luzPrincipal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isDescuento() {
        return descuento;
    }

    public void setDescuento(boolean descuento) {
        this.descuento = descuento;
    }

    public String getNuevoValorDescuento() {
        return nuevoValorDescuento;
    }

    public void setNuevoValorDescuento(String nuevoValorDescuento) {
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

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
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
}
