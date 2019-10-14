package com.example.games.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Usuario {
    private int id;
    private String nome;
    private String apelido;
    private String email;
    private String senha;
    private String estado;
    private String preferencias;

    private SharedPreferences sharedPreferences;

    //converter um shared preference para editor
    public SharedPreferences.Editor atualizarSecaoDoUsuario(SharedPreferences sharedPreferences){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", getId());
        editor.putString("nome", getNome());
        editor.putString("apelido", getApelido());
        editor.putString("email", getEmail());
        editor.putString("senha", getSenha());
        editor.putString("estado", getEstado());
        editor.putString("preferencias", getPreferencias());

        return editor;
    }

    //Contrutor padrão
    public Usuario(){}

    public Usuario(SharedPreferences sharedPreferences){
        setId(sharedPreferences.getInt("id",0));
        setNome(sharedPreferences.getString("nome","vazio"));
        setApelido(sharedPreferences.getString("apelido", "vazio"));
        setEmail(sharedPreferences.getString("email", "vazio"));
        setSenha(sharedPreferences.getString("senha","vazio"));
        setEstado(sharedPreferences.getString("estado", "vazio"));
        setPreferencias(sharedPreferences.getString("preferencias", "preferencias"));
    }

    //destrinchando um objeto resposta json
    public Usuario(JSONObject jsonObject) throws JSONException {
        setId(jsonObject.getInt("id"));
        setNome(jsonObject.getString("nome"));
        setApelido(jsonObject.getString("apelido"));
        setEmail(jsonObject.getString("email"));
        setSenha(jsonObject.getString("senha"));
        setEstado(jsonObject.getString("estado"));
        setPreferencias(jsonObject.getString("preferencias"));
    }

    //contrutor com todos parametros
    public Usuario(int id, String nome, String apelido, String email, String senha, String estado, String preferencias){
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.email = email;
        this.senha = senha;
        this.estado = estado;
        this.preferencias = preferencias;
    }

    //retornar json
    public JSONObject toJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id",getId());
        jsonObject.put("nome",getNome());
        jsonObject.put("apelido",getApelido());
        jsonObject.put("email",getEmail());
        jsonObject.put("senha", getSenha());
        jsonObject.put("estado", getEstado());
        jsonObject.put("preferencias", getPreferencias());

        return jsonObject;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }
}
