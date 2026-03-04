public class LabSoru2 {
    public static void main(String[] args) {
        
        byte b = 10;
      
        System.out.println("Baslangic degeri: " + b);

        // += operatörü arka planda gizlice şu işlemi yapar: b = (byte)(b + 120);
        // 10 + 120 = 130 eder. Ancak bir byte en fazla 127 değerini tutabilir!
        
        b += 120;
        
        // 127'den sonra sınır aşıldığı için sayı başa (en küçük negatif değere) sarar.
        // 127 -> 128(-128) -> 129(-127) -> 130(-126) şeklinde hesaplanır.
        
        System.out.println("b'nin yeni degeri: " + b);
        System.out.println("Not: Kod derleme hatasi vermedi ancak 130 degeri byte sinirini astigi icin Overflow oldu!");
    }
}
