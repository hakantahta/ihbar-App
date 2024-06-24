package com.example.ihbar;

public class Ihbar {
    private String kullaniciId;
    private String sikayetMetni;
    private String resimUrl;
    private boolean aktiflikDurumu; // Yeni eklenen aktiflikDurumu alanı

    public Ihbar() {
        // Boş yapıcı metot gerekli Firebase için
    }

    public Ihbar(String kullaniciId, String sikayetMetni, String resimUrl, boolean aktiflikDurumu) {
        this.kullaniciId = kullaniciId;
        this.sikayetMetni = sikayetMetni;
        this.resimUrl = resimUrl;
        this.aktiflikDurumu = aktiflikDurumu; // Aktiflik durumunu ayarla
    }

    public String getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(String kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getSikayetMetni() {
        return sikayetMetni;
    }

    public void setSikayetMetni(String sikayetMetni) {
        this.sikayetMetni = sikayetMetni;
    }

    public String getResimUrl() {
        return resimUrl;
    }

    public void setResimUrl(String resimUrl) {
        this.resimUrl = resimUrl;
    }

    public boolean isAktiflikDurumu() {
        return aktiflikDurumu;
    }

    public void setAktiflikDurumu(boolean aktiflikDurumu) {
        this.aktiflikDurumu = aktiflikDurumu;
    }
}
