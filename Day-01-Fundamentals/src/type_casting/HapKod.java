public class CastingTuzagi {
    public static void main(String[] args) {
      
        byte b = 10;
        System.out.println("Baslangic degeri: " + b);

        // DERLEME HATASI (Compile Error)
        // Eğer aşağıdaki yorum satırını açarsan Java hata verecektir.
        // Çünkü 'b + 5' matematiksel işlemi sonucu otomatik olarak int (32 bit) üretir.
        // int bir değer, doğrudan byte (8 bit) bir değişkene atanamaz.
        
        // b = b + 5; 

        
        // ÇALISIR! (Bileşik Atama Operatörü)
        // += operatörü, arka planda otomatik olarak açık tip dönüşümü (explicit casting) yapar.
        // Yani aslında Java gizlice şunu çalıştırır: b = (byte) (b + 5);
        
        b += 5; 
        
        System.out.println("b += 5 isleminden sonra b'nin yeni degeri: " + b);
    }
}
