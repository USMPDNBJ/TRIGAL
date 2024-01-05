package com.proyect.trigal;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private String nombreEmpresa;
    private String responsable;
    private String numeroDocumento;
    private String celular;
    private String correo;
    private String contraseña;

    public Usuario() {}

    public Usuario(String nombreEmpresa, String responsable, String numeroDocumento, String celular, String correo, String contraseña) {
        this.nombreEmpresa = nombreEmpresa;
        this.responsable = responsable;
        this.numeroDocumento = numeroDocumento;
        this.celular = celular;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }


    @Override
    public String toString() {
        return  "Nombre Empresa = " + nombreEmpresa + "\n" +
                "Numero Documento = " + numeroDocumento + "\n" +
                "Celular = " + celular + "\n" +
                "Correo = " + correo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombreEmpresa);
        dest.writeString(this.responsable);
        dest.writeString(this.numeroDocumento);
        dest.writeString(this.celular);
        dest.writeString(this.correo);
        dest.writeString(this.contraseña);
    }

    public void readFromParcel(Parcel source) {
        this.nombreEmpresa = source.readString();
        this.responsable = source.readString();
        this.numeroDocumento = source.readString();
        this.celular = source.readString();
        this.correo = source.readString();
        this.contraseña = source.readString();
    }

    protected Usuario(Parcel in) {
        this.nombreEmpresa = in.readString();
        this.responsable = in.readString();
        this.numeroDocumento = in.readString();
        this.celular = in.readString();
        this.correo = in.readString();
        this.contraseña = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel source) {
            return new Usuario(source);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}
