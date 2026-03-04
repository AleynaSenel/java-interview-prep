public class LabSoru2 {
    public static void main(String[] args) {
        
        byte b = 10;
      
        System.out.println("Başlangıç değeri -> b: " + b);

        // += operatörü arka planda gizlice şu işlemi yapar: b = (byte)(b + 120);
        // 10 + 120 = 130 eder. Ancak bir byte en fazla 127 değerini tutabilir!
        
        b += 120;
        
        // 127'den sonra sınır aşıldığı için sayı başa (en küçük negatif değere) sarar.
        // 127 -> 128(-128) -> 129(-127) -> 130(-126) şeklinde hesaplanır.
        
        System.out.println("b += 120 işleminden sonra b'nin yeni değeri: " + b);
        System.out.println("Not: Kod derleme hatası vermedi ancak 130 değeri byte sınırını aştığı için Overflow oldu!");
    }
}
