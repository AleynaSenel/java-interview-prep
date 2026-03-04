public class LabSoru3 {
    public static void main(String[] args){
        
        char karakter = 'A'; // 'A' karakterinin arka plandaki sayısal (ASCII) değeri 65'tir.
        System.out.println("Baslangic karakteri: " + karakter);

        // Matematiksel bir işlem yapıldığı anda Java char tipini otomatik olarak int'e yükseltir.
        // Yani aslında arka planda "65 + 5" işlemi yapılıyor.
        int sonuc = karakter + 5; 
        
        System.out.println("karakter + 5 isleminin int olarak sonucu: " + sonuc);
        
        // EKSTRA MÜLAKAT BİLGİSİ: 
        // Eğer çıkan 70 sonucunu tekrar harfe (char) çevirmek isteseydik ne olurdu?
        // O zaman çıkan 70 sayısını (int) zorla char'a sığdırmamız (casting) gerekirdi:
        
        char yeniKarakter = (char) sonuc;
        System.out.println("Eger 70'i tekrar char yaparsak yeni harf: " + yeniKarakter);
    }
}
